// 错误
const InvalidValueError = "invalid value";

// 数据类型定义
const MIN_BYTE = 0;
const MAX_BYTE = 63;
const BITS_PER_BYTE = 6;
const MIN_WORD = 0;
const MAX_WORD = 1073741823;
const BITS_PER_WORD = 30;
const MIN_BYTE_FIELD = 1;
const MAX_BYTE_FIELD = 5;

// 常量
const Zero = 0;
const MemorySize = 4000;

// 指令
const NOP = 0;
const ADD = 1;
const SUB = 2;
const MUL = 3;
const DIV = 4;
const HLT = 5;
const NUM = 5;
const CHAR = 5;
const SLA = 6;
const SRA = 6;
const SLAX = 6;
const SRAX = 6;
const SLC = 6;
const SRC = 6;
const MOVE = 7;
const LDA = 8;
const LD1 = 9;
const LD2 = 10;
const LD3 = 11;
const LD4 = 12;
const LD5 = 13;
const LD6 = 14;
const LDX = 15;
const LDAN = 16;
const LD1N = 17;
const LD2N = 18;
const LD3N = 19;
const LD4N = 20;
const LD5N = 21;
const LD6N = 22;
const LDXN = 23;
const STA = 24;
const ST1 = 25;
const ST2 = 26;
const ST3 = 27;
const ST4 = 28;
const ST5 = 29;
const ST6 = 30;
const STX = 31;
const STJ = 32;
const STZ = 33;
const JBUS = 34;
const IOC = 35;
const IN = 36;
const OUT = 37;
const JRED = 38;
const JMP = 39;
const JSJ = 39;
const JOV = 39;
const JNOV = 39;
const JL = 39;
const JE = 39;
const JG = 39;
const JGE = 39;
const JNE = 39;
const JLE = 39;
const JAN = 40;
const JAZ = 40;
const JAP = 40;
const JANN = 40;
const JANZ = 40;
const JANP = 40;
const J1N = 41;
const J1Z = 41;
const J1P = 41;
const J1NN = 41;
const J1NZ = 41;
const J1NP = 41;
const J2N = 42;
const J2Z = 42;
const J2P = 42;
const J2NN = 42;
const J2NZ = 42;
const J2NP = 42;
const J3N = 43;
const J3Z = 43;
const J3P = 43;
const J3NN = 43;
const J3NZ = 43;
const J3NP = 43;
const J4N = 44;
const J4Z = 44;
const J4P = 44;
const J4NN = 44;
const J4NZ = 44;
const J4NP = 44;
const J5N = 45;
const J5Z = 45;
const J5P = 45;
const J5NN = 45;
const J5NZ = 45;
const J5NP = 45;
const J6N = 46;
const J6Z = 46;
const J6P = 46;
const J6NN = 46;
const J6NZ = 46;
const J6NP = 46;
const JXN = 47;
const JXZ = 47;
const JXP = 47;
const JXNN = 47;
const JXNZ = 47;
const JXNP = 47;
const ENTA = 48;
const ENT1 = 49;
const ENT2 = 50;
const ENT3 = 51;
const ENT4 = 52;
const ENT5 = 53;
const ENT6 = 54;
const ENTX = 55;
const ENNA = 48;
const ENN1 = 49;
const ENN2 = 50;
const ENN3 = 51;
const ENN4 = 52;
const ENN5 = 53;
const ENN6 = 54;
const ENNX = 55;
const INCA = 48;
const INC1 = 49;
const INC2 = 50;
const INC3 = 51;
const INC4 = 52;
const INC5 = 53;
const INC6 = 54;
const INCX = 55;
const DECA = 48;
const DEC1 = 49;
const DEC2 = 50;
const DEC3 = 51;
const DEC4 = 52;
const DEC5 = 53;
const DEC6 = 54;
const DECX = 55;
const CMPA = 56;
const CMP1 = 57;
const CMP2 = 58;
const CMP3 = 59;
const CMP4 = 60;
const CMP5 = 61;
const CMP6 = 62;
const CMPX = 63;

// 检查值value是否大于min且小于max。
function checkValue(value, min, max, error) {
    let e = InvalidValueError;
    if (error != null) {
        e = error;
    }
    
    if (value < min || value > max) {
        throw e;
    }
}


// 字节
class Byte {
    constructor(value) {
        this._value = Zero;
        if (value != null) {
            this.assign(value);
        }
    }

    assign(value) {
        checkValue(value, MIN_BYTE, MAX_BYTE);
        this._value = value;
    }

    value() {
        return this._value;
    }
}

// 符号位
class SignBit {
    constructor(isPositive) {
        this.is_positive = isPositive;
    }

    isSame(sign) {
        return this.is_positive == sign.is_positive;
    }

    isPositive() {
        return this.is_positive;
    }

    isNegative() {
        return !this.is_positive;
    }

    setPositive() {
        this.is_positive = true;
    }

    setNegative() {
        this.is_positive = false;
    }

    flip() {
        this.is_positive = !this.is_positive;
    }

    equal(other) {
        return this.is_positive == other.is_positive;
    }
}

// 位描述符 TODO 是否保留？
class FieldDescriptor {
    constructor(value) {
        this.left_byte = value / 8;
        this.right_byte = value % 8;
    }

    assign(left, right) {
        this.left_byte = left;
        this.right_byte = right;
    }

    left() {
        return this.left_byte;
    }

    right() {
        return this.right_byte;
    }
}

// 字
class Word {
    // true/false, value
    constructor(sign, value) {
        this._sign = new SignBit(sign);
        this._value = Zero;
        if (value != null) {
            this.assign(value);
        }
    }

    flipSign() {
        this._sign.flip();
    }

    print() {
        let text = [this._sign.isPositive() ? '+' : '-'];
        for (let i = MIN_BYTE_FIELD; i <= MAX_BYTE_FIELD; i++) {
            text.push(this.getByte(i).value());
        }
        return '[' + text.join(' ') + ']';
    }

    isSameSign(word) {
        return this._sign.isSame(word._sign);
    }

    static copy(word) {
        let result = new Word(true, 0);
        result.assign(word.value());
        if (word._sign.isNegative()) {
            result._sign.setNegative();
        }
        return result;
    }

    static create(address, index, field, code) {
        let word = new Word(true, 0);
        if (address < 0) {
            word.setNegative();
            address = -1 * address;
        }

        word.assignByte(1, new Byte(address / 64));
        word.assignByte(2, new Byte(address % 64));
        word.assignByte(3, new Byte(index));
        word.assignByte(4, new Byte(field));
        word.assignByte(5, new Byte(code));
        return word;
    }

    equal(other) {
        return this._value == other._value && this._sign.equal(other._sign);
    }
    
    assign(value) {
        checkValue(Math.abs(value), MIN_WORD, MAX_WORD);
        this._value = Math.abs(value);
        if (value < 0) {
            this.setNegative();
        }
    }

    isPositive() {
        return this._sign.isPositive();
    }

    isNegative() {
        return this._sign.isNegative();
    }

    setNegative() {
        this._sign.setNegative();
    }

    setPositive() {
        this._sign.setPositive();
    }

    value() {
        return this._value;
    }

    getByte(field) {
        checkValue(field, MIN_BYTE_FIELD, MAX_BYTE_FIELD);
        let byteOffset = (MAX_BYTE_FIELD - field) * BITS_PER_BYTE;
        let mask = ~((~0) << BITS_PER_BYTE);
        let byte = new Byte((this._value >>> byteOffset) & mask);
        return byte;
    }

    assignByte(field, mixByte) {
        checkValue(field, MIN_BYTE_FIELD, MAX_BYTE_FIELD);
        let byteOffset = (MAX_BYTE_FIELD - field) * BITS_PER_BYTE;
        let byteMask = ~((~0) << BITS_PER_BYTE) << byteOffset;
        this.assign((this._value & ~byteMask) | (mixByte.value() << byteOffset));
    }
}

// 寄存器
class Register {
    constructor() {
        this._word = new Word(true, 0);
    }

    set(word) {
        this._word = new Word(word.isPositive(), word.value());
    }

    get() {
        return new Word(this._word.isPositive(), this._word.value());
    }
}

// 地址寄存器
class AddressRegister extends Register {
    set(word) {
        let copy = new Word(word.isPositive(), word.value());
        copy.assignByte(3, new Byte(0));
        copy.assignByte(4, new Byte(0));
        copy.assignByte(5, new Byte(0));
        this._word = copy;
    }
}

// 地址转移寄存器
class TransferAddressRegister extends AddressRegister {
    set(word) {
        super.set(word);
        this._word._sign.setPositive();
    }
}

// 溢出开关
class Toggle {
    constructor() {
        this._on = false;
    }

    isOn() {
        return this._on;
    }

    isOff() {
        return !this._on;
    }

    turnOn() {
        this._on = true;
    }

    turnOff() {
        this._on = false;
    }
}

// 比较指示器
class Indicator {
    constructor() {
        this._value = 'less';
    }

    turnLess() {
        this._value = 'less';
    }

    turnGreater() {
        this._value = 'greater';
    }

    turnEqual() {
        this._value = 'equal';
    }

    isLess() {
        return this._value == 'less';
    }

    isGreater() {
        return this._value == 'greater';
    }

    isEqual() {
        return this._value = 'equal';
    }
}

// MIX机器
class MixMachine {
    constructor() {
        this._rA = new Register();
        this._rX = new Register();
        this._rI = new Array(
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister()
        );
        this._rJ = new TransferAddressRegister();
        this._compare_indicator = new Indicator();
        this._overflow_toggle = new Toggle();
        this._memory = new Array();
        for (let index = 0; index <= MemorySize; index++) {
            this._memory.push(new Word(true, 0));
        }
        // TODO 外设 U0 ~ U20
    }

    rA() {
        return this._rA;
    }

    rX() {
        return this._rX;
    }

    rI(index) {
        return this._rI[i - 1];
    }

    rJ() {
        return this._rJ;
    }

    writeMemory(offset, word) {
        this._memory[offset] = Word.copy(word);
    }

    readMemory(offset) {
        return Word.copy(this._memory[offset]);
    }

    static adjustWordByField(word, left, right) {
        let result = new Word(true, 0);
        if (left == 0) {
            if (word.isNegative()) {
                result.setNegative();
            }
            left++;
        }

        for (let i = left; i <= right; i++) {
            let byte = word.getByte(i);
            result.assignByte(5 + i - right, byte);
        }

        return result;
    }

    _load(register, instrument) {
        let operand = this.readMemory(instrument.address);
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let result = MixMachine.adjustWordByField(operand, left, right);
        register.set(result);
    }

    _loadNegative(register, instrument) {
        let operand = this.readMemory(instrument.address);
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let result = MixMachine.adjustWordByField(operand, left, right);
        result.flipSign();
        register.set(result);
    }

    _store(register, instrument) {
        let operand = Word.copy(register.get());
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let result = this.readMemory(instrument.address);
        if (left == 0) {
            operand.isPositive() ? result.setPositive() : result.setNegative();
            left = 1;
        }
        for (let i = left; i <= right; i++) {
            result.assignByte(i, operand.getByte(MAX_BYTE_FIELD + i - right));
        }
        
        this.writeMemory(instrument.address, result);
    }

    _store_zero(instrument) {
        this.writeMemory(instrument.address, new Word(true, 0));
    }

    _add(instrument) {
        let operand = this.readMemory(instrument.address);
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MixMachine.adjustWordByField(operand, left, right);
        let rA = this.rA().get();

        let op1 = BigInt(rA.value() * (rA.isPositive() ? 1 : -1));
        let op2 = BigInt(tmp.value() * (tmp.isPositive() ? 1 : -1));

        let result = op1 + op2;
        let abs = result > 0 ? result : -result;

        if (abs > MAX_WORD) {
            this._overflow_toggle.turnOn();
            abs = MAX_WORD;
        }

        this.rA().set(new Word(result > 0, Number(abs)));
    }
    
    _sub(instrument) {
        let operand = this.readMemory(instrument.address);
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MixMachine.adjustWordByField(operand, left, right);
        let rA = this.rA().get();
        
        let op1 = BigInt(rA.value() * (rA.isPositive() ? 1 : -1));
        let op2 = BigInt(tmp.value() * (tmp.isPositive() ? 1 : -1));

        let result = op1 - op2;
        let abs = result > 0 ? result : -result;

        if (abs > MAX_WORD) {
            this._overflow_toggle.turnOn();
            abs = MAX_WORD;
        }

        this.rA().set(new Word(result > 0, Number(abs)));
    }

    _mul(instrument) {
        let operand = this.readMemory(instrument.address);
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MixMachine.adjustWordByField(operand, left, right);
        let rA = this.rA().get();

        let op1 = BigInt(rA.value() * (rA.isPositive() ? 1 : -1));
        let op2 = BigInt(tmp.value() * (tmp.isPositive() ? 1 : -1));

        let result = op1 * op2;
        let abs = result > 0 ? result : -result;

        let high = abs / BigInt(MAX_WORD + 1);
        let low = abs % BigInt(MAX_WORD + 1);

        this.rA().set(new Word(result > 0, Number(high)));
        this.rX().set(new Word(result > 0, Number(low)));
    }
    
    _div(instrument) {
        let high = this.rA().get().value();
        let low = this.rX().get().value();

        let operand = this.readMemory(instrument.address);
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MixMachine.adjustWordByField(operand, left, right);
        if (high >= tmp.value()) {
            this._overflow_toggle().turnOn();
            return;
        }

        let positive = this.rA().get().isSameSign(tmp);
        let oldRASign = this.rA().get().isPositive();
        let op1 = BigInt(high) * BigInt(MAX_WORD + 1) + BigInt(low);
        let op2 = BigInt(tmp.value());

        let quotient = Number(op1 / op2);
        let remainder = Number(op1 % op2);

        this.rA().set(new Word(positive, quotient));
        this.rX().set(new Word(oldRASign, remainder));
    }

    execute(instrument) {
        let operand;
        let left;
        let right;
        
        switch (instrument.operator) {
        case LDA:
            this._load(this._rA, instrument);
            break;
        case LD1:
            this._load(this._rI[0], instrument);
            break;
        case LD2:
            this._load(this._rI[1], instrument);
            break;
        case LD3:
            this._load(this._rI[2], instrument);
            break;
        case LD4:
            this._load(this._rI[3], instrument);
            break;
        case LD5:
            this._load(this._rI[4], instrument);
            break;
        case LD6:
            this._load(this._rI[5], instrument);
            break;
        case LDX:
            this._load(this._rX, instrument);
            break;
        case LDAN:
            this._loadNegative(this._rA, instrument);
            break;
        case LD1N:
            this._loadNegative(this._rI[0], instrument);
            break;
        case LD2N:
            this._loadNegative(this._rI[1], instrument);
            break;
        case LD3N:
            this._loadNegative(this._rI[2], instrument);
            break;
        case LD4N:
            this._loadNegative(this._rI[3], instrument);
            break;
        case LD5N:
            this._loadNegative(this._rI[4], instrument);
            break;
        case LD6N:
            this._loadNegative(this._rI[5], instrument);
            break;
        case LDXN:
            this._loadNegative(this._rX, instrument);
            break;
        case STA:
            this._store(this._rA, instrument);
            break;
        case ST1:
            this._store(this._rI[0], instrument);
            break;
        case ST2:
            this._store(this._rI[1], instrument);
            break;
        case ST3:
            this._store(this._rI[2], instrument);
            break;
        case ST4:
            this._store(this._rI[3], instrument);
            break;
        case ST5:
            this._store(this._rI[4], instrument);
            break;
        case ST6:
            this._store(this._rI[5], instrument);
            break;
        case STX:
            this._store(this._rX, instrument);
            break;
        case STJ:
            this._store(this._rJ, instrument);
            break;
        case STZ:
            this._store_zero(instrument);
            break;
        case ADD:
            this._add(instrument);
            break;
        case SUB:
            this._sub(instrument);
            break;
        case MUL:
            this._mul(instrument);
            break;
        case DIV:
            this._div(instrument);
            break;
        default:
            throw `invalid instrument ${instrument.operator}`;
            break;
        }
    }
}
