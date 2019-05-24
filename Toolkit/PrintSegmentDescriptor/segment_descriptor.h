#pragma once

#include <stdint.h>

typedef uint64_t segment_descriptor_t;

const uint8_t SYSTEM_DESCRIPTOR;
const uint8_t CODE_OR_DATA_DESCRIPTOR;
const uint8_t SEGMENT16;
const uint8_t SEGMENT32;
const uint8_t GRANULARITY_BYTE;
const uint8_t GRANULARITY_4KB;

struct SegmentDescriptor_internal;
typedef struct SegmentDescriptor_internal SegmentDescriptor_internal;

struct SegmentDescriptor {
    uint32_t (*base_address)(const struct SegmentDescriptor* sd);
    uint32_t (*segment_limit)(const struct SegmentDescriptor* sd);
    uint8_t (*descriptor_type)(const struct SegmentDescriptor* sd);
    uint8_t (*segment_type)(const struct SegmentDescriptor* sd);
    uint8_t (*descriptor_privilege_level)(const struct SegmentDescriptor* sd);
    uint8_t (*is_present)(const struct SegmentDescriptor* sd);
    uint8_t (*available_for_system_software)(const struct SegmentDescriptor* sd);
    uint8_t (*operation_size)(const struct SegmentDescriptor* sd);
    uint8_t (*granularity)(const struct SegmentDescriptor* sd);
    uint8_t (*is_64bit_code_segment)(const struct SegmentDescriptor* sd);
    struct SegmentDescriptor_internal *internal;
};
typedef struct SegmentDescriptor SegmentDescriptor;


// 1: success 0: fail
int initialize_segment_descriptor(SegmentDescriptor* sd, segment_descriptor_t segment_descriptor);
//  Õ∑≈sd->internal£¨≤ª Õ∑≈sd°£
void destroy_segment_descriptor(SegmentDescriptor sd);
