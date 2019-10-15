#pragma once

void io_halt(void);
void io_write_byte(int address, int value);
void io_cli(void);
void io_sti(void);
int io_in8(int port);
int io_in16(int port);
int io_in32(int port);
void io_out8(int port, int data);
void io_out16(int port, int data);
void io_out32(int port, int data);

void io_lidt(int address, int size);
