import { CodeSegment } from './code_segment.js';

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

export {
    CompileContext,
};
