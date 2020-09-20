import { Word, Byte, SignBit } from "./datatype.js";
import { MIXMachine } from "./mix.js";
import { Instrument } from "./compiler.js";
import { mix as mixvalue } from "./value.js";
import { assert } from "./test.js";


let mix = new MIXMachine();

let value = Word.create(-80, 3, 5, 4);
let address = 2000;
mix.writeMemory(address, value);
let inst = new Instrument(mixvalue.code.LDA,2000,0,5);
mix.execute(inst);
let result = mix.rA().get();
assert(value.print(), result.print());

inst = new Instrument(mixvalue.code.LDA,2000,0,13);
mix.execute(inst);
result = mix.rA().get();
let expect = Word.create(80, 3, 5, 4);
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.LDA,2000,0,29);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 3, 5, 4);
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.LDA,2000,0,3);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 1, 16, 3);
expect.setNegative();
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.LDA,2000,0,36);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 5);
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.LDA,2000,0,0);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 0);
expect.setNegative();
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.LDA,2000,0,9);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 1);
assert(expect.print(), result.print());


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(mixvalue.code.STA,2000,0,5);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(391, 8, 9, 0);
assert(expect.print(), result.print());


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(mixvalue.code.STA,2000,0,13);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-391, 8, 9, 0);
assert(expect.print(), result.print());

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(mixvalue.code.STA,2000,0,45);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-66, 3, 4, 0);
assert(expect.print(), result.print());


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(mixvalue.code.STA,2000,0,18);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-64, 3, 4, 5);
assert(expect.print(), result.print());

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(mixvalue.code.STA,2000,0,19);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-73, 0, 4, 5);
assert(expect.print(), result.print());

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(mixvalue.code.STA,2000,0,1);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(2, 3, 4, 5);
assert(expect.print(), result.print());


mix.rA().set(Word.create(1234, 1, 2, 22));
value = Word.create(100, 5, 0, 50);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.ADD,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(1334, 6, 3, 8);
assert(expect.print(), result.print());


mix.rA().set(Word.create(-1234, 0, 0, 9));
value = Word.create(-2000, 2, 22, 0);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.SUB,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(766, 2, 21, 55);
assert(expect.print(), result.print());


mix.rA().set(Word.create(65, 1, 1, 1));
value = Word.create(65, 1, 1, 1);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.MUL,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(1, 2, 3, 4);
assert(expect.print(), result.print());
result = mix.rX().get();
expect = Word.create(324, 3, 2, 1);
assert(expect.print(), result.print());


mix.rA().set(new Word(false, 112));
value = Word.create(128, 1, 1, 1);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.MUL,1000,0,9);
mix.execute(inst);
result = mix.rA().get();
expect = new Word(false, 0);
assert(expect.print(), result.print());
result = mix.rX().get();
expect = new Word(false, 224);
assert(expect.print(), result.print());


mix.rA().set(Word.create(-3200, 1, 48, 4));
value = Word.create(-128, 0, 0, 0);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.MUL,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(100, 0, 3, 32);
assert(expect.print(), result.print());
result = mix.rX().get();
expect = Word.create(512, 0, 0, 0);
assert(expect.print(), result.print());


mix.rA().set(new Word(true, 0));
mix.rX().set(new Word(true, 17));
value = new Word(true, 3);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.DIV,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = new Word(true, 5);
assert(expect.print(), result.print());
result = mix.rX().get();
expect = new Word(true, 2);
assert(expect.print(), result.print());


mix.rA().set(new Word(false, 0));
mix.rX().set(Word.create(1235, 0, 3, 1));
value = new Word(false, 128);
mix.writeMemory(1000, value);
inst = new Instrument(mixvalue.code.DIV,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(9, 41, 32, 1);
assert(expect.print(), result.print());
result = mix.rX().get();
expect = new Word(false, 65);
assert(expect.print(), result.print());


inst = new Instrument(mixvalue.code.SLA, 2, 0, 0);
mix.rA().set(Word.create(1, 2, 3, 4));
mix.execute(inst);
expect = Word.create(131, 4, 0, 0);
result = mix.rA().get();
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.SRA, 2, 0, 1);
mix.rA().set(Word.create(391, 8, 9, 2));
mix.execute(inst);
expect = Word.create(0, 6, 7, 8);
result = mix.rA().get();
assert(expect.print(), result.print());


inst = new Instrument(mixvalue.code.SRAX, 1, 0, 3);
mix.rA().set(Word.create(66, 3, 4, 5));
mix.rX().set(Word.create(-391, 8, 9, 10));
mix.execute(inst);
expect = Word.create(1, 2, 3, 4);
result = mix.rA().get();
assert(expect.print(), result.print());
expect = Word.create(-326, 7, 8, 9);
result = mix.rX().get();
assert(expect.print(), result.print());


inst = new Instrument(mixvalue.code.SLC, 501, 0, 4);
mix.rA().set(Word.create(0, 6, 7, 8));
mix.rX().set(Word.create(-196, 0, 0, 5));
mix.execute(inst);
expect = Word.create(6, 7, 8, 3);
result = mix.rA().get();
assert(expect.print(), result.print());
expect = Word.create(-256, 0, 5, 0);
result = mix.rX().get();
assert(expect.print(), result.print());


inst = new Instrument(mixvalue.code.SRC, 4, 0, 5);
mix.rA().set(Word.create(131, 4, 0, 0));
mix.rX().set(Word.create(-326, 7, 8, 9));
mix.execute(inst);
expect = Word.create(391, 8, 9, 2);
result = mix.rA().get();
assert(expect.print(), result.print());
expect = Word.create(-196, 0, 0, 5);
result = mix.rX().get();
assert(expect.print(), result.print());


inst = new Instrument(mixvalue.code.NUM, 0, 0, 0);
mix.rA().set(Word.create(0, 31, 32, 39));
mix.rX().set(Word.create(2425, 47, 30, 30));
mix.execute(inst);
expect = new Word(true, 12977700);
result = mix.rA().get();
assert(expect.print(), result.print());

inst = new Instrument(mixvalue.code.CHAR, 0, 0, 1);
mix.rA().set(new Word(false, 12977699));
mix.execute(inst);
expect = Word.create(-1950, 31, 32, 39);
result = mix.rA().get();
assert(expect.print(), result.print());
expect = Word.create(2405, 36, 39, 39);
result = mix.rX().get();
assert(expect.print(), result.print());
