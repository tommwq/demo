#include "pch.h"


/*
文件包格式

时间戳 u64 包

*/

class IPPacket {
public:
public:
	IPPacket(const IPPacket &rhs) = delete;
	IPPacket& operator=(const IPPacket &rhs) = delete;

	IPPacket(IPPacket &&rhs) {
		data = rhs.data;
		size = rhs.size;

		rhs.data = nullptr;
		rhs.size = 0;
	}

	IPPacket& operator=(IPPacket &&rhs) {
		delete[] data;

		data = rhs.data;
		size = rhs.size;

		rhs.data = nullptr;
		rhs.size = 0;

		return *this;
	}

	IPPacket(const char *data, unsigned int size) :size{ size } {
		this->data = new char[size];
		std::memcpy(this->data, data, size);
	}

	virtual ~IPPacket() {
		delete[] data;
	}

	char *data;
	unsigned int size;
};

class TimestampedIPPacket {
public:
	TimestampedIPPacket(const TimestampedIPPacket &rhs) = delete;
	TimestampedIPPacket& operator=(const TimestampedIPPacket &rhs) = delete;

	TimestampedIPPacket(TimestampedIPPacket &&rhs) :packet(std::move(rhs.packet)), timestamp(rhs.timestamp) {}

	TimestampedIPPacket& operator=(TimestampedIPPacket &&rhs) {
		delete[] packet.data;

		packet.data = rhs.packet.data;
		packet.size = rhs.packet.size;
		timestamp = rhs.timestamp;

		rhs.packet.data = nullptr;
		rhs.packet.size = 0;
		rhs.timestamp = 0;

		return *this;
	}

	TimestampedIPPacket(long long ts = 0, const char *data = nullptr, unsigned int size = 0) :timestamp(ts), packet(data, size) {}

	long long timestamp;
	IPPacket packet;
};

volatile bool running = true;
volatile long long timestamp_ns = 0;
Concurrency::concurrent_queue<TimestampedIPPacket*> packet_queue;

void update_timestamp_cycle(long long update_period_ns) {
	std::chrono::time_point<std::chrono::steady_clock, std::chrono::nanoseconds> now;
	std::chrono::steady_clock clock;

	while (running) {
		std::this_thread::sleep_for(std::chrono::nanoseconds{ update_period_ns });
		now = std::chrono::time_point_cast<std::chrono::nanoseconds>(clock.now());
		timestamp_ns = now.time_since_epoch().count();
	}
}

class windows_network_error : public std::runtime_error {
public:
	windows_network_error(const char *message) :runtime_error(message), error_code{ ::WSAGetLastError() }{
		std::size_t length = std::strlen(runtime_error::what()) + 64;
		buffer = new char[length];
		std::snprintf(static_cast<char*>(buffer), length, "错误：%s。错误号：%d", runtime_error::what(), error_code);
	}

	const char* what() const override {
		return buffer;
	}

	~windows_network_error() {
		delete[] buffer;
	}

private:
	int error_code;
	char *buffer;
};

void initialize_network() {
	WORD wsa_version = MAKEWORD(2, 0);
	WSADATA wsa_data;
	int return_value = WSAStartup(wsa_version, &wsa_data);
	if (return_value != 0) {
		throw windows_network_error("初始化网络失败");
	}
}

SOCKET create_promise_socket(const std::string &local_host) {
	SOCKET socket_fd = socket(AF_INET, SOCK_RAW, IPPROTO_IP);
	if (socket_fd == INVALID_SOCKET)
	{
		throw windows_network_error("建立套接字失败");
	}

	try {
		DWORD value = TRUE;
		int return_value = setsockopt(socket_fd, IPPROTO_IP, IP_HDRINCL, (char *)&value, sizeof(value));
		if (return_value != 0)
		{
			throw windows_network_error("设置套接字失败");
		}

		struct sockaddr_in address;
		ZeroMemory(&address, sizeof(address));
		address.sin_family = AF_INET;
		address.sin_addr.s_addr = inet_addr(local_host.c_str());
		address.sin_port = 0;

		return_value = bind(socket_fd, (struct sockaddr*) &address, sizeof(address));
		if (return_value != 0)
		{
			throw windows_network_error("绑定套接字失败");
		}

		u_long bool_value = 1;
		return_value = ioctlsocket(socket_fd, SIO_RCVALL, &bool_value);
		if (return_value != 0)
		{
			throw windows_network_error("设置套接字失败");
		}

		return socket_fd;
	}
	catch (const windows_network_error& error) {
		::closesocket(socket_fd);
		throw error;
	}
}

void receive_cycle(SOCKET promised_socket, const std::string source_host, unsigned short port, volatile bool &running) {
	struct sockaddr_in address;

	char buffer[1024 * 16];
	const int length = sizeof(buffer);
	char string_buffer[1024];
	const int string_buffer_length = sizeof(string_buffer);
	int address_length = sizeof(address);

	while (running) {
		ZeroMemory(&address, sizeof(address));
		int received_length = recvfrom(promised_socket, buffer, length, 0, (struct sockaddr*) &address, &address_length);
		if (received_length == 0)
		{
			throw windows_network_error("套接字接收数据失败");
		}

		inet_ntop(AF_INET, &(address.sin_addr), string_buffer, string_buffer_length);

		struct ip_header *ip_hdr = (struct ip_header*) buffer;
		int protocol = ip_hdr->protocol & 0x00ff;

		if (protocol != TransmissionControlProtocol) {
			continue;
		}

		if (address.sin_addr.s_addr != inet_addr(source_host.c_str())) {
			continue;
		}

		int ip_header_length = (ip_hdr->version_and_length & 0x0f) * IPPacketLengthUnit;

		struct tcp_header *t = (struct tcp_header*)(buffer + ip_header_length);
		int source_port = ntohs(t->source_port);
		if (source_port != port) {
			continue;
		}

		TimestampedIPPacket *packet = new TimestampedIPPacket(timestamp_ns, buffer, received_length);
		packet_queue.push(packet);
	}
}

void signal_handler(int signum) {
	running = false;
}

void receive_ip_packet(const std::string& local_host, const std::string& source_host, unsigned short port, volatile bool &running) {
	initialize_network();
	SOCKET socket_fd = create_promise_socket(local_host);
	receive_cycle(socket_fd, source_host, port, running);
}


void write_disk_cycle(std::ofstream &output_stream) {
	while (running) {
		TimestampedIPPacket *packet;
		while (packet_queue.try_pop(packet)) {
			struct ip_header *ip_hdr = (struct ip_header*) packet->packet.data;
			int ip_header_length = (ip_hdr->version_and_length & 0x0f) * IPPacketLengthUnit;

			struct tcp_header *tcp_hdr = (struct tcp_header*)(packet->packet.data + ip_header_length);

			output_stream.write(reinterpret_cast<char*>(&packet->timestamp), sizeof(packet->timestamp));
			output_stream.write(packet->packet.data, packet->packet.size);
			output_stream.flush();
			
			std::cout << packet->timestamp << " " << htonl(tcp_hdr->sequence) << std::endl;

			delete packet;
		}
	}
}

void receive_socket_cycle(SOCKET socket) {
	char *buffer = new char[4096];
	while (running) {
		recv(socket, buffer, 4096, 0);
	}

	delete[] buffer;
}

void show_help() {
	std::cout << R"(command line arguments:
  --help help
  --port PORT
  --local-host LOCAL_HOST
  --mode MODE record/reply/dump
  --output FILENAME
  --input FILENAME
  --source-host SOURCE_HOST
)" << std::endl;
}

int main(int argc, char *argv[])
{
	signal(SIGINT, signal_handler);

	bool help_flag = false;

	if (argc <= 1) {
		help_flag = true;
	}

	unsigned short port;
	std::string local_host;
	std::string source_host;
	std::string mode;
	std::string output_filename;
	std::string input_filename;

	for (int i = 1; i < argc; i++) {
		std::string arg{ argv[i] };
		if (arg == "-help") {
			help_flag = true;
			break;
		}

		if (arg == "--port") {
			if (i == argc - 1) {
				help_flag = true;
				break;
			}

			port = static_cast<unsigned short>(std::atoi(argv[++i]));
		} else if (arg == "--local-host") {
			if (i == argc - 1) {
				help_flag = true;
				break;
			}

			local_host = argv[++i];
		} else if (arg == "--source-host") {
			if (i == argc - 1) {
				help_flag = true;
				break;
			}

			source_host = argv[++i];
		} else if (arg == "--mode") {
			if (i == argc - 1) {
				help_flag = true;
				break;
			}

			mode = argv[++i];
		} else if (arg == "--output") {
			if (i == argc - 1) {
				help_flag = true;
				break;
			}

			output_filename = argv[++i];
		} else if (arg == "--input") {
			if (i == argc - 1) {
				help_flag = true;
				break;
			}

			input_filename = argv[++i];
		}
	}

	if (help_flag) {
		show_help();
		return 0;
	}

	running = true;

	try {
		if (mode == "record") {
			std::ofstream output_file(output_filename.c_str(), std::ios::binary | std::ios::out | std::ios::trunc);

			std::thread write_disk_thread(write_disk_cycle, std::ref(output_file));

			std::thread receive_packet_thread(receive_ip_packet, std::cref(local_host), std::cref(source_host), port, std::ref(running));
			long long period = 10000;
			std::thread update_timestamp_thread(update_timestamp_cycle, period);

			receive_packet_thread.join();
			update_timestamp_thread.join();
			write_disk_thread.join();
		} else if (mode == "dump") {
			std::ifstream input_file;
			input_file.open(input_filename.c_str(), std::ios_base::binary | std::ios_base::in);
			std::ofstream output_file(output_filename.c_str(), std::ios::binary | std::ios::out | std::ios::trunc);

			if (!input_file.is_open()) {
				std::cout << "无法打开文件" << std::endl;
				return -1;
			}

			if (!output_file.is_open()) {
				std::cout << "无法打开文件" << std::endl;
				return -1;
			}

			const int buffer_size = 64 * 1024;
			std::uint64_t position = 0;
			char *buffer = new char[buffer_size];
			int prev = input_file.tellg();

			while (true) {
				input_file.read(buffer + position, buffer_size - position);
				std::uint64_t curr = input_file.tellg();
				std::uint64_t read_bytes = curr - prev;
				if (read_bytes == 0) {
					break;
				}
				prev = curr;
				std::uint64_t available_bytes = read_bytes + position;
				position = 0;

				while (true) {
					const int required_bytes = 20 + sizeof(long long);
					if (available_bytes < required_bytes + position) {
						position = available_bytes;
						break;
					}

					long long *time = reinterpret_cast<long long*>(buffer + position);
					char *ip_offset = buffer + (int)(position + sizeof(*time));
					struct ip_header *ip_hdr = (struct ip_header*) ip_offset;
					int ip_header_length = (ip_hdr->version_and_length & 0x0f) * IPPacketLengthUnit;
					int packet_length = ntohs(ip_hdr->total_length);

					if (available_bytes < packet_length + sizeof(long long)) {
						position = available_bytes;
						break;
					}

					struct tcp_header *tcp_hdr = (struct tcp_header*)(ip_offset + ip_header_length);
					std::cout << htonl(tcp_hdr->sequence) << std::endl;
					int tcp_header_length = tcp_hdr->length_and_reversed >> 4;

					char *data_offset = ip_offset + ip_header_length + tcp_header_length;
					int data_length = packet_length - ip_header_length - tcp_header_length;

					output_file.write(data_offset, data_length);

					position += packet_length + sizeof(*time);
				}
			}

			input_file.close();

			delete[] buffer;

		} else if (mode == "reply") {

			std::ifstream input_file;
			input_file.open(input_filename.c_str(), std::ios_base::binary | std::ios_base::in);

			if (!input_file.is_open()) {
				std::cout << "无法打开文件" << std::endl;
				return -1;
			}

			const int buffer_size = 64 * 1024;
			std::uint64_t position = 0;
			char *buffer = new char[buffer_size];
			int prev = input_file.tellg();

			initialize_network();

			SOCKET listen_socket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
			if (listen_socket == INVALID_SOCKET) {
				throw windows_network_error("建立套接字失败。");
			}

			struct sockaddr_in address;
			ZeroMemory(&address, sizeof(address));
			address.sin_family = AF_INET;
			address.sin_addr.s_addr = INADDR_ANY;
			address.sin_port = htons(port);

			DWORD result = bind(listen_socket, reinterpret_cast<struct sockaddr*>(&address), sizeof(address));
			if (result != 0)
			{
				throw windows_network_error("绑定套接字失败");
			}

			result = listen(listen_socket, 1);
			if (result != 0)
			{
				throw windows_network_error("监听套接字失败");
			}

			ZeroMemory(&address, sizeof(address));
			int address_size = sizeof(address);
			SOCKET client = accept(listen_socket, reinterpret_cast<struct sockaddr*>(&address), &address_size);
			if (client == INVALID_SOCKET) {
				throw windows_network_error("接受连接失败");
			}

			std::thread receive_thread(receive_socket_cycle, client);
			while (true) {
				input_file.read(buffer + position, buffer_size - position);
				std::uint64_t curr = input_file.tellg();
				std::uint64_t read_bytes = curr - prev;
				if (read_bytes == 0) {
					break;
				}
				prev = curr;
				std::uint64_t available_bytes = read_bytes + position;
				position = 0;

				while (true) {
					const int required_bytes = 20 + sizeof(long long);
					if (available_bytes < required_bytes + position) {
						position = available_bytes;
						break;
					}

					long long *time = reinterpret_cast<long long*>(buffer + position);
					char *ip_offset = buffer + (int)(position + sizeof(*time));
					struct ip_header *ip_hdr = (struct ip_header*) ip_offset;
					int ip_header_length = (ip_hdr->version_and_length & 0x0f) * IPPacketLengthUnit;
					int packet_length = ntohs(ip_hdr->total_length);

					if (available_bytes < packet_length + sizeof(long long)) {
						position = available_bytes;
						break;
					}

					struct tcp_header *tcp_hdr = (struct tcp_header*)(ip_offset + ip_header_length);
					std::cout << htonl(tcp_hdr->sequence) << std::endl;
					int tcp_header_length = tcp_hdr->length_and_reversed >> 4;
				
					char *data_offset = ip_offset + ip_header_length + tcp_header_length;
					int data_length = packet_length - ip_header_length - tcp_header_length;

					send(client, data_offset, data_length, 0);

					std::cout << "packet length " << packet_length << std::endl
						<< " ip header length " << ip_header_length << std::endl
						<< " tcp header length " << tcp_header_length << std::endl
						<< " data length " << data_length << std::endl
						<< std::endl;

					position += packet_length + sizeof(*time);
				}
			}

			input_file.close();
			closesocket(client);

			delete[] buffer;
		}
	} catch (const std::exception &exception) {
		std::cout << exception.what() << std::endl;
	}
	return 0;
}
