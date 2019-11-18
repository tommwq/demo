#include <stdio.h>

int main() {
        int indent;
        int star;
        int flip = 1;
        int row = 7;
        int half = row / 2;
        
        for (int i = 0; i < row; i++) {
                indent = (3 - i) * flip;
                star = (3 - indent)*2 + 1;

                for (int j = 0; j < indent; j++) {
                        printf("%c", ' ');
                }

                for (int j = 0; j < star; j++) {
                        printf("%c", '*');
                }
                printf("\n");

                if (i == half) {
                        flip = -1;
                }
        }
        
        return 0;
}
