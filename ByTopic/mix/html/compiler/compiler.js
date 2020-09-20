import { Reader } from '../util/reader.js';
import * as ctype from '../util/ctype.js';
import { CompileContext } from './compile_context.js';
import { MIXInstrumentTable } from './instrument_table.js';
import { Instrument } from '../instrument.js';

// OP ADDRESS, I(F)

// TODO put Instrument to common.js


const MIXALKeyword = {
    "EQU": "EQU",
    "ORIG": "ORIG",
    "CON": "CON",
    "ALF": "ALF",
    "END": "END"
};

class Compiler {

    parse(line) {
        let tokens = [];
        for (let pos = 0; pos < line.length; pos++) {
            let ch = line[pos];
            if (ctype.isspace(ch)) {
                continue;
            }

            let word = "";
            if (ctype.ispunct(ch)) {
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
                if (!ctype.isalnum(ch)) {
                    pos--;
                    break;
                }
                word = word + ch;
                if (ctype.isalpha(ch)) {
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
        return ctype.isdigit(word[0]) && (c == 'H' || c == 'B' || c == 'F');
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

export {
    Compiler,
    Token,
};


