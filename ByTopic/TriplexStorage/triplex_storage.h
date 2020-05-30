#pragma once

#include <stdint.h>

#define REPLICA_COUNT 3
#define MAX_FILENAME_LENGTH 4096
#define PAGE_SIZE 64

struct TriplexStorage;

// 建立或打开存储空间
struct TriplexStorage* open_triplex_storage(const char* directory, uint32_t replica_storage_size);

// 写页
int write_page(struct TriplexStorage* storage, uint32_t page_number, const char page[PAGE_SIZE]);

// 读页
int read_page(struct TriplexStorage* storage, uint32_t page_number, char page_buffer[PAGE_SIZE]);

// 关闭存储空间
void close_triple_storage(struct TriplexStorage* storage);

