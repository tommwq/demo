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
        copy.assignByte(1, new Byte(0));
        copy.assignByte(2, new Byte(0));
        copy.assignByte(3, new Byte(0));
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

class Device {
    constructor(blockSize) {
        this._blockSize = blockSize;
        this._data = [];
        this._position = 0;
    }
    seek(position) {
        throw "not supported";
    }
    forward(blockCount) {
        throw "not supported";
    }
    backward(blockCount) {
        throw "not supported";
    }
    rewind() {
        throw "not supported";
    }
    paperFeed() {
        throw "not supported";
    }
    setData(words) {
        this._data = words;
        this._position = 0;
    }
    getBlockSize() {
        return this._blockSize;
    }
    // 从设备中读取数据，返回[word]
    read() {
        let block = this._data.slice(this._position, this._position + this._blockSize);
        this._position += this._blockSize;
        return block;
    }
    // block = [word]
    write(block) {
        for (let word of block) {
            this._data[this._position++] = Word.copy(word);
        }
    }
}

// 磁带
class Tape extends Device {
    constructor() {
        super(100);
    }
    forward(blockCount) {
        this._position += (blockCount * this._blockSize);
    }
    backward(blockCount) {
        this._position -= (blockCount * this._blockSize);
    }
    rewind() {
        this._position = 0;
    }
}

class Disk extends Device {
    constructor() {
        super(100);
    }
    seek(position) {
        this._position = position;
    }
}

class LinePrinter extends Device {
    constructor() {
        super(24);
    }
    paperFeed() {
        // TODO
    }
}

class PunchPaperTape extends Device {
    constructor() {
        super(16);
    }
    rewind() {
        this._position = 0;
    }
}

// MIX机器
class MixMachine {
    constructor() {
        this._rA = new Register();
        this._rX = new Register();
        this._rI = [
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister()
        ];
        this._rJ = new TransferAddressRegister();
        this._compare_indicator = new Indicator();
        this._overflow_toggle = new Toggle();
        this._memory = [];
        for (let index = 0; index <= MemorySize; index++) {
            this._memory.push(new Word(true, 0));
        }
        this._devices = [
            new Tape(),
            new Tape(),
            new Tape(),
            new Tape(),
            new Tape(),
            new Tape(),
            new Tape(),
            new Tape(),
            new Disk(),
            new Disk(),
            new Disk(),
            new Disk(),
            new Disk(),
            new Disk(),
            new Disk(),
            new Disk(),
            new PunchPaperTape(),
            new Device(16), // 卡片穿孔机
            new LinePrinter(), 
            new Device(14), // 打字机终端
            new Device(14), // 纸带
        ];
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
    getAddress(instrument) {
        let offset = instrument.address;
        let base = 0;
        if (instrument.index >= 1 && instrument.index <= 6) {
            base = this.rI(instrument.index).get().value();
        }
        return base + offset;
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
        let operand = this.readMemory(this.getAddress(instrument));
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let result = MixMachine.adjustWordByField(operand, left, right);
        register.set(result);
    }
    _loadNegative(register, instrument) {
        let operand = this.readMemory(this.getAddress(instrument));
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
        let result = this.readMemory(this.getAddress(instrument));
        if (left == 0) {
            operand.isPositive() ? result.setPositive() : result.setNegative();
            left = 1;
        }
        for (let i = left; i <= right; i++) {
            result.assignByte(i, operand.getByte(MAX_BYTE_FIELD + i - right));
        }
        
        this.writeMemory(this.getAddress(instrument), result);
    }
    _store_zero(instrument) {
        this.writeMemory(this.getAddress(instrument), new Word(true, 0));
    }
    _add(instrument) {
        let operand = this.readMemory(this.getAddress(instrument));
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
        let operand = this.readMemory(this.getAddress(instrument));
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
        let operand = this.readMemory(this.getAddress(instrument));
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

        let operand = this.readMemory(this.getAddress(instrument));
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
    _enter(instrument, register) {
        switch (instrument.field) {
        case 0:
            this._inc(instrument, register);
            break;
        case 1:
            this._dec(instrument, register);
            break;
        case 2:
            this._ent(instrument, register);
            break;
        case 3:
            this._enn(instrument, register);
            break;
        }
    }
    _inc(instrument, register) {
        let op1 = this.getAddress(instrument);
        let op2 = register.get().value();
        let overflow = false;
        let result = op1 + op2;
        if (Math.abs(result) > MAX_WORD) {
            result = result % (MAX_WORD + 1);
            overflow = true;
        }

        register.set(result);
        if (overflow) {
            this._overflow_toggle.turnOn();
        }
    }
    _dec(instrument, register) {
        let op1 = this.getAddress(instrument);
        let op2 = register.get().value();
        let overflow = false;
        let result = op1 - op2;
        if (Math.abs(result) > MAX_WORD) {
            result = result % (MAX_WORD + 1);
            overflow = true;
        }

        register.set(result);
        if (overflow) {
            this._overflow_toggle.turnOn();
        }
    }
    _ent(instrument, register) {
        register.set(this.getAddress(instrument));
    }
    _enn(instrument, register) {
        register.set(-1 * this.getAddress(instrument));
    }
    _compare(instrument, register) {
        let operand = this.readMemory(this.getAddress(instrument));
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let op1 = register.get().value();
        let op2 = adjustWordByField(operand, left, right);
        if (op1 == op2) {
            this._compare_indicator.turnEqual();
        } else if (op1 < op2) {
            this._compare_indicator.turnLess();
        } else {
            this._compare_indicator.turnGreater();
        }
    }
    _jump_reg(instrument, register) {
        [
            this._jn, this._jz, this._jp, this._jnn,
            this._jnz, this._jnp
        ][instrument.field].bind(this)(instrument, register);
    }
    _jn(instrument, register) {
        if (register.get().isNegative()) {
            this._jmp(instrument);
        }
    }
    _jz(instrument, register) {
        if (register.get().value() == 0) {
            this._jmp(instrument);
        }
    }
    _jp(instrument, register) {
        if (register.get().isPositive()) {
            this._jmp(instrument);
        }
    }
    _jnn(instrument, register) {
        if (!register.get().isNegative()) {
            this._jmp(instrument);
        }
    }
    _jnz(instrument, register) {
        if (register.get().value() != 0) {
            this._jmp(instrument);
        }
    }
    _jnp(instrument, register) {
        if (!register.get().isPositive()) {
            this._jmp(instrument);
        }
    }
    _jump(instrument) {
        [
            this._jmp, this._jsj, this._jov, this._jnov,
            this._jl, this._je, this._jg, this._jge,
            this._jne, this._jle
        ][instrument.field].bind(this)(instrument);
    }
    _jmp(instrument) {
        this._rJ.set(this.getAddress(instrument));
    }
    _jsj(instrument) {
        // TODO 实现JSJ
    }
    _jov(instrument) {
        if (this._overflow_toggle.isOn()) {
            this._overflow_toggle.turnOff();
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _jnov(instrument) {
        if (this._overflow_toggle.isOff()) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _jl(instrument) {
        if (this._compare_indicator.isLess()) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _je(instrument) {
        if (this._compare_indicator.isEqual()) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _jg(instrument) {
        if (this._compare_indicator.isGreater()) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _jge(instrument) {
        if (!this._compare_indicator.isLess() ) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _jne(instrument) {
        if (!this._compare_indicator.isEqual()) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _jle(instrument) {
        if (!this._compare_indicator.isGreater()) {
            this._rJ.set(this.getAddress(instrument));
        }
    }
    _shift(instrument) {
        [
            this._sla, this._sra, this._slax, this._srax,
            this._slc, this._src
        ][instrument.field].bind(this)(instrument);
    }
    _sla(instrument) {
        let operand = this.getAddress(instrument);
        if (operand <= 0) {
            return;
        }
        if (operand >= 5) {
            operand = 5;
        }
        let word = this._rA.get();
        for (let i = 1; i <= 5; i++) {
            let value = 0;
            let shiftIndex = i + operand;
            if (shiftIndex <= 5) {
                value = word.getByte(shiftIndex).value();
            }
            word.assignByte(i, new Byte(value));
        }
        this._rA.set(word);
    }
    _sra(instrument) {
        let operand = this.getAddress(instrument);
        if (operand <= 0) {
            return;
        }
        if (operand >= 5) {
            operand = 5;
        }
        let word = this._rA.get();
        for (let i = 5; i >= 1; i--) {
            let value = 0;
            let shiftIndex = i - operand;
            if (shiftIndex >= 1) {
                value = word.getByte(shiftIndex).value();
            }
            word.assignByte(i, new Byte(value));
        }
        this._rA.set(word);
    }
    _slax(instrument) {
        let operand = this.getAddress(instrument);
        if (operand <= 0) {
            return;
        }
        if (operand >= 10) {
            operand = 10;
        }
        let word1 = this._rA.get();
        let word2 = this._rX.get();
        for (let i = 1; i <= 10; i++) {
            let value = 0;
            let shiftIndex = i + operand;
            if (shiftIndex <= 5) {
                value = word1.getByte(shiftIndex).value();
            } else if (6 <= shiftIndex && shiftIndex <= 10) {
                value = word2.getByte(shiftIndex - 5).value();
            }
            if (i <= 5) {
                word1.assignByte(i, new Byte(value));
            } else {
                word2.assignByte(i - 5, new Byte(value));
            }
        }
        this._rA.set(word1);
        this._rX.set(word2);
    }
    _srax(instrument) {
        let operand = this.getAddress(instrument);
        if (operand <= 0) {
            return;
        }
        if (operand >= 10) {
            operand = 10;
        }
        let word1 = this._rA.get();
        let word2 = this._rX.get();
        for (let i = 10; i >= 1; i--) {
            let value = 0;
            let shiftIndex = i - operand;
            if (1 <= shiftIndex && shiftIndex <= 5) {
                value = word1.getByte(shiftIndex).value();
            } else if (6 <= shiftIndex && shiftIndex <= 10) {
                value = word2.getByte(shiftIndex - 5).value();
            }
            if (i <= 5) {
                word1.assignByte(i, new Byte(value));
            } else {
                word2.assignByte(i - 5, new Byte(value));
            }
        }
        this._rA.set(word1);
        this._rX.set(word2);
    }
    _slc(instrument) {
        let operand = this.getAddress(instrument);
        if (operand < 0) {
            return;
        }
        operand = operand % 10;
        if (operand == 0) {
            return;
        }
        let ra = this._rA.get();
        let rx = this._rX.get();
        let word1 = this._rA.get();
        let word2 = this._rX.get();
        for (let i = 1; i <= 10; i++) {
            let value = 0;
            let shiftIndex = i + operand;
            if (shiftIndex <= 5) {
                value = word1.getByte(shiftIndex).value();
            } else if (6 <= shiftIndex && shiftIndex <= 10) {
                value = word2.getByte(shiftIndex - 5).value();
            } else {
                value = word1.getByte(shiftIndex - 10).value();
            }
            if (i <= 5) {
                ra.assignByte(i, new Byte(value));
            } else {
                rx.assignByte(i - 5, new Byte(value));
            }
        }
        this._rA.set(ra);
        this._rX.set(rx);
    }
    _src(instrument) {
        let operand = this.getAddress(instrument);
        if (operand < 0) {
            return;
        }
        operand = operand % 10;
        if (operand == 0) {
            return;
        }
        let ra = this._rA.get();
        let rx = this._rX.get();
        let word1 = this._rA.get();
        let word2 = this._rX.get();
        for (let i = 10; i >= 1; i--) {
            let value = 0;
            let shiftIndex = i - operand;
            if (shiftIndex < 1) {
                shiftIndex += 10;
            }
            if (shiftIndex <= 5) {
                value = word1.getByte(shiftIndex).value();
            } else if (6 <= shiftIndex && shiftIndex <= 10) {
                value = word2.getByte(shiftIndex - 5).value();
            } else {
                value = word1.getByte(shiftIndex + 10).value();
            }
            if (i <= 5) {
                ra.assignByte(i, new Byte(value));
            } else {
                rx.assignByte(i - 5, new Byte(value));
            }
        }
        this._rA.set(ra);
        this._rX.set(rx);
    }
    _move(instrument) {
        let base = this.getAddress(instrument);
        let count = instrument.field;
        if (count <= 0) {
            return;
        }
        let ri1 = this._rI[0].get().value();
        for (let i = 0; i < count; i++) {
            this.writeMemory(ri1 + i, this.readMemory(base + i));
        }
        this._rI[0].set(new Word(true, ri1 + count));
    }
    _nop() {
        // do nothing
    }
    _char(instrument) {
        let ra = this._rA.get();
        let rx = this._rX.get();
        let value = ra.value();
        for (let i = 10; i >= 1; i--) {
            let digit = 30 + value % 10;
            value /= 10;
            if (i <= 5) {
                ra.assignByte(i, new Byte(digit));
            } else {
                rx.assignByte(i - 5, new Byte(digit));
            }
        }
        this._rA.set(ra);
        this._rX.set(rx);
    }
    _num(instrument) {
        let ra = this._rA.get();
        let rx = this._rX.get();
        let value = 0;
        for (let i = 1; i <= 10; i++) {
            let digit;
            if (i <= 5) {
                digit = ra.getByte(i).value();
            } else {
                digit = rx.getByte(i - 5).value();
            }
            value = value * 10 + (digit % 10);
        }
        value %= (MAX_WORD + 1);
        this._rA.set(new Word(ra.isPositive(), value));
    }
    _hlt(instrument) {
        // do nothing
    }
    _ioc(instrument) {
        let m = this.getAddress(instrument);
        let deviceNumber = instrument.field;
        let device = this._device[deviceNumber];
        if (m == 0) {
            if (deviceNumber <= 7 || deviceNumber == 16) {
                device.rewind();
            } else if (deviceNumber <= 15) {
                device.seek(this._rX.get().value());
            }
        }
        
        if (deviceNumber <= 7 && m != 0) {
            if (m < 0) {
                device.backword(-m);
            } else {
                device.forward(m);
            }
        }
    }
    _in(instrument) {
        let base = this.getAddress(instrument);
        let device = this._devices[instrument.field];
        let block = device.read();
        for (let word of block) {
            this.writeMemory(base++, word);
        }
    }
    _out(instrument) {
        let base = this.getAddress(instrument);
        let device = this._devices[instrument.field];
        let block = this._memory.slice(base, base + device.getBlockSize());
        device.write(block);
    }
    _jred(instrument) {
        this._jmp(instrument);
    }
    _jbus(instrument) {
        // do nothing
    }
    execute(instrument) {
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
        case ENTA:
            this._enter(instrument, this._rA);
            break;
        case ENT1:
            this._enter(instrument, this._rI[0]);
            break;
        case ENT2:
            this._enter(instrument, this._rI[1]);
            break;
        case ENT3:
            this._enter(instrument, this._rI[2]);
            break;
        case ENT4:
            this._enter(instrument, this._rI[3]);
            break;
        case ENT5:
            this._enter(instrument, this._rI[4]);
            break;
        case ENT6:
            this._enter(instrument, this._rI[5]);
            break;
        case ENTX:
            this._enter(instrument, this._rX);
            break;
        case CMPA:
            this._compare(instrument, this._rA);
            break;
        case CMP1:
            this._compare(instrument, this._rI[0]);
            break;
        case CMP2:
            this._compare(instrument, this._rI[1]);
            break;
        case CMP3:
            this._compare(instrument, this._rI[2]);
            break;
        case CMP4:
            this._compare(instrument, this._rI[3]);
            break;
        case CMP5:
            this._compare(instrument, this._rI[4]);
            break;
        case CMP6:
            this._compare(instrument, this._rI[5]);
            break;
        case CMPX:
            this._compare(instrument, this._rX);
            break;
        case JMP:
            this._jump(instrument);
            break;
        case JAN:
            this._jump_reg(instrument, this._rA);
            break;
        case J1N:
            this._jump_reg(instrument, this._rI[1]);
            break;
        case J2N:
            this._jump_reg(instrument, this._rI[2]);
            break;
        case J3N:
            this._jump_reg(instrument, this._rI[3]);
            break;
        case J4N:
            this._jump_reg(instrument, this._rI[4]);
            break;
        case J5N:
            this._jump_reg(instrument, this._rI[5]);
            break;
        case J6N:
            this._jump_reg(instrument, this._rI[6]);
            break;
        case JXN:
            this._jump_reg(instrument, this._rX);
            break;
        case SLA:
            this._shift(instrument);
            break;
        case MOVE:
            this._move(instrument);
            break;
        case NOP:
            this._nop();
            break;
        case HLT:
            [
                this._num, this._char, this._hlt
            ][instrument.field].bind(this)(instrument);
            break;
        case IOC:
            this._ioc(instrument);
            break;
        case IN:
            this._in(instrument);
            break;
        case OUT:
            this._out(instrument);
            break;
        case JRED:
            this._jred(instrument);
            break;
        case JBUS:
            this._jbus(instrument);
            break;
        default:
            throw `invalid instrument ${instrument.operator}`;
            break;
        }
    }
}
