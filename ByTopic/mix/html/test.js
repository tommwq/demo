function assert(expect, actural) {
    if (expect != actural) {
        throw `expect ${expect} while get ${actural}`;
    }
}

function assertTrue(condition) {
    if (!condition) {
        throw `assertion failed`;
    }
}

// assert(1, 10);

let word1 = new Word();
assert(0, word1.get());

let word2 = new Word(10);
assert(10, word2.get());

word2.set(100);
assert(100, word2.get());

word2.set(0);
word2.setByte(5, new Byte(11));
assert(11, word2.get());

word2.set(0);
word2.setByte(4, new Byte(10));
assert(640, word2.get());


let fd = new FieldDescriptor(0);
assert(0, fd.left());
assert(0, fd.right());


let compiler = new Compiler();
let testCaseList = [
    {"text": "LDA 2000,2(0:3)", "instrument": new Instrument(8,2000,2,3)},
    {"text": "LDA 2000,2(1:3)", "instrument": new Instrument(8,2000,2,11)},
    {"text": "LDA 2000(1:3)", "instrument": new Instrument(8,2000,0,11)},
    {"text": "LDA 2000", "instrument": new Instrument(8,2000,0,5)},
    {"text": "LDA -2000,4", "instrument": new Instrument(8,-2000,4,5)}
];

for (let testCase of testCaseList) {
    let text = testCase.text;
    let instrument = testCase.instrument;
    let actural = compiler.compile(text);
    // console.log(instrument, actural);
    assertTrue(instrument.equal(actural));
}

let mix = new MixMachine();
let value = Word.create(-80, 3, 5, 4);
let address = 2000;
mix.writeMemory(address, value);
let inst = new Instrument(8,2000,0,5);
mix.execute(inst);
let result = mix.registerA().get();

assertTrue(result.equal(value));

console.log("test passed");
