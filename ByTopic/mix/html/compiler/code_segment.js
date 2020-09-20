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


export {
    CodeSegment,
};
