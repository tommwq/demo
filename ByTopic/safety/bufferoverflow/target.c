// /GS-
// /GS�����__security_cookie������ջ��

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[]) {

    char buffer[8] = {0};
    strcpy(buffer, argv[1]);
    printf("%s\n", buffer);
    
    printf("ok\n");
    return 0;
}
