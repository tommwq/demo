/**
 *
 * PrintSegmentDescriptor
 *
 * 打印段描述符。
 *
 * 2019年05月24日 Wang Qian
 */

/**
 * 示例：
 * PrintSegmentDescriptor.exe 0x123456
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>

#include "descriptor.h"

int main(int argc, const char *argv[]) {
    if (argc != 4) {
        fprintf(stdout, "usage: %s [task_gate|interrupt_gate|trap_gate|segment_descriptor] high32bit low32bit\n", argv[0]);
        return -1;
    }

    const char* descriptor_type_string = argv[1];
    enum DescriptorType descriptor_type;
    if (strcmp(descriptor_type_string, "task_gate") == 0) {
        descriptor_type = TaskGate;
    } else if (strcmp(descriptor_type_string, "interrupt_gate") == 0) {
        descriptor_type = InterruptGate;    
    } else if (strcmp(descriptor_type_string, "trap_gate") == 0) {
        descriptor_type = TrapGate;    
    } else if (strcmp(descriptor_type_string, "segment_descriptor") == 0) {
        descriptor_type = SegmentDescriptor;    
    } else {
        fprintf(stdout, "error: invalid descriptor type.");
        return -1;
    } 

    uint32_t high, low;
    if (sscanf(argv[2], "%x", &high) != 1) {
        fprintf(stderr, "fail to read high bits. \n");
        return -1;
    }
    if (sscanf(argv[3], "%x", &low) != 1) {
        fprintf(stderr, "fail to read low bits. \n");
        return -1;
    }

    descriptor_t encoded_descriptor = ((uint64_t)(high) << 32) + ((uint64_t)(low));
    struct Descriptor descriptor;

    enum OperationStatus result = initialize_descriptor(&descriptor,
                                                        encoded_descriptor,
                                                        descriptor_type);

    if (result == Failure) {
        fprintf(stderr, "fail to initialize struct Descriptor.\n");
        return -1;
    }
    
    print_descriptor(&descriptor, stdout);
    return 0;
}
