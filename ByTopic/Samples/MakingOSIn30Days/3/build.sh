nasm -f bin a.asm -o a.bin
nasm -f bin b.asm -o b.bin
gcc -S -masm=intel c.c -o c.asm

