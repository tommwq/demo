let mix = new MixMachine();

let value = Word.create(-80, 3, 5, 4);
let address = 2000;
mix.writeMemory(address, value);
let inst = new Instrument(LDA,2000,0,5);
mix.execute(inst);
let result = mix.rA().get();
assert(value.print(), result.print());

inst = new Instrument(LDA,2000,0,13);
mix.execute(inst);
result = mix.rA().get();
let expect = Word.create(80, 3, 5, 4);
assert(expect.print(), result.print());

inst = new Instrument(LDA,2000,0,29);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 3, 5, 4);
assert(expect.print(), result.print());

inst = new Instrument(LDA,2000,0,3);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 1, 16, 3);
expect.setNegative();
assert(expect.print(), result.print());

inst = new Instrument(LDA,2000,0,36);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 5);
assert(expect.print(), result.print());

inst = new Instrument(LDA,2000,0,0);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 0);
expect.setNegative();
assert(expect.print(), result.print());

inst = new Instrument(LDA,2000,0,9);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 1);
assert(expect.print(), result.print());


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,5);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(391, 8, 9, 0);
assert(expect.print(), result.print());


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,13);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-391, 8, 9, 0);
assert(expect.print(), result.print());

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,45);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-66, 3, 4, 0);
assert(expect.print(), result.print());


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,18);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-64, 3, 4, 5);
assert(expect.print(), result.print());

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,19);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-73, 0, 4, 5);
assert(expect.print(), result.print());

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,1);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(2, 3, 4, 5);
assert(expect.print(), result.print());


mix.rA().set(Word.create(1234, 1, 2, 22));
value = Word.create(100, 5, 0, 50);
mix.writeMemory(1000, value);
inst = new Instrument(ADD,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(1334, 6, 3, 8);
assert(expect.print(), result.print());


mix.rA().set(Word.create(-1234, 0, 0, 9));
value = Word.create(-2000, 2, 22, 0);
mix.writeMemory(1000, value);
inst = new Instrument(SUB,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(766, 2, 21, 55);
assert(expect.print(), result.print());


mix.rA().set(Word.create(65, 1, 1, 1));
value = Word.create(65, 1, 1, 1);
mix.writeMemory(1000, value);
inst = new Instrument(MUL,1000,0,5);
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
inst = new Instrument(MUL,1000,0,9);
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
inst = new Instrument(MUL,1000,0,5);
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
inst = new Instrument(DIV,1000,0,5);
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
inst = new Instrument(DIV,1000,0,5);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(9, 41, 32, 1);
assert(expect.print(), result.print());
result = mix.rX().get();
expect = new Word(false, 65);
assert(expect.print(), result.print());

console.log("test instrument pass");
