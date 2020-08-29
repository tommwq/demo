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
    assertTrue(instrument.equal(actural));
}

console.log("test compiler pass");
