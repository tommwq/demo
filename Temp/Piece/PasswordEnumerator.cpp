

#include <cstring>

void group(const char *buf, size_t len, size_t group_size){
    int *pos = new int[group_size]; 
    int i;
    for (i = 0; i < group_size; ++i){
        pos[i] = 0x0;
    }

    bool over(false);
    while (!over){
        for (i = 0; i < group_size; ++i){
            printf("%c", buf[pos[i]]);
        }
        printf("\n");

        int pos_sum = 0;
        for (i = 0; i < group_size; ++i){
            pos_sum += pos[i];
        }
        if (pos_sum == (len - 1) * group_size){
            over = true;
            break;
        }

        i = group_size - 1;
        while (i > -1){
            pos[i]++;
            if (pos[i] == len){
                pos[i] = 0;
                i--;
            } else {
                break;
            }
        }
    }

    delete[] pos;
}

int _tmain(int argc, _TCHAR* argv[])
{
    const char *LETTERS = "1234567890"
        "abcdefghijklmnopqrstuvwxyz"
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //"`,./;'[]\\-=~!@#$%^&*()_+|}{\":?><";

    const int MAX_LEN(16);
    for (int i = 1; i < MAX_LEN + 1; ++i){
        group(LETTERS, strlen(LETTERS), i);
    }

    return 0;
}

