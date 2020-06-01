// /GS- /Zi
// /GS会插入__security_cookie保护堆栈。

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void unsafe(char *data);

int main(int argc, char *argv[]) {
    
    char *shellcode = "\xeb\xfe";
    char *data = "\x01\x01\x02\x03\x04\x05\x06\x07"
        "\x08\x09\x0a\x0b\x0c\x0d\x0e\x0f"
        "\x11\x11\x12\x13";
    
    char *buffer = (char*) malloc(1024);
    strncpy(buffer, data, 1024);
    strncpy(buffer + 20, &shellcode, 4);
    unsafe(data);
    printf("ok\n");
    
    return 0;
}

void unsafe(char *data) {
    int buffer[4];
    strcpy(buffer, data);
}
