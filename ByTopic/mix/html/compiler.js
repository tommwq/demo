import { Reader } from './util/reader.js';
// OP ADDRESS, I(F)

// TODO put Instrument to common.js

class CodeSegment {
    constructor(baseAddress) {
        this.baseAddress = baseAddress;
        this.currentAddress = baseAddress;
        this.instruments = [];
    }

    addInstrument(instrument) {
        // console.debug(`add instrument ${instrument}`);
        this.instruments.push(instrument);
        this.currentAddress++;
    }        
}

class CompileContext {
    constructor() {
        this.symbols = {};
        this.codeSegments = [];
        // this.symbolDependencies = {}; // symbol -> [symbol]
    }

    addInstrument(instrument) {
        this.getCurrentCodeSegment().addInstrument(instrument);
    }
    
    clearCodeSegments() {
        this.codeSegments = [];
    }

    isSymbolDefined(symbol) {
        return symbol in this.symbols;
    }

    // A resolved symbol's value is determined.
    isSymbolResolved(symbol) {
        // TODO
    }

    // getSymbolDependencies(symbol) {
    //     if (symbol in this.symbolDependencies) {
    //         return this.symbolDependencies[symbol];
    //     }
    //     return [];
    // }

    defineSymbol(symbol, definition) {
        console.debug(`define ${symbol} = ${definition}`);
        this.symbols[symbol] = definition;
    }

    getSymbolValue(symbol) {
        if (!this.isSymbolDefined(symbol)) {
            throw `symbol not defined: "${symbol}"`;
        }
        return this.symbols[symbol];
    }
    
    createCodeSegment(baseAddress) {
        let segment = new CodeSegment(baseAddress);
        this.codeSegments.push(segment);
        return segment;
    }

    getCurrentCodeSegment() {
        if (this.codeSegments.length == 0) {
            throw `no code segments defined`;
        }
        return this.codeSegments[this.codeSegments.length-1];
    }
}


const MIXInstrumentTable = {
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

const MIXALKeyword = {
    "EQU": "EQU",
    "ORIG": "ORIG",
    "CON": "CON",
    "ALF": "ALF",
    "END": "END"
};

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
class Reader_old {
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

    // translateInstrument(str) {
    //     return this.instrumentTable[str.toUpperCase()];
    // }
    
    compile_old(source) {
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

    parse_old(source) {
        let reader = new Reader_old(source);
        let tokens = [];
        let token = reader.nextToken();
        while (token != null) {
            tokens.push(token);
            token = reader.nextToken();
        }
        return tokens;
    }

    parse(line) {
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

    compile(source) {
        let context = new CompileContext();
        let tokenLines =source.split("\n")
            .map(sourceLine => this.parse(sourceLine));
        tokenLines.forEach(tokenLine => this._resolveSymbol(tokenLine, context));
        tokenLines.forEach(tokenLine => this.compileLine(tokenLine, context));
        return context;
    }

    _resolveSymbol(tokenLine, context) {
        let tokens = new Reader(tokenLine);
        if (tokens.isEnd()) {
            return;
        }
        
        let token = tokens.get();
        if (token.isStar()) {
            // ignore comment
            return;
        }
        
        if (token.isKeyword_ORIG()) {
            if (tokens.isEnd()) {
                throw `compile error: missing ADDRESS`;
            }

            let nextToken = tokens.get();
            if (!nextToken.isNumber()) {
                throw `compile error: require NUMBER`;
            }

            let address = parseInt(nextToken.word);
            context.createCodeSegment(address);
            return;
        }

        if (token.isSpecialSymbol()) {
            // TODO
            context.getCurrentCodeSegment().addInstrument(null);
        } else if (token.isSymbol() && !token.isInstrument()) {
            if (tokens.isEnd()) {
                throw `syntax error: missing instrument or equ statement`;
            }
            
            let nextToken = tokens.get();
            if (nextToken.isKeyword_EQU()) {
                if (tokens.isEnd()) {
                    throw `syntax error: missing equ value`;
                }
                let symbol = token.word;
                token = tokens.get();
                if (tokens.isEnd()) {
                    if (token.isNumber()) {
                        let value = parseInt(token.word);
                        context.defineSymbol(symbol, value);
                    } else if (token.isSymbol()) {
                        // TODO resolve symbol
                    } else {
                        // TODO error
                    }
                } else {
                    // TODO 处理形式 X EQU Y + 1
                }
                return;
            } else {
                let symbol = token.word;
                let definition = context.getCurrentCodeSegment().currentAddress;
                context.defineSymbol(symbol, definition);
                context.getCurrentCodeSegment().addInstrument(null);
                return;
            }
        }
    }

    compileLine(tokenLine, context) {
        let tokens = new Reader(tokenLine);
        if (tokens.isEnd()) {
            return;
        }
        
        let token = tokens.get();
        if (token.isKeyword_ORIG()) {
            if (tokens.isEnd()) {
                throw `compile error: missing ADDRESS`;
            }

            let nextToken = tokens.get();
            if (!nextToken.isNumber()) {
                throw `compile error: require NUMBER`;
            }

            let address = parseInt(nextToken.word);
            context.createCodeSegment(address);
            return;
        }

        if (token.isKeyword_CON()) {
            // TODO
            return;
        }

        if (token.isKeyword_ALF()) {
            // TODO
            return;
        }

        if (token.isKeyword_END()) {
            // TODO
            return;
        }

        if (token.isInstrument()) {
            tokens.unget();
            context.addInstrument(this.compileInstrument(tokens.remainder(), context));
            return;
        }

        if (token.isSpecialSymbol()) {
            // TODO
            return;
        }

        if (token.isStar()) {
            // ignore comment
            return;
        }

        if (token.isSymbol() && !token.isInstrument()) {
            if (tokens.isEnd()) {
                throw `syntax error: missing instrument or equ statement`;
            }
            
            let nextToken = tokens.get();
            if (!nextToken.isKeyword_EQU()) {
                tokens.unget();
                context.addInstrument(this.compileInstrument(tokens.remainder(), context));
            }
            
            return;
        }

        throw `invalid statement: ${tokenLine.map(x=>x.string())}`;
    }

    compileInstrument(tokenList, context) {
        // 替换符号
        tokenList = tokenList.map(x => {
            if (x.isSymbol() && !x.isInstrument()) {
                return new Token(TOKEN.Number, context.getSymbolValue(x.word));
            }
            return x;
        });

        let tokens = new Reader(tokenList);
        let code = 0;
        let address = 0;
        let index = 0;
        let field = 0;

        if (tokens.isEnd()) {
            throw `missing instrument code`;
        }
        let token = tokens.get();
        if (!(token.word in MIXInstrumentTable)) {
            throw `invalid instrument: ${token.word}`;
        }
        code = MIXInstrumentTable[token.word];

        if (tokens.isEnd()) {
            throw `missing instrument address`;
        }
        token = tokens.get();
        if (token.isStar()) {
            // TODO 处理 JGE *+3格式的指令
            // address = context.current
        } else if (token.isNumber()) {
                 address = parseInt(token.word);   
        } else {
            throw `invalid address: ${token.word}`;
        }

        if (!tokens.isEnd()) {
            token = tokens.get();
            if (token.isComma()) {
                if (tokens.isEnd()) {
                    throw `missing instrument index`;
                }
                token = tokens.get();
                if (!token.isNumber()) {
                    throw `invalid index: ${token.word}`;
                }
                index = parseInt(token.word);
            } else if (token.isLeftParenthesis()) {
                tokens.unget();
            } else {
                throw `invalid syntax`;
            }
        }

        let left = 0;
        let right =5;
        if (!tokens.isEnd()) {
            token = tokens.get();
            if (token.isLeftParenthesis()) {
                if (tokens.isEnd()) {
                    throw `missing field`;
                }
                left = parseInt(tokens.get().word);
                if (tokens.isEnd()) {
                    throw `invalid field`;
                }
                token = tokens.get();
                if (!token.isColon() || tokens.isEnd()) {
                    throw `invalid field`;
                }
                token = tokens.get();
                if (!token.isNumber()) {
                    throw `invalid field`;
                }
                right = parseInt(token.word);
            } else {
                throw `invalid syntax`;
            }
        }

        field = left * 8 + right;
        return new Instrument(code, address, index, field);
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
    isStar() {
        return this.isPunctuation() && this.word == "*";
    }
    isLeftParenthesis() {
        return this.isPunctuation() && this.word == "(";
    }
    isRightParenthesis() {
        return this.isPunctuation() && this.word == ")";
    }
    isRightParenthesis() {
        return this.isPunctuation() && this.word == ")";
    }
    isPlus() {
        return this.isPunctuation() && this.word == "+";
    }
    isMinus() {
        return this.isPunctuation() && this.word == "-";
    }
    isColon() {
        return this.isPunctuation() && this.word == ":";
    }
    isDivide() {
        return this.isPunctuation() && this.word == "/";
    }
    isDoubleDivide() {
        return this.isPunctuation() && this.word == "//";
    }
    isComma() {
        return this.isPunctuation() && this.word == ",";
    }
    isInstrument() {
        return this.isSymbol() && this.word in MIXInstrumentTable;
    }
    isKeyword() {
        return this.isSymbol() && this.word in MIXALKeyword;
    }
    isKeyword_EQU() {
        return this.isKeyword() && this.word == "EQU";
    }
    isKeyword_ORIG() {
        return this.isKeyword() && this.word == "ORIG";
    }
    isKeyword_CON() {
        return this.isKeyword() && this.word == "CON";
    }
    isKeyword_ALF() {
        return this.isKeyword() && this.word == "ALF";
    }
    isKeyword_END() {
        return this.isKeyword() && this.word == "END";
    }
    toInstrumentCode() {
        if (!this.isInstrument()) {
            throw `not an instrument: "${this.word}"`;
        }
        
        return MIXInstrumentTable[this.word];
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

export {
    Instrument,
    Compiler,
    TOKEN,
    Token,
    CType,    
};


