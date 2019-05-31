/**
 *
 * print_descriptor
 *
 * 打印Intel描述符。
 *
 * 2019年05月24日 Wang Qian
 * 2019年05月31日 Wang Qian
 */

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "descriptor.h"

int main(int argc, const char *argv[]) {
    if (argc != 4) {
        fprintf(stdout, "usage: %s [task_gate|interrupt_gate|trap_gate|segment_descriptor] high32bit low32bit\n", argv[0]);
        return -1;
    }

    struct {
        const char* type_name;
        enum DescriptorType type;
    } valid_type_table[] = {{"task_gate", TaskGate},
                            {"interrupt_gate", InterruptGate},
                            {"trap_gate", TrapGate},
                            {"segment_descriptor", SegmentDescriptor}};

    const char* type_name = argv[1];
    enum DescriptorType descriptor_type;
    int is_valid_type = 0;
    for (int i = 0; i < sizeof(valid_type_table) / sizeof(valid_type_table[0]); i++) {
        if (strcmp(type_name, valid_type_table[i].type_name) == 0) {
            is_valid_type = 1;
            descriptor_type = valid_type_table[i].type;
        }
    }

    if (!is_valid_type) {
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
