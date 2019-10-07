void halt(void);
void write_byte(int address, int value);
void init_screen(void);

void main(void) {

        init_screen();

        /* int i; */
        /* for (i = 0x0a0000; i <= 0x0affff; i++) { */
        /*         write_byte(i, 15); */
        /* } */
        write_byte(0xdeadbeef, 15);
        write_byte(0x0a0000, 15);
        /* while (1) { */
        /*         halt(); */
        /* } */
}
