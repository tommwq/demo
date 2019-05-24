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

#include "segment_descriptor.h"


int main(int argc, char *argv[]) {
    if (argc == 1) {
        fprintf(stdout, "usage: %s 0x0123456789ABCDEF", argv[0]);
        return -1;
    }

    segment_descriptor_t segment_descriptor;
    if (sscanf(argv[1], "%llx", &segment_descriptor) != 1) {
        perror("fail to read segment descriptor");
        return -1;
    }


    struct SegmentDescriptor sd;
    int result = initialize_segment_descriptor(&sd, segment_descriptor);
    if (result == 0) {
        fprintf(stderr, "fail to create segment descriptor");
        return -1;
    }

    fprintf(stdout, "segment descriptor\n");
    uint32_t base_address = sd.base_address(&sd);
    uint32_t segment_limit = sd.segment_limit(&sd);
    uint8_t descriptor_type = sd.descriptor_type(&sd);
    uint8_t segment_type = sd.segment_type(&sd);
    uint8_t descriptor_privilege_level = sd.descriptor_privilege_level(&sd);
    uint8_t is_present = sd.is_present(&sd);
    uint8_t available_for_system_software = sd.available_for_system_software(&sd);
    uint8_t operation_size = sd.operation_size(&sd);
    uint8_t granularity = sd.operation_size(&sd);
    uint8_t is_64bit_code_segment = sd.is_64bit_code_segment(&sd);
    
    fprintf(stdout, "base address:    0x%08x %d\n", base_address, base_address);
    fprintf(stdout, "segment limit:   0x%08x %d\n", segment_limit, segment_limit);
    fprintf(stdout, "descriptor type: %s\n",
            (descriptor_type == SYSTEM_DESCRIPTOR)? "system" :
            (descriptor_type == CODE_OR_DATA_DESCRIPTOR)? "code or data" : "invalid");
    fprintf(stdout, "segment type:    %s %d\n",
            segment_description(descriptor_type, segment_type),
            segment_type);
    fprintf(stdout, "privilege level: %d\n", descriptor_privilege_level);
    fprintf(stdout, "present:         %d\n", is_present);
    fprintf(stdout, "AVL:             %d\n", available_for_system_software);
    fprintf(stdout, "operation size:  %d\n", operation_size);
    fprintf(stdout, "granularity:     %s\n", (granularity == GRANULARITY_BYTE) ? "byte" :
            (granularity == GRANULARITY_4KB) ? "4kb" : "invalid");
    fprintf(stdout, "64 bit code seg: %d\n", is_64bit_code_segment);
        
    destroy_segment_descriptor(sd);

    return 0;
}
