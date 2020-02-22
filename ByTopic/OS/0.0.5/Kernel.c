#include "Kernel.h"

void KernelStart() {
    TeletypeConsole console = TeletypeConsole_Create();
    TeletypeConsole_Clear(&console);
    TeletypeConsole_WriteLine(&console, "KERNEL");

    Halt();
}

void FillMemory(void *buffer, uint size, uchar value) {
    uchar *array = (uchar*) buffer;
    for (uint i = 0; i < size; i++) {
        array[i] = value;
    }
}

void CopyMemory(void *dest, void *src, uint size) {
    uchar *d = (uchar*) dest;
    uchar *s = (uchar*) src;
    
    for (uint i = 0; i < size; i++) {
        d[i] = s[i];
    }
}

uint FindByte(void *start, uchar value) {
    uchar *base = (uchar*) start;
    uint offset;
    for(offset = 0; base[offset] != value; offset++) {}
    return offset;
}

uint TeletypeConsole_Row(TeletypeConsole *console) {
    return 25;
}

uint TeletypeConsole_Column(TeletypeConsole *console) {
    return 80;
}

void TeletypeConsole_Render(TeletypeConsole *console) {
    if (console->NextLine == 0) {
        return;
    }

    CopyMemory(console->VideoBuffer, console->Buffer, console->NextLine*80*2);
}

void TeletypeConsole_Clear(TeletypeConsole *console) {
    console->NextLine = 0;
    FillMemory(console->VideoBuffer, 25*80*2, 0x00);
}

void TeletypeConsole_WriteLine(TeletypeConsole *console, char *string) {
    uint length = FindByte(string, '\0');
    uint row = length / 80 + (length % 80 > 0);
    uint line_length = 80;
    char *start = string;
    uint next_line = console->NextLine;
        
    if (row >= 25) {
        start = string + (row - 25) * 80;
        row = 25;
        next_line = 0;
    } else if (console->NextLine + row > 25) {
        uint scroll = console->NextLine + row - 25;
        for (uint i = scroll; i <= console->NextLine; i++) {
            CopyMemory(console->Buffer + (i-1) * 80, console->Buffer + i*80, 80);
        }
        next_line = 25 - row;
    }

    for (uint i = 0; i < row; i++, next_line++, start += 80) {
        if (i == row - 1) {
            line_length = FindByte(start, '\0');
        }

        for (uint j = 0; j < line_length; j++) {
            console->Buffer[2*j] = start[j];
            console->Buffer[2*j+1] = ColorWhite;
        }
    }
    console->NextLine = next_line;
    TeletypeConsole_Render(console);
}

TeletypeConsole TeletypeConsole_Create() {
    TeletypeConsole console;
    console.NextLine = 0;
    console.VideoBuffer = (void*) 0xB8000;
    return console;
}

