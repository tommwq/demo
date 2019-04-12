#pragma once

class Storage {
public:
    unsigned long get_size();
    void move_to(unsigned long position);
    int read(void *destination, unsigned long length);
    int write(void *source, unsigned long length);
};
