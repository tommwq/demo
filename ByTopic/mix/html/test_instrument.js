let mix = new MixMachine();

let value = Word.create(-80, 3, 5, 4);
let address = 2000;
mix.writeMemory(address, value);
let inst = new Instrument(8,2000,0,5);
mix.execute(inst);
let result = mix.registerA();
assertTrue(result.equal(value));

inst = new Instrument(8,2000,0,13);
mix.execute(inst);
result = mix.registerA();
let expect = Word.create(80, 3, 5, 4);
assertTrue(result.equal(expect));

inst = new Instrument(8,2000,0,29);
mix.execute(inst);
result = mix.registerA();
expect = Word.create(0, 3, 5, 4);
assertTrue(result.equal(expect));

inst = new Instrument(8,2000,0,3);
mix.execute(inst);
result = mix.registerA();
expect = Word.create(0, 1, 16, 3);
expect.setNegative();
assertTrue(result.equal(expect));

inst = new Instrument(8,2000,0,36);
mix.execute(inst);
result = mix.registerA();
expect = Word.create(0, 0, 0, 5);
assertTrue(result.equal(expect));

inst = new Instrument(8,2000,0,0);
mix.execute(inst);
result = mix.registerA();
expect = Word.create(0, 0, 0, 0);
expect.setNegative();
assertTrue(result.equal(expect));

inst = new Instrument(8,2000,0,9);
mix.execute(inst);
result = mix.registerA();
expect = Word.create(0, 0, 0, 1);
assertTrue(result.equal(expect));



console.log("test instrument pass");
