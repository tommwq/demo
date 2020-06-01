#pragma once

typedef enum Color {
    ColorBlack = 0,
    ColorBlue,
    ColorGreen,
    ColorCyan,
    ColorRed,
    ColorPurple,
    ColorBrown,
    ColorGray,
    ColorDarkGray,
    ColorLightBlue,
    ColorLightGreen,
    ColorLightCyan,
    ColorLightRed,
    ColorLightPurple,
    ColorYellow,
    ColorWhite
} Color;


typedef unsigned char uchar;
typedef unsigned short ushort;
typedef unsigned int uint;

void KernelStart();

void FillMemory(void *buffer, uint size, uchar value);
void CopyMemory(void *dest, void *src, uint size);
uint FindByte(void *start, uchar value);
void ForeverLoop();

// asm
void Halt();
void Nop();

typedef struct TeletypeConsole {
    uchar Buffer[25*80*2];
    uint NextLine;
    void *VideoBuffer;
} TeletypeConsole;

uint TeletypeConsole_Row(TeletypeConsole *console);
uint TeletypeConsole_Column(TeletypeConsole *console);
void TeletypeConsole_Render(TeletypeConsole *console);
void TeletypeConsole_Clear(TeletypeConsole *console);
void TeletypeConsole_WriteLine(TeletypeConsole *console, char *string);
TeletypeConsole TeletypeConsole_Create();

