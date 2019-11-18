#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

typedef int(*char_predict)(int);

typedef struct char_statistic {
        char* name;
        char_predict predict;
        int count;
} char_statistic_t;

int char_predict_other(int);

int main() {

        char_statistic_t stat_array[] = {
                {"alpha", isalpha, 0},
                {"blank", isblank, 0},
                {"digit", isdigit, 0},
                {"other", char_predict_other, 0}
        };

        int stat_num = sizeof(stat_array) / sizeof(stat_array[0]);
        int ch;
        while ((ch = fgetc(stdin)) != EOF) {
                for (int i = 0; i < stat_num; i++) {
                        char_statistic_t stat = stat_array[i];
                        if (stat.predict(ch)) {
                                stat_array[i].count++;
                                break;
                        }
                }
        }

        for (int i = 0; i < stat_num; i++) {
                char_statistic_t stat = stat_array[i];
                printf("%s: %d\n", stat.name, stat.count);
        }

        return 0;
}

int char_predict_other(int ch) {
        return 1;
}
