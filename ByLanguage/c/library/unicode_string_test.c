#include <stdio.h>
#include "common.h"
#include "unicode_string.h"

int main() {

    Unicode_string* ustr = unicode_string_create();
    
    unicode_string_assign(ustr, "Hello, world!");
    wprintf(L"%s\n", unicode_string_to_wchar(ustr));

    unicode_string_assign(ustr, "OK");
    wprintf(L"%s\n", unicode_string_to_wchar(ustr));
            
    unicode_string_assign_wchar(ustr, L"Good");
    wprintf(L"%s\n", unicode_string_to_wchar(ustr));

    unicode_string_assign_wchar(ustr, L"你好");
    wprintf(L"%s\n", unicode_string_to_wchar(ustr));
    
    return 0;
}
