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
    {"text": "LDA 2000,2(0:3)", "instrument": new Instrument(8,2000,2,3)}
];

for (let testCase of testCaseList) {
    let text = testCase.text;
    let instrument = testCase.instrument;
    let actural = compiler.compile(text);
    assertTrue(instrument.equal(actural));
}

console.log("test passed");
