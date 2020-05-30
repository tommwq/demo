#include <stdio.h>

unsigned int left_most_bit(unsigned int x) {
    // ����x=0001010...������x����y=0001111...��Ȼ��ͨ��(y+1) >> 1�õ���������y+1=0��ֱ�ӷ���0x80000000��

    unsigned int y = x;
    y |= (y >> 1);
    y |= (y >> 2);
    y |= (y >> 4);
    y |= (y >> 8);
    y |= (y >> 16);

    (y = (y + 1) >> 1) || (y = 0x80000000);
    return y;
}

int main() {
    printf("%x %x\n", 0xff00, left_most_bit(0xff00));
    printf("%x %x\n", 0x6600, left_most_bit(0x6600));
    return 0;
}
