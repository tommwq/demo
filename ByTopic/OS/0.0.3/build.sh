clear

nasm boot.asm -f bin -o boot.bin
if [ $? -eq 1 ]; then
    exit 1
fi

gcc main.c -c -m32 -fno-pie -fno-stack-protector
if [ $? -eq 1 ]; then
    exit 1
fi

nasm util.asm -f elf32
if [ $? -eq 1 ]; then
    exit 1
fi

ld -m elf_i386 --oformat binary --output bootloader.bin --entry main --strip-all main.o util.o
if [ $? -eq 1 ]; then
    exit 1
fi

cat boot.bin bootloader.bin > image.bin

# nasm boot2.asm -f bin -o boot2.bin
# cat boot.bin boot2.bin > image.bin

dd if=./image.bin of=../build/os.img count=1 bs=1440k conv=sync

