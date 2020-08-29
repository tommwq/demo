let mix = new MixMachine();

let value = Word.create(-80, 3, 5, 4);
let address = 2000;
mix.writeMemory(address, value);
let inst = new Instrument(LDA,2000,0,5);
mix.execute(inst);
let result = mix.rA().get();
assertTrue(result.equal(value));

inst = new Instrument(LDA,2000,0,13);
mix.execute(inst);
result = mix.rA().get();
let expect = Word.create(80, 3, 5, 4);
assertTrue(result.equal(expect));

inst = new Instrument(LDA,2000,0,29);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 3, 5, 4);
assertTrue(result.equal(expect));

inst = new Instrument(LDA,2000,0,3);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 1, 16, 3);
expect.setNegative();
assertTrue(result.equal(expect));

inst = new Instrument(LDA,2000,0,36);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 5);
assertTrue(result.equal(expect));

inst = new Instrument(LDA,2000,0,0);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 0);
expect.setNegative();
assertTrue(result.equal(expect));

inst = new Instrument(LDA,2000,0,9);
mix.execute(inst);
result = mix.rA().get();
expect = Word.create(0, 0, 0, 1);
assertTrue(result.equal(expect));


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,5);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(391, 8, 9, 0);
assertTrue(result.equal(expect));


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,13);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-391, 8, 9, 0);
assertTrue(result.equal(expect));

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,45);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-66, 3, 4, 0);
assertTrue(result.equal(expect));


value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,18);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-64, 3, 4, 5);
assertTrue(result.equal(expect));

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,19);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(-73, 0, 4, 5);
assertTrue(result.equal(expect));

value = Word.create(-66, 3, 4, 5);
mix.writeMemory(2000, value);
mix.rA().set(Word.create(391, 8, 9, 0));
inst = new Instrument(STA,2000,0,1);
mix.execute(inst);
result = mix.readMemory(2000);
expect = Word.create(2, 3, 4, 5);
assertTrue(result.equal(expect));

console.log("test instrument pass");
