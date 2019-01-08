#pragma once

#include <string_view>


std::string_view get_ip_protocol_name(int protocol);


typedef unsigned char byte;
typedef unsigned short word;
typedef unsigned int dword;

int const TransmissionControlProtocol = 6;

int const IPPacketLengthUnit = 4;

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
	byte length_and_reversed;
	byte reversed_and_flag;
	word window_size;
	word checksum;
	word urgent_pointer;
};
