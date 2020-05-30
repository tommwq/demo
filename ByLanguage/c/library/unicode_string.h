#pragma once

#include <stdint.h>

struct Unicode_string;
typedef struct Unicode_string Unicode_string;

// Create unicode string.
Unicode_string* unicode_string_create();

// Release underlying resources of a unicode string.
void unicode_string_delete(Unicode_string* ustring);

// Assign a string.
void unicode_string_assign(Unicode_string* ustring, const char *char_string);

// Assign a string.
void unicode_string_assign_wchar(Unicode_string* ustring, const wchar_t *wchar_string);

// Convert to wchar*.
wchar_t* unicode_string_to_wchar(Unicode_string* ustring);

// Get length of string.
uint32_t unicode_string_length(Unicode_string* ustring);

// Swap two strings.
void unicode_string_swap(Unicode_string* ustring1, Unicode_string* ustring2);

// Get character of a string.
wchar_t unicode_string_get(Unicode_string* ustring, uint32_t position);

// Set character of a string.
void unicode_string_set(Unicode_string* ustring, uint32_t position, wchar_t character);

// Resize a string.
void unicode_string_resize(Unicode_string* ustring, uint32_t new_size);
