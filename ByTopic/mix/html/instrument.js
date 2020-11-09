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

export {
    Instrument,
};
