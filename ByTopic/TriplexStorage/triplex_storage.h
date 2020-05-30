#pragma once

#include <stdint.h>

#define REPLICA_COUNT 3
#define MAX_FILENAME_LENGTH 4096
#define PAGE_SIZE 64

struct TriplexStorage;

// ������򿪴洢�ռ�
struct TriplexStorage* open_triplex_storage(const char* directory, uint32_t replica_storage_size);

// дҳ
int write_page(struct TriplexStorage* storage, uint32_t page_number, const char page[PAGE_SIZE]);

// ��ҳ
int read_page(struct TriplexStorage* storage, uint32_t page_number, char page_buffer[PAGE_SIZE]);

// �رմ洢�ռ�
void close_triple_storage(struct TriplexStorage* storage);

