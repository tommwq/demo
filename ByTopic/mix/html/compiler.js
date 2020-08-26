// OP ADDRESS, I(F)

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

        if (token == '') {
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

        inst = this.translateInstrument(tokens[0]);
        addr = parseInt(tokens[1]);
        index = parseInt(tokens[3]);
        let left = parseInt(tokens[5]);
        let right = parseInt(tokens[7]);

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
