#include <stdio.h>

int is_set_bit_number_odd(unsigned int x) {
    // ��2nλ�����ĸ�λ�͵�λ����������㣬������ֵλ1��λ������ԭֵ��1λ����-2*k��
    unsigned int z = x >> 16;
    z ^= x;

    z >>= 8;
    z ^= x;

    z >>= 4;
    z ^= x;

    z >>= 2;
    z ^= x;

    z >>= 1;
    z ^= x;

    return z & 0x01;
}

int main() {
    printf("%d %d\n", 0x01, is_set_bit_number_odd(0x01));
    printf("%d %d\n", 0x03, is_set_bit_number_odd(0x03));
    return 0;
}
