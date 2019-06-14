#include "triplex_storage.h"

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const char* replica_names[] = {
                               "replica_1",
                               "replica_2",
                               "replica_3"
};

const char empty_page[PAGE_SIZE] = {0};

struct TriplexStorage {
    const char *directory;
    FILE* replica[REPLICA_COUNT];
};

// 释放存储空间（包括文件）。
void release_triplex_storage(struct TriplexStorage* storage);
// 打开副本文件
FILE* open_replica_file(const char *filename, uint32_t expected_file_size, uint32_t page_size);
// 生成副本文件名
void make_replica_filename(const char* directory, const char* replica_name, char buffer[MAX_FILENAME_LENGTH]);

// 建立或打开存储空间
struct TriplexStorage* open_triplex_storage(const char* directory, uint32_t replica_storage_size) {
    char filename_buffer[MAX_FILENAME_LENGTH];
    struct TriplexStorage *storage = NULL;
    int success = 0;
    
    do {
        if (strlen(directory) > (MAX_FILENAME_LENGTH - 10)) {
            break;
        }
        
        storage = (struct TriplexStorage*) malloc(sizeof(struct TriplexStorage));
        if (storage == NULL) {
            break;
        }

        memset(storage, '\0', sizeof(struct TriplexStorage));
        storage->directory = directory;

        int page_count = (int) ceil((double) replica_storage_size / (double) PAGE_SIZE);
        int adjusted_size = page_count * PAGE_SIZE;

        success = 1;
        for (int i = 0; i < REPLICA_COUNT; i++) {
            make_replica_filename(directory, replica_names[i], filename_buffer);
            storage->replica[i] = open_replica_file(filename_buffer, adjusted_size, PAGE_SIZE);
            if (storage->replica[i] == NULL) {
                success = 0;
                break;
            }
        }

    } while (0);

    if (success) {
        return storage;
    }

    release_triplex_storage(storage);
    return NULL;
}

// 写页
// TODO 做成可靠写
int write_page(struct TriplexStorage* storage, uint32_t page_number, const char page[PAGE_SIZE]) {
    if (storage == NULL) {
        return -1;
    }
    
    uint32_t offset = page_number * PAGE_SIZE;
    int success_count = 0;
    for (int i = 0; i < REPLICA_COUNT; i++) {
        if (fseek(storage->replica[i], offset, SEEK_SET) != 0) {
            continue;
        }
        
        if (fwrite(page, PAGE_SIZE, 1, storage->replica[i]) == 1) {
            success_count++;
        }
    }

    if (success_count > REPLICA_COUNT / 2) {
        return 0;
    }

    return -1;
}

// 读页
int read_page(struct TriplexStorage* storage, uint32_t page_number, char page_buffer[PAGE_SIZE]) {
    if (storage == NULL) {
        return -1;
    }

    char* read_buffer_list[REPLICA_COUNT];
    int same_copy_count[REPLICA_COUNT] = {0};
    int max_same_copy_index = 0;

    int success = 0;
    for (int i = 0; i < REPLICA_COUNT; i++) {
        char* page = (char*) malloc(PAGE_SIZE);
        if (page == NULL) {
            break;
        }
        read_buffer_list[i] = page;
    }
    
    uint32_t offset = page_number * PAGE_SIZE;
    int success_count = 0;
    for (int i = 0; i < REPLICA_COUNT; i++) {
        if (fseek(storage->replica[i], offset, SEEK_SET) != 0) {
            continue;
        }
        
        if (fread(read_buffer_list[i], PAGE_SIZE, 1, storage->replica[i]) != 1) {
            continue;
        }

        success_count++;
        int same_copy = 0;
        for (int j = i - 1; j > 0; j--) {
            if (memcmp(read_buffer_list[i], read_buffer_list[j], PAGE_SIZE) == 0) {
                same_copy++;
            }
        }
        same_copy_count[i] = same_copy;

        if (same_copy > same_copy_count[max_same_copy_index]) {
            max_same_copy_index = i;
        }
    }

    if (success_count > REPLICA_COUNT / 2) {
        memcpy(page_buffer, read_buffer_list[max_same_copy_index], PAGE_SIZE);
        success = 1;
    }

    for (int i = 0; i < REPLICA_COUNT; i++) {
        free(read_buffer_list[i]);
    }
    
    return (success - 1);
}

// 关闭存储空间
void close_triple_storage(struct TriplexStorage* storage) {
    if (storage == NULL) {
        return;
    }

    for (int i = 0; i <REPLICA_COUNT; i++) {
        if (storage->replica[i] != NULL) {
            fclose(storage->replica[i]);
        }
    }
    free(storage);
}

void release_triplex_storage(struct TriplexStorage* storage) {
    if (storage == NULL) {
        return;
    }
    
    const char* directory = storage->directory;
    close_triple_storage(storage);

    char filename_buffer[MAX_FILENAME_LENGTH];
    for (int i = 0; i < REPLICA_COUNT; i++) {
        make_replica_filename(directory, replica_names[i], filename_buffer);
    
        FILE* file = fopen(filename_buffer, "r");
        if (file == NULL) {
            // 存储文件没有建立，不需要删除。
            continue;
        }

        // 删除文件。
        fclose(file);
        if (remove(filename_buffer) != 0) {
            fprintf(stderr, "错误：释放存储空间失败。\n");
            fflush(stderr);
            abort();
        }
    }
}


FILE* open_replica_file(const char *filename, uint32_t expected_file_size, uint32_t page_size) {
    FILE* file = fopen(filename, "a+");
    if (file == NULL) {
        return NULL;
    }
    
    file = fopen(filename, "rb+");
    if (file == NULL) {
        return NULL;
    }

    if (fseek(file, 0, SEEK_END) != 0) {
        fclose(file);
        return NULL;
    }

    uint32_t file_size = (uint32_t) ftell(file);
    if (file_size > 0 && file_size != expected_file_size) {
        fclose(file);
        return NULL;
    }
    
    if (file_size == expected_file_size) {
        return file;
    }

    rewind(file);
    int page_count = expected_file_size / page_size;
    int success = 1;

    for (int i = 0; i < page_count; i++) {
        if (fwrite(empty_page, page_size, 1, file) != 1) {
            success = 0;
            break;
        }
    }

    if (!success) {
        fclose(file);
        return NULL;
    }

    fflush(file);
    return file;
}

void make_replica_filename(const char* directory, const char* replica_name, char buffer[MAX_FILENAME_LENGTH]) {
    buffer[0] = '\0';
    strncpy(buffer, directory, MAX_FILENAME_LENGTH);
    strncat(buffer, "/", MAX_FILENAME_LENGTH);
    strncat(buffer, replica_name, MAX_FILENAME_LENGTH);
}
