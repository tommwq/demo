/*
 * 1001.c
 * A solution for poj problem 1001.
 * 2014-05-03 23:20:34
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
void* my_malloc(int n){
    void *p = malloc(n);
    printf("%p %d\n", p, n);
    return p;
}

void* my_calloc(int n, int size){
    void *p = calloc(n, size);
    printf("%p %d\n", p, n * size);
    return p;
}

#define free(x) printf("%s:%d free %p\n", __FILE__, __LINE__, x); free((x))
#define malloc(n) (printf("%s:%d malloc ", __FILE__, __LINE__), my_malloc((n)))
#define calloc(n, size) (printf("%s:%d calloc ", __FILE__, __LINE__), my_calloc((n), (size)))
*/

struct Decimal {
    char *base;
    unsigned int length;
    unsigned int point_position;  // 0 for .xxx 1 for x.yyy 2 for xy.zzz length for xyz.
};

int decimal_copy(struct Decimal *x, const struct Decimal *y);

int fractional_length(const struct Decimal *x, unsigned int *length){
    if (x == NULL || length == NULL){
        return -1;
    }
    if (x->length < x->point_position){
        return -1;
    }
    *length = x->length - x->point_position;
    return 0;
}

// x += y. 0: success, -1: fail.
int decimal_add(struct Decimal *x, const struct Decimal *y){
    if (x == NULL || y == NULL){
        return -1;
    }
    if (x->length == 0){
        return decimal_copy(x, y);
    }
    if (y->length == 0){
        return 0;
    }
    unsigned int x_frac_len, y_frac_len;
    if (fractional_length(x, &x_frac_len) == -1){
        return -1;
    }
    if (fractional_length(y, &y_frac_len) == -1){
        return -1;
    }
    int frac_len = (x_frac_len < y_frac_len) ? y_frac_len : x_frac_len;
    int int_len = (x->point_position < y->point_position) ? 
        y->point_position : x->point_position;
    int buffer_len = int_len + frac_len + 1; // carry
    char *x_buffer = calloc(1, buffer_len);
    char *y_buffer = calloc(1, buffer_len);
    int padding;
    if (x->point_position < y->point_position){
        padding = y->point_position - x->point_position;   
        memcpy(x_buffer + padding + 1, x->base, x->length);
        memcpy(y_buffer + 1, y->base, y->length);
    } else {
        padding = x->point_position - y->point_position;   
        memcpy(x_buffer + 1, x->base, x->length);
        memcpy(y_buffer + padding + 1, y->base, y->length);
    } 
    int i;
    int carry = 0;
    for (i = buffer_len - 1; i >= 0; --i){
        char dec = x_buffer[i] + y_buffer[i] + carry;
        carry = 0;
        if (dec > 9){
          dec = dec % 10;
          carry = 1;
        }
        x_buffer[i] = dec;
    }
    int begin = 0, end = buffer_len;
    if (x_buffer[0] == 0){
        begin = 1;
    }
    while (frac_len > 0 && end > begin && x_buffer[end - 1] == 0){
        --end;
    }
    char *base = malloc(end - begin);
    memcpy(base, x_buffer + begin, end - begin);
    free(x->base);
    x->base = base;
    x->length = end - begin;
    x->point_position = int_len + ((begin == 0) ? 1 : 0);
    free(x_buffer);
    free(y_buffer);
    return 0;
}

// x = x * 10 ^ n
int decimal_shl(struct Decimal *x, unsigned int n){
    if (x == NULL){
        return -1;
    }
    if (x->point_position <= x->length - n){
        x->point_position += n;
        return 0;
    }
    int pad_len = x->point_position + n - x->length;
    char *base = calloc(1, x->length + pad_len);
    if (base == NULL){
        return -1;
    }
    memcpy(base, x->base, x->length);
    free(x->base);
    x->base = base;
    x->length += pad_len;
    x->point_position = x->length;
    return 0;
}

// x = x / 10 ^ n
int decimal_shr(struct Decimal *x, unsigned int n){
    if (x == NULL){
        return -1;
    }
    if (x->point_position >= n){
        x->point_position -= n;
        return 0;
    }
    int pad_len = n - x->point_position;
    char *base = calloc(1, x->length + pad_len);
    if (base == NULL){
        return -1;
    }
    memcpy(base + pad_len, x->base, x->length);
    free(x->base);
    x->base = base;
    x->length += pad_len;
    x->point_position = 0;
    return 0;
}

struct Decimal* new_decimal(){
    struct Decimal *x = malloc(sizeof(*x));
    if (x == NULL){
        return NULL;
    }
    x->base = NULL;
    x->length = 0;
    x->point_position = 0;
    return x;
}

void free_decimal(struct Decimal *x){
    if (x == NULL){
        return;
    }
    free(x->base);
    free(x);
}

// x *= y
// if len < 16, return x->base * y->base
int decimal_multiply(struct Decimal *x, const struct Decimal *y){
    if (x == NULL || y == NULL){
        return 0;
    }
    struct Decimal *result = new_decimal();
    struct Decimal *tmp[10];
    int i;
    for (i = 0; i < 10; ++i){
        tmp[i] = new_decimal();
        if (i > 0){
            decimal_copy(tmp[i], x);
        }
    }
    for (i = 1; i < 10; ++i){
        decimal_add(tmp[i], tmp[i - 1]);
    }
    struct Decimal *z = new_decimal();
    for (i = 0; i < y->length; ++i){
        decimal_copy(z, tmp[y->base[i]]);
        decimal_shl(z, y->length - 1 - i);
        decimal_add(result, z);
    }
    for (i = 1; i < 10; ++i){
        free_decimal(tmp[i]);
    }
    free_decimal(z);
    unsigned int y_frac_len;
    fractional_length(y, &y_frac_len);
    decimal_shr(result, y_frac_len);
    decimal_copy(x, result);
    free_decimal(result);
    return 0;
}

int decimal_copy(struct Decimal *x, const struct Decimal *y){
    if (x == NULL || y == NULL){
        return -1;
    }
    if (y->length == 0){
        free(x->base);
        x->base = NULL;
        x->length = 0;
        x->point_position = 0;
        return 0;
    }
    char *base = malloc(y->length);
    if (base == NULL){
        return -1;
    }
    memcpy(base, y->base, y->length);
    free(x->base);
    *x = *y;
    x->base = base;
    return 0;
}

int decimal_power(struct Decimal *x, unsigned int n){
    if (x == NULL){
        return -1;
    }
    if (n == 0){
        // ignore
        return 0;
    }
    if (n == 1){
        return 0;
    }
    struct Decimal *z = new_decimal();
    decimal_copy(z, x);
    if (n == 2){
        decimal_multiply(x, z);
        return 0;
    }
    int k = n / 2;
    decimal_power(x, k);
    struct Decimal *y = new_decimal();
    decimal_copy(y, x);
    decimal_multiply(x, y);
    if (n % 2 == 1){
        decimal_multiply(x, z);
    }
    free_decimal(z);
    free_decimal(y);
    return 0;
}

int decimal_display(const struct Decimal *x){
    if (x == NULL){
        return -1;
    }
    int i;
    for (i = 0; i < x->length; ++i){
        if (i == x->point_position){
            printf(".");
        }
        unsigned char c = x->base[i];
        printf("%d", c);
    }
    printf("\n");
    return 0;
}

int decimal_parse(struct Decimal *x, const char *str){
    if (x == NULL || str == NULL){
        return -1;
    }
    int str_len = strlen(str);
    int real_begin = -1;
    int point_pos = -1;
    int i;
    for (i = 0; i < str_len; ++i){
        if (real_begin == -1 && str[i] != '0'){
            real_begin = i;
        }
        if (str[i] == '.'){
            point_pos = i - real_begin;
        }
    }
    int len;
    char *base;
    if (real_begin == -1){
        len = 1;
        if ((base = calloc(1, len)) == NULL){
            return -1;
        }
        free(x->base);
        x->base = base;
        x->length = len;
        x->point_position = len;
        return 0;
    }
    int real_end = str_len;
    while (point_pos != -1 && real_end > real_begin && str[real_end - 1] == '0'){
        --real_end;
    } 
    len = real_end - real_begin;
    if (point_pos != -1){
        --len;
    }
    if ((base = calloc(1, len)) == NULL){
        return -1;
    }
    int str_off;
    for (i = 0, str_off = 0; i < len, str_off < str_len; ++i, ++str_off){
        if (str[real_begin + str_off] == '.'){
            --i;
            continue;
        }
        base[i] = str[real_begin + str_off] - '0';
    }
    free(x->base);
    x->base = base;
    x->length = len;
    x->point_position = (point_pos == -1) ? len : point_pos;
    return 0;
}

int main(void){
    int n;
    char buffer[7];
    buffer[6] = '\0';
    while (scanf("%s %d", buffer, &n) == 2){
        struct Decimal *decimal = new_decimal();
        if (decimal_parse(decimal, buffer) == -1){
            return -1;
        }
        if (decimal_power(decimal, n) == -1){
            return -1;
        }
        if (decimal_display(decimal) == -1){
            return -1;
        }
        free_decimal(decimal);
        memset(buffer, 0x0, 7);
    }
    return 0;
}
