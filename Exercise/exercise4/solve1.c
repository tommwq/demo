#include <stdio.h>

int day_of_year(int year, int month, int day_of_month);

int main() {
        int year;
        int month;
        int day;
        
        scanf("%d %d %d", &year, &month, &day);
        printf("%d\n", day_of_year(year, month, day));
        return 0;
}

int day_of_year(int year, int month, int day_of_month) {
        static int days_in_month[] = {
                31, 29, 31, 30,
                31, 30, 31, 31,
                30, 31, 30, 31
        };

        if (month > 12 || month < 1) {
                return -1;
        }
        
        if (day_of_month < 1 || day_of_month > days_in_month[month-1]) {
                return -1;
        }

        int leap_year = (year % 400 == 0) || ((year % 100 != 0) && (year % 4 == 0));
        if ((!leap_year) && month == 2 && day_of_month > days_in_month[1] - 1) {
                return -1;
        }

        if (year == 1752 && month == 9 && day_of_month >= 3 && day_of_month <= 13) {
                return -1;
        }

        int day_of_year = 0;
        for (int i = 0; i < month - 1; i++) {
                day_of_year += days_in_month[i];
        }
        day_of_year += day_of_month;
        return day_of_year;
}
