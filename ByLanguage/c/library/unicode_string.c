#include <memory.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <wchar.h>
#include "common.h"
#include "unicode_string.h"

struct Unicode_string {
    uint32_t length;
    wchar_t *data;
};

void unicode_string_release(Unicode_string ustring);

Unicode_string* unicode_string_create() {
    Unicode_string* ustring = (Unicode_string*) malloc(sizeof(Unicode_string));
    if (ustring == NULL) {
        return NULL;
    }

    ustring->length = 0;
    ustring->data = NULL;
    return ustring;
}

void unicode_string_delete(Unicode_string* ustring) {
    if (ustring == NULL) {
        return;
    }

    unicode_string_release(*ustring);
    free(ustring);
}

void unicode_string_release(Unicode_string ustring) {
    wchar_t *data = ustring.data;
    ustring.length = 0;
    ustring.data = NULL;
    free(data);
}

void unicode_string_assign(Unicode_string* ustring, const char *char_string) {
    Unicode_string new_string;
    new_string.length = 0;
    new_string.data = NULL;
    uint32_t length = strlen(char_string);
    unicode_string_resize(&new_string, length);
    for (int i = 0; i < length; i++) {
        unicode_string_set(&new_string, i, (wchar_t) char_string[i]);
    }
    unicode_string_swap(&new_string, ustring);
    unicode_string_release(new_string);
}

void unicode_string_assign_wchar(Unicode_string* ustring, const wchar_t *wchar_string) {
    Unicode_string new_string;
    new_string.length = 0;
    new_string.data = NULL;
    uint32_t length = wcslen(wchar_string);
    unicode_string_resize(&new_string, length);
    for (int i = 0; i < length; i++) {
        unicode_string_set(&new_string, i, wchar_string[i]);
    }
    unicode_string_swap(&new_string, ustring);
    unicode_string_release(new_string);
}

wchar_t* unicode_string_to_wchar(Unicode_string* ustring) {
    return ustring->data;
}

uint32_t unicode_string_length(Unicode_string* ustring) {
    return ustring->length;
}

void unicode_string_swap(Unicode_string* ustring1, Unicode_string* ustring2) {
    if (ustring1 == NULL || ustring2 == NULL) {
        panic();
    }

    Unicode_string tmp;
    tmp.length = ustring1->length;
    tmp.data = ustring1->data;
    ustring1->length = ustring2->length;
    ustring1->data = ustring2->data;
    ustring2->length = tmp.length;
    ustring2->data = tmp.data;
}

wchar_t unicode_string_get(Unicode_string* ustring, uint32_t position) {
    if (ustring == NULL) {
        panic();
    }
    
    uint32_t length = unicode_string_length(ustring);
    if (position >= length) {
        panic();
    }
    
    return ustring->data[position];
}

void unicode_string_set(Unicode_string* ustring, uint32_t position, wchar_t character) {
    if (ustring == NULL) {
        panic();
    }

    uint32_t length = unicode_string_length(ustring);
    if (position >= length) {
        panic();
    }

    ustring->data[position] = character;
}

void unicode_string_resize(Unicode_string* ustring, uint32_t new_size) {
    
    if (ustring == NULL) {
        panic();
    }

    uint32_t length = unicode_string_length(ustring);
    if (new_size == 0) {
        unicode_string_release(*ustring);        
    }

    if (length == new_size) {
        return;
    }

    Unicode_string new_string;
    new_string.length = new_size;
    new_string.data = (wchar_t*) malloc(sizeof(wchar_t) * new_size + 1);
    if (new_string.data == NULL) {
        panic();
    }
    new_string.data[new_size] = L'\0';
    
    uint32_t size = length;
    if (new_size < length) {
        size = new_size;
    }

    int position = 0;
    while (position < size) {
        unicode_string_set(&new_string, position, ustring->data[position]);
        position++;
    }

    while (position < new_size) {
        unicode_string_set(&new_string, position, L'\0');
        position++;
    }

    unicode_string_swap(&new_string, ustring);
}
