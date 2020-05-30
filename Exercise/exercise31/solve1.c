#include <stdio.h>
#include <stdlib.h>

int main() {

        char *weekday[] = {
                "sunday",
                "monday",
                "tuesday",
                "wednesday",
                "thursday",
                "friday",
                "saturday"
        };

        char buf[3] = { '0' };
        int len = 0;
        int matched_count = 7;
        char *matched = "none";
        
        while (matched_count > 1) {
                matched_count = 0;
                matched = "none";
                buf[len++] = getchar();
                for (int i = 0; i < 7; i++) {
                        if (strncmp(weekday[i], buf, len) == 0) {
                                matched_count++;
                                matched = weekday[i];
                        }
                }

                if (matched_count == 0) {
                        break;
                }
        }

        printf("%s\n", matched);
        
        return 0;
}
