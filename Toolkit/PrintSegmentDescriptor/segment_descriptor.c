#include "segment_descriptor.h"

#include <stdlib.h>
#include <stdint.h>

const uint8_t SYSTEM_DESCRIPTOR = 0;
const uint8_t CODE_OR_DATA_DESCRIPTOR = 1;
const uint8_t SEGMENT16 = 0;
const uint8_t SEGMENT32 = 1;
const uint8_t GRANULARITY_BYTE = 0;
const uint8_t GRANULARITY_4KB = 1;

struct SegmentDescriptor_internal {
    segment_descriptor_t segment_descriptor;
};

static uint32_t _base_address(const SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 16) & 0x00FFFFFF) | (descriptor >> 56);
}

static uint32_t _segment_limit(const SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return (descriptor & 0x0000FFFF) | ((descriptor >> 48) & 0x0F);
}

static uint8_t _descriptor_type(const SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 44) & 0x01);
}

static uint8_t _descriptor_privilege_level(const SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 45) & 0x03);
}

static uint8_t _segment_type(const SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 40) & 0x0F);
}

static uint8_t _is_present(const SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 47) & 0x01);
}

static uint8_t _available_for_system_software(const struct SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 52) & 0x01);
}

static uint8_t _operation_size(const struct SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 54) & 0x01);
}

static uint8_t _granularity(const struct SegmentDescriptor* sd) {
    uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 55) & 0x01);
}

static uint8_t _is_64bit_code_segment(const struct SegmentDescriptor* sd) {
   uint64_t descriptor = sd->internal->segment_descriptor;
    return ((descriptor >> 53) & 0x01);
}

int initialize_segment_descriptor(SegmentDescriptor* sd, segment_descriptor_t segment_descriptor) {
    if (sd == NULL) {
        return 0;
    }
    
    sd->internal = (SegmentDescriptor_internal*) malloc(sizeof(SegmentDescriptor_internal));
    if (sd->internal == NULL) {
        return 0;
    }

    sd->internal->segment_descriptor = segment_descriptor;
    sd->base_address = _base_address;
    sd->segment_limit = _segment_limit;
    sd->descriptor_type = _descriptor_type;
    sd->segment_type = _segment_type;
    sd->descriptor_privilege_level = _descriptor_privilege_level;
    sd->is_present = _is_present;
    sd->available_for_system_software = _available_for_system_software;
    sd->operation_size = _operation_size;
    sd->granularity = _granularity;
    sd->is_64bit_code_segment = _is_64bit_code_segment;
    return 1;
}

void destroy_segment_descriptor(SegmentDescriptor sd) {
    free(sd.internal);
}
