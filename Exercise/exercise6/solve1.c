#include <stdio.h>

void print_character(int character[9]);

int main() {
        /* 00111000; */
        /* 01101100; */
        /* 01000100; */
        /* 01000000; */
        /* 01000000; */
        /* 01000000; */
        /* 01000100; */
        /* 01001100; */
        /* 00111000; */

        int character[9] = {
                0x38, 0x6c, 0x44, 0x40,
                0x40, 0x40, 0x44, 0x4c,
                0x38
        };

        print_character(character);
        
        return 0;
}

void print_dot(int solid) {
        printf("%c", solid == 0 ? ' ' : '*');
}

void print_character(int character[9]) {
        for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 8; j++) {
                        int mask = 1 << (7 - j);
                        print_dot(character[i] & mask);
                }
                
                printf("\n");
        }
}
