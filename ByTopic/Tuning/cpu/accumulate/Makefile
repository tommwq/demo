accumulate.bin: accumulate.c
	$(CC) -o $@ $< -pthread


test: accumulate.bin
	./accumulate.bin single
	./accumulate.bin dual
	./accumulate.bin dual_pad
