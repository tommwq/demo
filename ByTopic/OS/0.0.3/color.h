#pragma once

#include "type.h"

#define black             0x000000
#define light_bright_blue 0x00ffff
#define dark_blue         0x000084
#define bright_red        0xff0000
#define white             0xffffff
#define dark_purple       0x000084
#define bright_green      0x00ff00
#define bright_grey       0xc6c6c6
#define light_dark_blue   0x008484
#define bright_yellow     0xffff00
#define dark_red          0x840000
#define dark_grey         0x848484
#define bright_blue       0x0000ff
#define dark_green        0x008400
#define bright_purple     0xff00ff
#define dark_yellow       0x848400

// 8-bit RGB转6-bit VGA
#define vga_red(x)   ((x) >> 16)
#define vga_green(x) ((x) >> 18)
#define vga_blue(x)  (x)
