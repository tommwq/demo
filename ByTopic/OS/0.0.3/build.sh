clear

nasm boot.asm -f bin -o boot.bin

gcc main.c -c -m32 -fno-pie -fno-stack-protector 
nasm util.asm -f elf32
ld -m elf_i386 --oformat binary --output bootloader.bin --entry main --strip-all main.o util.o
cat boot.bin bootloader.bin > image.bin

# nasm boot2.asm -f bin -o boot2.bin
# cat boot.bin boot2.bin > image.bin

dd if=./image.bin of=../build/os.img count=1 bs=1440k conv=sync

