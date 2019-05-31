#include "descriptor.h"

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>

const char* InvalidString = "invalid";
const char* ValidString = "valid";

const char* TrueString = "true";
const char* FalseString = "false";

const char* SetString = "set";
const char* ClearString = "clear";

const char* Size16BitString = "16-bit";
const char* Size32BitString = "32-bit";

enum Mode {
           _32Bit, IA32e
};

enum SegmentDescriptorType get_segment_type(uint8_t segment_type, uint8_t descriptor_type) {
    if (descriptor_type == 0) {
        return System;
    }

    return (segment_type > 7) ? Code : Data;
}

const char* get_segment_type_name(uint8_t segment_type, uint8_t descriptor_type) {
    static const char* name_table[] = {"system", "code", "", "data"};

    return name_table[get_segment_type(segment_type, descriptor_type)];
}

const char* get_descriptor_type_name(uint8_t segment_type, uint8_t descriptor_type, enum Mode mode) {
    static const char* code_and_date_name[16] =  {"Read-Only",
                                                  "Read-Only, accessed",
                                                  "Read/Write",
                                                  "Read/Write, accessed",
                                                  "Read-Only, expand-down",
                                                  "Read-Only, expand-down, accessed",
                                                  "Read/Write, expand-down",
                                                  "Read/Write, expand-down, accessed",
                                                  "Execute-Only",
                                                  "Execute-Only, accessed",
                                                  "Execute/Read",
                                                  "Execute/Read, accessed",
                                                  "Execute-Only, conforming",
                                                  "Execute-Only, conforming, accessed",
                                                  "Execute/Read-Only, conforming",
                                                  "Execute/Read-Only, conforming, accessed"};
    
    static const char* system_name[2][16] = {{"Reserved",
                                              "16-bit TSS (Available)",
                                              "LDT",
                                              "16-bit TSS (Busy)",
                                              "16-bit Call Gate",
                                              "Task Gate",
                                              "16-bit Interrupt Gate",
                                              "16-bit Trap Gate",
                                              "Reserved",
                                              "32-bit TSS (Available)",
                                              "Reserved",
                                              "32-bit TSS (Busy)",
                                              "32-bit Call Gate",
                                              "Reserved",
                                              "32-bit Interrupt Gate",
                                              "32-bit Trap Gate"},
                                             {"Upper 8 byte of an 1G-byte descriptor",
                                              "Reserved",
                                              "LDT",
                                              "Reserved",
                                              "Reserved",
                                              "Reserved",
                                              "Reserved",
                                              "Reserved",
                                              "Reserved",
                                              "64-bit TSS (Available)",
                                              "Reserved",
                                              "64-bit TSS (Busy)",
                                              "64-bit Call Gate",
                                              "Reserved",
                                              "64-bit Interrupt Gate",
                                              "64-bit Trap Gate"}};

    if (segment_type > 15) {
        return "";
    }
    
    switch (descriptor_type) {
    case System:
        return system_name[mode & 0x01][segment_type];
    case Code: // fall through
    case Data:
        return code_and_date_name[segment_type];
    default:
        return "";
    }
}

int parse_task_gate(descriptor_t descriptor, struct TaskGate* task_gate) {
    task_gate->is_valid = (((descriptor >> 40) & 0x1F) == 0x05);
    task_gate->tss_segment_selector = (descriptor >> 16) & 0xFFFF;
    task_gate->descriptor_privilege_level = (descriptor >> 45) & 0x03;
    task_gate->segment_present_flag = (descriptor >> 47) & 0x01;
    return Success;
}

void print_task_gate(const struct TaskGate* task_gate, FILE* file) {
    static const char* format = "task gate (%s)\n"
        "tss segment selector:       0x%08x %d\n"
        "descriptor privilege level: 0x%08x %d\n"
        "segment present flag:       0x%08x %d\n";

    fprintf(file,
            format,
            (task_gate->is_valid == True) ? ValidString : InvalidString,
            task_gate->tss_segment_selector,
            task_gate->tss_segment_selector,
            task_gate->descriptor_privilege_level,
            task_gate->descriptor_privilege_level,
            task_gate->segment_present_flag,
            task_gate->segment_present_flag);
}

int parse_interrupt_gate(descriptor_t descriptor, struct InterruptGate* interrupt_gate) {
    interrupt_gate->offset = ((descriptor >> 48) << 16) | (descriptor & 0xFFFF);
    interrupt_gate->segment_selector = (descriptor >> 16) & 0xFFFF;
    interrupt_gate->descriptor_privilege_level = (descriptor >> 45) & 0x03;
    interrupt_gate->segment_present_flag = (descriptor >> 47) & 0x01;
    interrupt_gate->size_of_gate = (descriptor >> 44) & 0x1;
    interrupt_gate->is_valid = (((descriptor >> 40) & 0x1F) == 0x06) || (((descriptor >> 40) & 0x1F) == 0x0E);
    return Success;
}

void print_interrupt_gate(const struct InterruptGate* interrupt_gate, FILE* file) {
    static const char* format = "interrupt gate (%s)\n"
        "segment selector:           0x%08x %d\n"
        "offset:                     0x%08x %d\n"
        "size of gate:               %d bits\n"
        "descriptor privilege level: 0x%08x %d\n"
        "segment present flag:       0x%08x %d\n";

    fprintf(file,
            format,
            (interrupt_gate->is_valid == True) ? ValidString : InvalidString,
            interrupt_gate->segment_selector,
            interrupt_gate->segment_selector,
            interrupt_gate->offset,
            interrupt_gate->offset,
            (interrupt_gate->size_of_gate == Size32Bit) ? 32 : 16,
            interrupt_gate->descriptor_privilege_level,
            interrupt_gate->descriptor_privilege_level,
            interrupt_gate->segment_present_flag,
            interrupt_gate->segment_present_flag);
}

int parse_trap_gate(descriptor_t descriptor, struct TrapGate* trap_gate) {
    trap_gate->offset = ((descriptor >> 48) << 16) | (descriptor & 0xFFFF);
    trap_gate->segment_selector = (descriptor >> 16) & 0xFFFF;
    trap_gate->descriptor_privilege_level = (descriptor >> 45) & 0x03;
    trap_gate->segment_present_flag = (descriptor >> 47) & 0x01;
    trap_gate->size_of_gate = (descriptor >> 44) & 0x1;
    trap_gate->is_valid = (((descriptor >> 40) & 0x1F) == 0x07) || (((descriptor >> 40) & 0x1F) == 0x0F);
    return Success;
}

void print_trap_gate(const struct TrapGate* trap_gate, FILE* file) {
    static const char* format = "trap gate (%s)\n"
        "segment selector:           0x%08x %d\n"
        "offset:                     0x%08x %d\n"
        "size of gate:               %d bits\n"
        "descriptor privilege level: 0x%08x %d\n"
        "segment present flag:       0x%08x %d\n";

    fprintf(file,
            format,
            (trap_gate->is_valid == True) ? ValidString : InvalidString,
            trap_gate->segment_selector,
            trap_gate->segment_selector,
            trap_gate->offset,
            trap_gate->offset,
            (trap_gate->size_of_gate == Size32Bit) ? 32 : 16,
            trap_gate->descriptor_privilege_level,
            trap_gate->descriptor_privilege_level,
            trap_gate->segment_present_flag,
            trap_gate->segment_present_flag);
}

int parse_segment_descriptor(descriptor_t descriptor, struct SegmentDescriptor* segment_descriptor) {
    segment_descriptor->segment_limit = (descriptor & 0xFFFF) | (((descriptor >> 48) & 0x07) << 16);
    segment_descriptor->base_address = ((descriptor >> 16) & 0xFFFF) | (((descriptor >> 32) & 0xFF) << 16) | ((descriptor << 48) << 24);
    segment_descriptor->segment_type = (descriptor >> 40) & 0x07;
    segment_descriptor->descriptor_type = (descriptor >> 43) & 0x01;
    segment_descriptor->descriptor_privilege_level = (descriptor >> 45) & 0x03;
    segment_descriptor->segment_present_flag = (descriptor >> 47) & 0x01;
    segment_descriptor->available_for_use_by_system_software = (descriptor >> 19) & 0x01;
    segment_descriptor->_64bit_code_segment_flag = descriptor & 0x0020000000000000;
    segment_descriptor->d_b_flag = descriptor & 0x0040000000000000;
    segment_descriptor->granularity = descriptor & 0x0080000000000000;
    return Success;
}

void print_segment_descriptor(const struct SegmentDescriptor* segment_descriptor, FILE* file) {
    static const char* format1 = "segment descriptor (%s)\n"
        "segment limit:              0x%08x %d\n"
        "base address:               0x%08x %d\n"
        "segment type:               %s\n";
    
    static const char* format2 = "descriptor privilege level: 0x%08x %d\n"
        "segment present flag:       0x%08x %d\n"
        "available for system:       %s\n"
        "64bit code segment:         %s\n"
        "D/B flag:                   %s (%s)\n"
        "granularity:                %s\n";
                
    fprintf(file,
            format1,
            ValidString,
            segment_descriptor->segment_limit,
            segment_descriptor->segment_limit,
            segment_descriptor->base_address,
            segment_descriptor->base_address,
            get_segment_type_name(segment_descriptor->segment_type,
                                  segment_descriptor->descriptor_type));

    enum SegmentDescriptorType segment_type = get_segment_type(segment_descriptor->segment_type,
                                                               segment_descriptor->descriptor_type);
                
    if (segment_type == System) {
        fprintf(file,
                "descriptor type:            32-bit Mode: %s\n"
                "                            IA-32e Mode: %s\n",
                get_descriptor_type_name(segment_descriptor->segment_type,
                                         segment_descriptor->descriptor_type,
                                         _32Bit),
                get_descriptor_type_name(segment_descriptor->segment_type,
                                         segment_descriptor->descriptor_type,
                                         IA32e));
    } else {
        fprintf(file,
                "descriptor type:            %s\n",
                get_descriptor_type_name(segment_descriptor->segment_type,
                                         segment_descriptor->descriptor_type,
                                         _32Bit));
    }
    
    fprintf(file, format2, 
            segment_descriptor->descriptor_privilege_level,
            segment_descriptor->descriptor_privilege_level,
            segment_descriptor->segment_present_flag,
            segment_descriptor->segment_present_flag,
            (segment_descriptor->available_for_use_by_system_software == True) ? TrueString : FalseString,
            (segment_descriptor->_64bit_code_segment_flag == True) ? TrueString : FalseString,
            (segment_descriptor->d_b_flag == True) ? SetString : ClearString,
            "TODO",
            (segment_descriptor->granularity == Size16Bit) ? Size16BitString : Size32BitString);
}

enum OperationStatus initialize_descriptor(struct Descriptor* descriptor,
                                           descriptor_t encoded_descriptor,
                                           enum descriptor_type type) {
    if (descriptor == NULL) {
        return Failure;
    }

    descriptor->encoded = encoded_descriptor;
    descriptor->type = type;

    switch (type) {
    case TaskGate:
        parse_task_gate(descriptor->encoded, &(descriptor->task_gate));
        break;
    case InterruptGate:
        parse_interrupt_gate(descriptor->encoded, &(descriptor->interrupt_gate));
        break;
    case TrapGate:
        parse_trap_gate(descriptor->encoded, &(descriptor->trap_gate));
        break;
    case SegmentDescriptor:
        parse_segment_descriptor(descriptor->encoded, &(descriptor->segment_descriptor));
        break;
    default:
        return Failure;
    }
    
    return Success;
}

void print_descriptor(const struct Descriptor* descriptor, FILE* file) {
    if (descriptor == NULL || file == NULL) {
        return;
    }
    
    switch (descriptor->type) {
    case TaskGate:
        print_task_gate(&(descriptor->task_gate), file);
        break;
    case InterruptGate:
        print_interrupt_gate(&(descriptor->interrupt_gate), file);
        break;
    case TrapGate:
        print_trap_gate(&(descriptor->trap_gate), file);
        break;
    case SegmentDescriptor:
        print_segment_descriptor(&(descriptor->segment_descriptor), file);
        break;
    default:
        break;
    }
}
