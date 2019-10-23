#include "util.h"
#include "type.h"
#include "color.h"

void draw_box(int address, int screen_width, int color, int left, int top, int right, int bottom);
void draw_pixel(int address, int screen_width, int color, int x, int y);
void init_palette();
void fill_memory(uint address, uint size, uchar byte);
void fill_screen(int color);

void main(void) {
        init_palette();

        fill_screen(1);
        
        int video_buffer = 0xa0000;
        draw_box(video_buffer, 320, 2, 20, 20, 120, 120);
        draw_box(video_buffer, 320, 3, 70, 50, 170, 150);
        draw_box(video_buffer, 320, 4, 120, 80, 220, 180);
        
        while (1) {
                io_halt();
        }
}

void fill_screen(int color) {
        for (int i = 0x0a0000; i <= 0x0affff; i++) {
                io_write_byte(i, color);
        }
}

// 设置320x200 VGA的调色板，不会调用cli和sti。
// not work
void init_palette() {
        uint palette[16] = {
                black,
                light_bright_blue,
                dark_blue,
                bright_red,
                white,
                dark_purple,
                bright_green,
                bright_grey,
                light_dark_blue,
                bright_yellow,
                dark_red,
                dark_grey,
                bright_blue,
                dark_green,
                bright_purple,
                dark_yellow
        };

        int number_port = 0x03c8;
        int color_port = 0x03c9;

        io_out8(number_port, 0);
        for (int i = 0; i < 15; i++) {
                io_out8(color_port, vga_red(palette[i]));
                io_out8(color_port, vga_green(palette[i]));
                io_out8(color_port, vga_blue(palette[i]));
        }
}

void fill_memory(uint address, uint size, uchar byte) {
        for (int i = 0; i < size; i++, address++) {
                io_write_byte(address, byte);
        }
}

void draw_box(int address, int screen_width, int color, int left, int top, int right, int bottom) {

        char *buffer = (char *) address;

        for (int y = top; y <= bottom; y++) {
                for (int x = left; x <= right; x++) {
                        buffer[y * screen_width + x] = color;
                }
        }
}

void draw_pixel(int address, int screen_width, int color, int x, int y) {
        char *buffer = (char *) address;
        buffer[y * screen_width + x] = color;
}
