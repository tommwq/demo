// OP ADDRESS, I(F)

// TODO put Instrument to common.js

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
