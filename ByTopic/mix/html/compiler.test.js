import { Instrument, Compiler, TOKEN, Token } from "./compiler.js";
import { assert } from "./test.js";


let compiler = new Compiler();
let testCaseList = [
    {"text": "LDA 2000,2(0:3)", "instrument": new Instrument(8,2000,2,3)},
    {"text": "LDA 2000,2(1:3)", "instrument": new Instrument(8,2000,2,11)},
    {"text": "LDA 2000(1:3)", "instrument": new Instrument(8,2000,0,11)},
    {"text": "LDA 2000", "instrument": new Instrument(8,2000,0,5)},
    {"text": "LDA -2000,4", "instrument": new Instrument(8,-2000,4,5)},
];

for (let testCase of testCaseList) {
    let text = testCase.text;
    let instrument = testCase.instrument;
    // TODO
    // let actural = compiler.compile(text);
    // assert(instrument.toString(), actural.toString());
}



let program1 = String.raw`X EQU 1000
ORIG 3000
MAXIMUM STJ EXIT
INIT ENT3 0,1
JMP CHANGEM
LOOP CMPA X,3
JGE *+3
CHANGEM ENT2 0,3
LDA X,3
DEC3 1
J3P LOOP
EXIT JMP *
`;

compiler.compile(program1);

// TODO
// for (let line of program1.split("\n")) {
//     console.log(compiler.parse(line).map((token) => token.string()).join(" "));
// }
