#include "util.h"
#include "type.h"

void draw_point(short x, short y, int color);
void draw_line(short x1, short y1, short x2, short y2, int color);
void draw_rectangle(short x1, short y1, short x2, short y2, int color);

void main(void) {
        ushort *video_buffer = (ushort*) 0xE0000000;

        for (short i = 0; i < 300; i++) {
                draw_point(i, i, 0x07ff);
        }

        /* short length = 1000; */
        // for (short i = 0; i < 8000; i++) {
                /* draw_point(i, 0, 0x07ff); */
                /* draw_point(i, 1, 0x07ff); */
                /* draw_point(i, 2, 0x07ff); */
                // draw_line(0, i, 800, i, 0x07ff);
        // }

        // draw_line(0, 0, 800, 0, 0x07ff);
        // draw_rectangle(0, 0, 800, 600, 0x07ff);

        /* for (short i = 0; i < 600; i++) { */
        /*         draw_point(2, i, 0x07ff); */
        /* } */


        /* draw_line(0, 0, 800, 600, 0x07ff); */
        /* draw_line(0, 300, 800, 300, 0x07ff); */
        /* draw_line(400, 0, 400, 600, 0x07ff); */
        
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

short min(short x, short y) {
        return x > y ? y : x;
}

short max(short x, short y) {
        return x < y ? y : x;
}

void draw_line(short x1, short y1, short x2, short y2, int color) {
        short i;
        short step = x1 < x2 ? 1 : -1;
        
        /* if (x1 == x2) { */
        /*         a = min(y1, y2); */
        /*         b = max(y1, y2); */
        /*         for (i = a; i <= b; i++) { */
        /*                 draw_point(x1, i, color); */
        /*         } */
        /*         return; */
        /* } */

        if (y1 == y2) {
                for (i = x1; i != x2; i += step) {
                        draw_point(i, y1, color);
                }
                return;
        }

        /* float slope = (y2 - y1) / (x2 - x1); */
        /* for (i = x1; i != x2; i += step) { */
        /*         draw_point(i, (i - x1) * slope + y1, color); */
        /* } */
}

void draw_point(short x, short y, int color) {
        short offset = y * 800 + x;
        short *video_buffer = (short*) 0xE0000000;
        video_buffer[offset] = 0x07ff;
}

void draw_rectangle(short x1, short y1, short x2, short y2, int color) {
        short a = min(x1, x2);
        short b = max(x1, x2);
        short c = min(y1, y2);
        short d = min(y1, y2);

        for (short i = c; i <= d; i++) {
                draw_line(a, i, b, i, color);
        }
}
