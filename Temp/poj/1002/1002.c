/*
 * poj 1002
 * WA
 * 2014-05-05 20:08:10
 */
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct PhoneNumberEntry {
    int number;
    int count;
};

struct PhoneNumberBook {
    struct PhoneNumberEntry *entry;
    int size;
    int capacity;
};

// for qsort
int sort_phone_number(const void *lhs, const void *rhs){
    const struct PhoneNumberEntry *x, *y;
    x = (const struct PhoneNumberEntry *) lhs;
    y = (const struct PhoneNumberEntry *) rhs;
    if (x->number == y->number){
        return 0;
    } else if (x->number < y->number){
        return -1;
    }
    return 1;
}

int digit(char c){
    int d = -1;
    c = toupper(c);
    if ('0' <= c && c <= '9'){
        return c - '0';
    }
    if (c > 'Q'){
        c--;
    }
    d = (c - 'A') / 3 + 2;
    if (d < 2 || d > 9){
        d = -1;
    }
    return d;
}

int parse_phone_number(const char *str){
    int number = 0;
    for (; *str != '\0'; ++str){
        int d = digit(*str);
        if (d != -1){
            number = number * 10 + d;
        }
    }
    return number;
}

void print_phone_number(struct PhoneNumberEntry *entry){
    if (entry == NULL){
        return;
    }
    int x, y;
    x = (entry->number / 10000) % 1000;
    y = entry->number % 10000;
    printf("%03d-%04d %d\n", x, y, entry->count);
}

int add_phone_number(struct PhoneNumberBook *book, int number){
    static int step = 16;
    if (book == NULL){
        return -1;
    }
    int i;
    for (i = 0; i < book->size; ++i){
        if (book->entry[i].number == number){
            book->entry[i].count++;
            return 0;
        }
    }
    if (book->size < book->capacity){
        book->entry[book->size].count = 1;
        book->entry[book->size].number = number;
        book->size = book->size + 1;
        return 0;
    }
    int *buffer = (int *) calloc((book->capacity + step), sizeof(struct PhoneNumberEntry));
    if (buffer == NULL){
        return -1;
    }
    memcpy(buffer, book->entry, book->size * sizeof(struct PhoneNumberEntry));
    //free(book->entry);
    book->entry = (struct PhoneNumberEntry *) buffer;
    book->entry[book->size].number = number;
    book->entry[book->size].count = 1;
    book->size = book->size + 1;
    book->capacity += step;
    return 0;
}

/* sort phone numbers in a phone number book. */
void quick_sort(struct PhoneNumberEntry *entry, int size){
    if (entry == NULL){
        return;
    }
    if (size <= 1){
        return;
    }
    struct PhoneNumberEntry tmp;
    if (size == 2){
        memcpy(&tmp, entry, sizeof(*entry));
        memcpy(entry, entry + 1, sizeof(*entry));
        memcpy(entry + 1, &tmp, sizeof(*entry));
    }
    struct PhoneNumberEntry *pivot, *left, *right;
    int left_size, right_size;
    pivot = entry;
    for (left = pivot + 1, right = entry + (size - 1); left + 1 < right; ){
        for (; left < right && left->number < pivot->number; ++left){}
        for (; left < right && pivot->number < right->number; --right){}
        if (left == right || left + 1 == right){
            break;
        }
        memcpy(&tmp, left, sizeof(*left));
        memcpy(left, right, sizeof(*left));
        memcpy(right, &tmp, sizeof(*left));
        ++left;
        --right;
    }
    left_size = left - entry;
    right_size = size - left_size - 1;
    memcpy(&tmp, pivot, sizeof(*left));
    memcpy(pivot, left, sizeof(*left));
    memcpy(left, &tmp, sizeof(*left));
    quick_sort(entry, left_size);
    quick_sort(entry + left_size + 1, right_size);
}

int main(void){
    int count;
    if (scanf("%d", &count) != 1){
        return -1;
    }
    struct PhoneNumberBook book;
    book.size = 0;
    book.capacity = 0;
    book.entry = NULL;
    char buffer[16];
    while (count-- > 0){
        memset(buffer, 0x00, 16);
        scanf("%s", buffer);
        int phone_number = parse_phone_number(buffer);
        add_phone_number(&book, phone_number);
    }
    quick_sort(book.entry, book.size);
    int i;
    count = 0;
    for (i = 0; i < book.size; ++i){
        if (book.entry[i].count > 1){
            print_phone_number(book.entry + i);
            ++count;
        }
    }
    //free(book.entry);
    if (count == 0){
        printf("No duplicates.\n");
    }
    return 0;
}
