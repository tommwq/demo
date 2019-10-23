#include "util.h"
#include "type.h"

void draw_point(short x, short y, int color);

void main(void) {

        short length = 100;
        for (short i = 0; i < length; i++) {
                for (short j = 0; j < length; j++) {
                        draw_point(i, j, 0xffffff);
                }
        }
        
        while (1) {
                io_halt();
        }
}

short to_svga_color(int color) {
        int r = color >> 16;
        int g = (color >> 8) & 0xff;
        int b = color & 0xff;

        r >>= 3;
        g >>= 2;
        b >>= 3;

        return (r << 11) + (g << 5) + b;
}

void draw_point(short x, short y, int color) {
        short offset = y * 1600 + x;
        // short page_number = offset >> 15;
        short *video_buffer = (short*) 0xa0000000;
        video_buffer[offset] = color; // to_svga_color(color);
}
