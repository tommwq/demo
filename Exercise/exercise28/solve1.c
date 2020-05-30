#include <stdio.h>

int person_age(int person_number) {
        if (person_number <= 1) {
                return 10;
        }

        return 2 + person_age(person_number - 1);
}

int main() {
        printf("%d\n", person_age(5));
        return 0;
}
