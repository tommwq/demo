#pragma once

#include <stdint.h>
#include <stdio.h>

typedef uint64_t descriptor_t;

enum OperationStatus {
                      Success = 0,
                      Failure
};

enum Bool {
           False = 0,
           True
};

enum DescriptorType {
                     TaskGate,
                     InterruptGate,
                     TrapGate,
                     SegmentDescriptor
};

enum SizeOfSegment {
                    Size16Bit = 0,
                    Size32Bit
};

enum SegmentDescriptorType {
                     System = 0,
                     Code,
                     Data = 3
};

struct TaskGate {
    uint8_t is_valid;
    uint16_t tss_segment_selector;
    uint8_t segment_present_flag;
    uint8_t descriptor_privilege_level;
};

struct InterruptGate {
    uint8_t is_valid;
    uint32_t offset;
    uint16_t segment_selector;
    uint8_t segment_present_flag;
    uint8_t descriptor_privilege_level;
    uint8_t size_of_gate;
};

struct TrapGate {
    uint8_t is_valid;
    uint32_t offset;
    uint16_t segment_selector;
    uint8_t segment_present_flag;
    uint8_t descriptor_privilege_level;
    uint8_t size_of_gate;
};

struct SegmentDescriptor {
    uint32_t segment_limit;
    uint32_t base_address;
    uint8_t segment_type;
    uint8_t descriptor_type;
    uint8_t segment_present_flag;
    uint8_t descriptor_privilege_level;
    uint8_t available_for_use_by_system_software;
    uint8_t _64bit_code_segment_flag;
    uint8_t d_b_flag;
    uint8_t granularity;
};

struct Descriptor {
    descriptor_t encoded;
    enum descriptor_type type;
    union {
        struct TaskGate task_gate;
        struct InterruptGate interrupt_gate;
        struct TrapGate trap_gate;
        struct SegmentDescriptor segment_descriptor;
    };
};

enum OperationStatus initialize_descriptor(struct Descriptor* descriptor,
                                           descriptor_t encoded_descriptor,
                                           enum descriptor_type type);

void print_descriptor(const struct Descriptor* descriptor, FILE* file);


