/*
  BasicDataLengthCheck.c
  检查系统的数据类型（LLP）。
*/

#include <stdio.h>
#include <stdlib.h>

/*
  type    LP64    ILP64   LLP64   ILP32   LP32
  char    8       8       8       8       8
  short   16      16      16      16      16
  int     32      64      32      32      16
  long    64      64      32      32      32
  long long               64
  *void   64      64      64      32      32
  */

#define BYTE_SIZE 8
#define NUM 6

/* char, short, int, long, long long, *void, NAME */
struct BasicType {
    size_t len[NUM];
    char *name;
};

int main(void){
    size_t i, j;
    struct BasicType data;
    struct BasicType data_type[] = {
        {{ 8, 16, 32, 64, 0, 64 },  "LP64"  },
        {{ 8, 16, 64, 64, 0, 64 },  "ILP64" },
        {{ 8, 16, 32, 32, 64, 64 }, "LLP64" },
        {{ 8, 16, 32, 32, 0, 32 },  "ILP32" },
        {{ 8, 16, 16, 32, 0, 32 },  "LP32"  },
    };
    // print basic data type length
    printf("%-18s = %u\n", "sizeof(char)", sizeof(char) * BYTE_SIZE);
    printf("%-18s = %u\n", "sizeof(short)", sizeof(short) * BYTE_SIZE);
    printf("%-18s = %u\n", "sizeof(int)", sizeof(int) * BYTE_SIZE);
    printf("%-18s = %u\n", "sizeof(long)", sizeof(long) * BYTE_SIZE);
    printf("%-18s = %u\n", "sizeof(long long)", sizeof(long long) * BYTE_SIZE);
    printf("%-18s = %u\n", "sizeof(void*)", sizeof(void*) * BYTE_SIZE);
    printf("\n");

    data.len[0] = sizeof(char) * BYTE_SIZE;
    data.len[1] = sizeof(short) * BYTE_SIZE;
    data.len[2] = sizeof(int) * BYTE_SIZE;
    data.len[3] = sizeof(long) * BYTE_SIZE;
    data.len[4] = sizeof(long long) * BYTE_SIZE;
    data.len[5] = sizeof(void*) * BYTE_SIZE;
    data.name = NULL;

    for (i = 0; i < sizeof(data_type) / sizeof(data_type[0]); ++i){
        for (j = 0; j < NUM; ++j){
            if (data_type[i].len[j] != 0 && data_type[i].len[j] != data.len[j]){
                break;
            }
        }

        if (j == NUM){
            data.name = data_type[i].name;
            break;
        }
    }
    printf("\n%s\n", data.name == NULL ? "unknown" : data.name);
    return 0;
}
