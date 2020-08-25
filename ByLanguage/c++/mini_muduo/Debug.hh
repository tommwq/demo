#pragma once

#include <iostream>

#define print_line() std::cout << __FILE__ << ":" << __LINE__ << std::endl

#define print_ptr(ptr) _print_ptr(#ptr, ptr)

#define _print_ptr(name, ptr) std::cout << __FILE__ << ":" << __LINE__ << " POINTER " << name << " " << ptr << std::endl
