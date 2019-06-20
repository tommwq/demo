#define _WINSOCK_DEPRECATED_NO_WARNINGS 

#include <stdio.h>
#include <stdlib.h>
#include <WinSock2.h>
#include <Windows.h>
#include <mstcpip.h>
#include <ws2def.h>
#include <ws2ipdef.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

char* get_ip_protocol_name(int protocol);


typedef unsigned char byte;
typedef unsigned short word;
typedef unsigned int dword;

struct ip_header {
	byte version_and_length;
	byte type_of_service;
	word total_length;
	word identity;
	word flag_and_offset;
	byte time_to_live;
	byte protocol;
	word checksum;
	dword source_address;
	dword destination_address;
};

struct tcp_header {
	word source_port;
	word destination_port;
	dword sequence;
	dword acknowledge;
	word length_and_reversed;
	word reversed_and_flag;
	word window_size;
	word checksum;
	word urgent_pointer;
};

int main(int argc, char *argv[])
{
	WORD wsa_version = MAKEWORD(2, 0);
	WSADATA wsa_data;
	int return_value = WSAStartup(wsa_version, &wsa_data);
	if (return_value != 0)
        {
            printf("WSAStartup error: %d\n", WSAGetLastError());
            exit(-1);
        }

	SOCKET socket_fd = socket(AF_INET, SOCK_RAW, IPPROTO_IP);
	if (socket_fd == INVALID_SOCKET)
        {
            printf("socket error: %d\n", WSAGetLastError());
            exit(-1);
        }

	DWORD value = TRUE;
	return_value = setsockopt(socket_fd, IPPROTO_IP, IP_HDRINCL, (char *)&value, sizeof(value));
	if (return_value != 0)
        {
            printf("setsockopt error: %d\n", WSAGetLastError());
            exit(-1);
        }

	char *host = "172.24.20.150";
	if (argc > 1)
        {
            host = argv[1];
        }

	struct sockaddr_in address;
	ZeroMemory(&address, sizeof(address));
	address.sin_family = AF_INET;
	address.sin_addr.s_addr = inet_addr(host); // htonl(INADDR_ANY); // 不能给你用INADDR_ANY
	address.sin_port = 0;

	return_value = bind(socket_fd, (struct sockaddr*) &address, sizeof(address));
	if (return_value != 0)
        {
            printf("bind error: %d\n", WSAGetLastError());
            exit(-1);
        }

	BOOL bool_value = 1;
	return_value = ioctlsocket(socket_fd, SIO_RCVALL, &bool_value);
	if (return_value != 0)
        {
            printf("ioctlsocket error: %d\n", WSAGetLastError());
            exit(-1);
        }

	char buffer[1024 * 16];
	int length = sizeof(buffer);

	char string_buffer[1024];
	int string_buffer_length = sizeof(string_buffer);

	int address_length = sizeof(address);

	int count = 30;

	while (1)
        {
            ZeroMemory(&address, sizeof(address));
            return_value = recvfrom(socket_fd, buffer, length, 0, (struct sockaddr*) &address, &address_length);
            if (return_value == 0)
                {
                    printf("recvfrom error: %d\n", WSAGetLastError());
                    exit(-1);
                }

            inet_ntop(AF_INET, &(address.sin_addr), string_buffer, string_buffer_length);
	
            struct ip_header *h = (struct ip_header*) buffer;
            int prot = h->protocol & 0x00ff;

            int const TCP = 6;
            if (prot != TCP) {
                continue;
            }

            if (address.sin_addr.s_addr != inet_addr(host)) {
                //continue;
            }

            int ip_header_length = (h->version_and_length & 0x0f)*4;
		
            struct tcp_header *t = (struct tcp_header*)(buffer + ip_header_length);
            int source_port = ntohs(t->source_port);
            if (source_port != 9999) {
                continue;
            }

            int seq = ntohl(t->sequence);
            int ack = ntohl(t->acknowledge);

            printf("seq %u ack %u size %d\n", seq, ack, return_value); 
            // printf("%s:%d => %s:%d\n", string_buffer, ntohs(t->source_port), host, ntohs(t->destination_port));
            //break;

            if (count-- == 0) {
                break;
            }
        }
}


char* get_ip_protocol_name(int protocol) {
	switch (protocol) {
	case 0:
		return "HOPOPT";
	case 1:
		return "ICMP";
	case 2:
		return "IGMP";
	case 3:
		return "GGP";
	case 4:
		return "IP-in-IP";
	case 5:
		return "ST";
	case 6:
		return "TCP";
	case 7:
		return "CBT";
	case 8:
		return "EGP";
	case 9:
		return "IGP";
	case 10:
		return "BBN-RCC-MON";
	case 11:
		return "NVP-II";
	case 12:
		return "PUP";
	case 13:
		return "ARGUS";
	case 14:
		return "EMCON";
	case 15:
		return "XNET";
	case 16:
		return "CHAOS";
	case 17:
		return "UDP";
	case 18:
		return "MUX";
	case 19:
		return "DCN-MEAS";
	case 20:
		return "HMP";
	case 21:
		return "PRM";
	case 22:
		return "XNS-IDP";
	case 23:
		return "TRUNK-1";
	case 24:
		return "TRUNK-2";
	case 25:
		return "LEAF-1";
	case 26:
		return "LEAF-2";
	case 27:
		return "RDP";
	case 28:
		return "IRTP";
	case 29:
		return "ISO-TP4";
	case 30:
		return "NETBLT";
	case 31:
		return "MFE-NSP";
	case 32:
		return "MERIT-INP";
	case 33:
		return "DCCP";
	case 34:
		return "3PC";
	case 35:
		return "IDPR";
	case 36:
		return "XTP";
	case 37:
		return "DDP";
	case 38:
		return "IDPR-CMTP";
	case 39:
		return "TP++";
	case 40:
		return "IL";
	case 41:
		return "IPv6";
	case 42:
		return "SDRP";
	case 43:
		return "IPv6-Route";
	case 44:
		return "IPv6-Frag";
	case 45:
		return "IDRP";
	case 46:
		return "RSVP";
	case 47:
		return "GREs";
	case 48:
		return "DSR";
	case 49:
		return "BNA";
	case 50:
		return "ESP";
	case 51:
		return "AH";
	case 52:
		return "I-NLSP";
	case 53:
		return "SWIPE";
	case 54:
		return "NARP";
	case 55:
		return "MOBILE";
	case 56:
		return "TLSP";
	case 57:
		return "SKIP";
	case 58:
		return "IPv6-ICMP";
	case 59:
		return "IPv6-NoNxt";
	case 60:
		return "IPv6-Opts";
	case 62:
		return "CFTP";
	case 64:
		return "SAT-EXPAK";
	case 65:
		return "KRYPTOLAN";
	case 66:
		return "RVD";
	case 67:
		return "IPPC";
	case 69:
		return "SAT-MON";
	case 70:
		return "VISA";
	case 71:
		return "IPCU";
	case 72:
		return "CPNX";
	case 73:
		return "CPHB";
	case 74:
		return "WSN";
	case 75:
		return "PVP";
	case 76:
		return "BR-SAT-MON";
	case 77:
		return "SUN-ND";
	case 78:
		return "WB-MON";
	case 79:
		return "WB-EXPAK";
	case 80:
		return "ISO-IP";
	case 81:
		return "VMTP";
	case 82:
		return "SECURE-VMTP";
	case 83:
		return "VINES";
	case 84:
		return "TTP/IPTM";
	case 85:
		return "NSFNET-IGP";
	case 86:
		return "DGP";
	case 87:
		return "TCF";
	case 88:
		return "EIGRP";
	case 89:
		return "OSPF";
	case 90:
		return "Sprite-RPC";
	case 91:
		return "LARP";
	case 92:
		return "MTP";
	case 93:
		return "AX.25";
	case 94:
		return "OS";
	case 95:
		return "MICP";
	case 96:
		return "SCC-SP";
	case 97:
		return "ETHERIP";
	case 98:
		return "ENCAP";
	case 100:
		return "GMTP";
	case 101:
		return "IFMP";
	case 102:
		return "PNNI";
	case 103:
		return "PIM";
	case 104:
		return "ARIS";
	case 105:
		return "SCPS";
	case 106:
		return "QNX";
	case 107:
		return "A/N";
	case 108:
		return "IPComp";
	case 109:
		return "SNP";
	case 110:
		return "Compaq-Peer";
	case 111:
		return "IPX-in-IP";
	case 112:
		return "VRRP";
	case 113:
		return "PGM";
	case 115:
		return "L2TP";
	case 116:
		return "DDX";
	case 117:
		return "IATP";
	case 118:
		return "STP";
	case 119:
		return "SRP";
	case 120:
		return "UTI";
	case 121:
		return "SMP";
	case 122:
		return "SM";
	case 123:
		return "PTP";
	case 124:
		return "IS-ISoverIPv4";
	case 125:
		return "FIRE";
	case 126:
		return "CRTP";
	case 127:
		return "CRUDP";
	case 128:
		return "SSCOPMCE9)";
	case 129:
		return "IPLT";
	case 130:
		return "SPS";
	case 131:
		return "PIPE";
	case 132:
		return "SCTP";
	case 133:
		return "FC";
	case 134:
		return "RSVP-E2E-IGNORE";
	case 135:
		return "MobilityHeader";
	case 136:
		return "UDPLite";
	case 137:
		return "MPLS-in-IP";
	case 138:
		return "manet";
	case 139:
		return "HIP";
	case 140:
		return "Shim6";
	case 141:
		return "WESP";
	case 142:
		return "ROHC";
	default:
		return "";
	}
}
