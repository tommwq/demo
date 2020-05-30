#include <stdio.h>

void print_score_level(int score);

int main() {

        int score;
        scanf("%d", &score);

        print_score_level(score);
        
        return 0;
}

void print_score_level(int score) {
        printf("%c\n", score >= 90 ? 'A' : score >= 60 ? 'B' : 'C');
}
