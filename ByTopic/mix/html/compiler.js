// OP ADDRESS, I(F)

// TODO put Instrument to common.js

// mix指令
class Instrument {
    constructor(operator, address, index, field) {
        this.operator = operator;
        this.address = address;
        this.index = index;
        this.field = field;
    }

    equal(instrument) {
        return instrument != null &&
            this.operator == instrument.operator &&
            this.address == instrument.address &&
            this.index == instrument.index &&
            this.field == instrument.field;
    }
}

// TODO 提取和TokenSequence相似的部分，做成基类。
class Reader {
    constructor(str) {
        this.offset = 0;
        this.str = str;
    }

    isSeparator(ch) {
        let result = false;
        for (let sep of [' ', ',', '(', ')', ':']) {
            if (ch == sep) {
                result = true;
            }
        }
        return result
    }

    skipSpace() {
        while (!this.isEOL() && this.str[this.offset] == ' ') {
            this.offset++;
        }
    }

    isEOL() {
        return this.offset >= this.str.length;
    }

    // nullable
    nextToken() {
        this.skipSpace();
        if (this.isEOL()) {
            return null;
        }
        
        let token = '';
        let ch = this.getChar();
        while (!this.isEOL() && !this.isSeparator(ch)) {
            token = token + ch;
            ch = this.getChar();
        }

        if (this.isEOL() && !this.isSeparator(ch)) {
            token = token + ch;
        } else if (token == '') {
            token = ch;
        } else {
            this.ungetChar();
        }
        
        return token;
    }

    getChar() {
        if (this.isEOL()) {
            return null;
        }
        
        let ch = this.str[this.offset];
        this.offset++;
        return ch;
    }

    ungetChar() {
        this.offset--;
    }
}

class TokenSequence {
    constructor(tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    next() {
        if (this.isOver()) {
            throw 'token sequence overflow'; 
        }
        let t =  this.tokens[this.position];
        this.position++;
        return t
    }

    back() {
        this.position--;
    }

    isOver() {
        return this.position >= this.tokens.length;
    }
}

class Compiler {

    constructor() {
        this.instrumentTable = {
            "LDA": 8,
            "NOP": 0,
            "ADD": 1,
            "SUB": 2,
            "MUL": 3,
            "DIV": 4,
            "HLT": 5,
            "NUM": 5,
            "CHAR": 5,
            "SLA": 6,
            "SRA": 6,
            "SLAX": 6,
            "SRAX": 6,
            "SLC": 6,
            "SRC": 6,
            "MOVE": 7,
            "LDA": 8,
            "LD1": 9,
            "LD2": 10,
            "LD3": 11,
            "LD4": 12,
            "LD5": 13,
            "LD6": 14,
            "LDX": 15,
            "LDAN": 16,
            "LD1N": 17,
            "LD2N": 18,
            "LD3N": 19,
            "LD4N": 20,
            "LD5N": 21,
            "LD6N": 22,
            "LDXN": 23,
            "STA": 24,
            "ST1": 25,
            "ST2": 26,
            "ST3": 27,
            "ST4": 28,
            "ST5": 29,
            "ST6": 30,
            "STX": 31,
            "STJ": 32,
            "STZ": 33,
            "JBUS": 34,
            "IOC": 35,
            "IN": 36,
            "OUT": 37,
            "JRED": 38,
            "JMP": 39,
            "JSJ": 39,
            "JOV": 39,
            "JNOV": 39,
            "JL": 39,
            "JE": 39,
            "JG": 39,
            "JGE": 39,
            "JNE": 39,
            "JLE": 39,
            "JAN": 40,
            "JAZ": 40,
            "JAP": 40,
            "JANN": 40,
            "JANZ": 40,
            "JANP": 40,
            "J1N": 41,
            "J1Z": 41,
            "J1P": 41,
            "J1NN": 41,
            "J1NZ": 41,
            "J1NP": 41,
            "J2N": 42,
            "J2Z": 42,
            "J2P": 42,
            "J2NN": 42,
            "J2NZ": 42,
            "J2NP": 42,
            "J3N": 43,
            "J3Z": 43,
            "J3P": 43,
            "J3NN": 43,
            "J3NZ": 43,
            "J3NP": 43,
            "J4N": 44,
            "J4Z": 44,
            "J4P": 44,
            "J4NN": 44,
            "J4NZ": 44,
            "J4NP": 44,
            "J5N": 45,
            "J5Z": 45,
            "J5P": 45,
            "J5NN": 45,
            "J5NZ": 45,
            "J5NP": 45,
            "J6N": 46,
            "J6Z": 46,
            "J6P": 46,
            "J6NN": 46,
            "J6NZ": 46,
            "J6NP": 46,
            "JXN": 47,
            "JXZ": 47,
            "JXP": 47,
            "JXNN": 47,
            "JXNZ": 47,
            "JXNP": 47,
            "ENTA": 48,
            "ENT1": 49,
            "ENT2": 50,
            "ENT3": 51,
            "ENT4": 52,
            "ENT5": 53,
            "ENT6": 54,
            "ENTX": 55,
            "ENNA": 48,
            "ENN1": 49,
            "ENN2": 50,
            "ENN3": 51,
            "ENN4": 52,
            "ENN5": 53,
            "ENN6": 54,
            "ENNX": 55,
            "INCA": 48,
            "INC1": 49,
            "INC2": 50,
            "INC3": 51,
            "INC4": 52,
            "INC5": 53,
            "INC6": 54,
            "INCX": 55,
            "DECA": 48,
            "DEC1": 49,
            "DEC2": 50,
            "DEC3": 51,
            "DEC4": 52,
            "DEC5": 53,
            "DEC6": 54,
            "DECX": 55,
            "CMPA": 56,
            "CMP1": 57,
            "CMP2": 58,
            "CMP3": 59,
            "CMP4": 60,
            "CMP5": 61,
            "CMP6": 62,
            "CMPX": 63,
        };
    }

    translateInstrument(str) {
        return this.instrumentTable[str.toUpperCase()];
    }
    
    compile(source) {
        let tokens = this.parse(source);
        let inst = 0;
        let addr = 0;
        let index = 0;
        let field = 0;

        let seq = new TokenSequence(tokens);
        inst = this.translateInstrument(seq.next());
        addr = parseInt(seq.next());

        if (!seq.isOver()) {
            let t = seq.next();
            if (t == ',') {
                index = parseInt(seq.next());
            } else if (t == '(') {
                seq.back();
            } else {
                console.log(tokens);
                throw 'syntax error 1';
            }
        }

        let left = 0;
        let right = 5;
        if (!seq.isOver()) {
            let t = seq.next();
            if (t == '(') {
                left = parseInt(seq.next());
                if (seq.next() != ':') {
                    throw 'syntax error 2';
                }
                right = parseInt(seq.next());
            } else {
                throw 'syntax error 3';
            }
        }
        
        field = left * 8 + right;
        return new Instrument(inst, addr, index, field);
    }

    parse(source) {
        let reader = new Reader(source);
        let tokens = [];
        let token = reader.nextToken();
        while (token != null) {
            tokens.push(token);
            token = reader.nextToken();
        }
        return tokens;
    }
}

const TOKEN = {
    "Symbol": "Symbol",
    "Number": "Number",
    "Punctuation": "Punctuation",
    "EQU": "EQU",
    "ORIG": "ORIG",
    "CON": "CON",
    "ALF": "ALF",
    "END": "END",
};

class Token {
    constructor(type, word) {
        this.type = type;
        this.word = word;
    }
    string() {
        return `Token(${this.type},"${this.word}")`;
    }
    isSymbol() {
        return this.type == TOKEN.Symbol;
    }
    isNumber() {
        return this.type == TOKEN.Number;
    }
    isPunctuation() {
        return this.type == TOKEN.Punctuation;
    }
    isSpecialSymbol() {
        let word = this.word;
        if (word.length != 2) {
            return false;
        }

        let c = word[1];
        return CType.isdigit(word[0]) && (c == 'H' || c == 'B' || c == 'F');
    }    
}

var CType = {
    "isalpha": (c) => {
        return ("a" <= c && c <= "z") || ("A" <= c && c <= "Z");
    },
    "isdigit": (c) => {
        return "0" <= c && c <= "9";
    },
    "isalnum": (c) => {
        return CType.isalpha(c) || CType.isdigit(c);
    },
    "ispunct": (c) => {
        let x = c.charCodeAt(0);
        return (33 <= x && x <= 47) ||
            (58 <= x && x <= 64) ||
            (91 <= x && x <= 96) ||
            (123 <= x && x <= 126);
    },
    "isspace": (c) => {
        return c in {
            " ": true,
            "\n": true,
            "\t": true,
            "\v": true,
            "\r": true,
        };
    },
};

function parseLine(line) {
    let tokens = [];
    for (let pos = 0; pos < line.length; pos++) {
        let ch = line[pos];
        if (CType.isspace(ch)) {
            continue;
        }

        let word = "";
        if (CType.ispunct(ch)) {
            word = ch;
            if (ch == "/" && pos + 1 < line.length && line[pos + 1] == "/") {
                pos++;
                word = "//";
            }
            tokens.push(new Token(TOKEN.Punctuation, word));
            continue;
        }

        let isNumber = true;
        while (pos < line.length) {
            ch = line[pos];
            if (!CType.isalnum(ch)) {
                pos--;
                break;
            }
            word = word + ch;
            if (CType.isalpha(ch)) {
                isNumber = false;
            }
            pos++;
        }
        if (word.length > 0) {
            let token;
            if (isNumber) {
                token = new Token(TOKEN.Number, word);
            } else {
                token = new Token(TOKEN.Symbol, word);
            }
            tokens.push(token);
        }
    }

    return tokens;
}

export {
    Instrument,
    Compiler,
    TOKEN,
    Token,
    parseLine,
    CType,    
};


