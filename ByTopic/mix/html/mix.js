const InvalidValueError = "invalid value"
const ByteMin = 0;
const ByteMax = 63;
const ByteBits = 6;
const WordMin = 0;
const WordMax = 1073741823;
const WordBits = 30;
const Zero = 0;
const ByteFieldMin = 1
const ByteFieldMax = 5
const MemorySize = 4000


// 检查值value是否大于min且小于max。
function checkValue(value, min, max, error) {
    let e = InvalidValueError;
    if (error != null) {
        e = error;
    }
    
    if (value < min || value > max) {
        console.debug(value, min, max);
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
        checkValue(value, ByteMin, ByteMax);
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

    print() {
        let text = [this._sign.isPositive() ? '+' : '-'];
        for (let i = ByteFieldMin; i <= ByteFieldMax; i++) {
            text.push(this.getByte(i).value());
        }
        return text.join(' ');
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
        return this.value == other.value && this._sign.equal(other._sign);
    }
    
    assign(value) {
        checkValue(Math.abs(value), WordMin, WordMax);
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
        checkValue(field, ByteFieldMin, ByteFieldMax);
        let byteOffset = (ByteFieldMax - field) * ByteBits;
        let mask = ~((~0) << ByteBits);
        let byte = new Byte((this.value >>> byteOffset) & mask);
        return byte;
    }

    assignByte(field, mixByte) {
        checkValue(field, ByteFieldMin, ByteFieldMax);
        let byteOffset = (ByteFieldMax - field) * ByteBits;
        let byteMask = ~((~0) << ByteBits) << byteOffset;
        this.assign((this.value & ~byteMask) | (mixByte.value() << byteOffset));
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
        this.word = copy;
    }

    // get() {
    //     return this.word;
    // }
}

// 地址转移寄存器
class TransferAddressRegister extends AddressRegister {
    set(word) {
        super.set(word);
        this.word.sign.setPositive();
    }
}

// 溢出开关
class Toggle {
    constructor() {
        this.is_turn_on = false;
    }

    isTurnOn() {
        return this.is_turn_on;
    }

    isTurnOff() {
        return !this.is_turn_on;
    }

    turnOn() {
        this.is_turn_on = true;
    }

    turnOff() {
        this.is_turn_on = false;
    }
}

// 比较指示器
class Indicator {
    constructor() {
        this.value = 'less';
    }

    turnLess() {
        this.value = 'less';
    }

    turnGreater() {
        this.value = 'greater';
    }

    turnEqual() {
        this.value = 'equal';
    }

    isLess() {
        return this.value == 'less';
    }

    isGreater() {
        return this.value == 'greater';
    }

    isEqual() {
        return this.value = 'equal';
    }
}

// MIX机器
class MixMachine {
    constructor() {
        this.register_a = new Register();
        this.register_x = new Register();
        this.register_i = new Array(
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister(),
            new AddressRegister()
        );
        this.register_j = new TransferAddressRegister();
        this.compare_indicator = new Indicator();
        this.overflow_toggle = new Toggle();
        this.memory = new Array();
        for (let index = 0; index <= MemorySize; index++) {
            this.memory.push(new Word(true, 0));
        }
        // TODO 外设 U0 ~ U20
    }

    registerA() {
        return this.register_a.get();
    }

    writeMemory(offset, word) {
        this.memory[offset] = Word.copy(word);
    }

    readMemory(offset) {
        return Word.copy(this.memory[offset]);
    }

    static adjustWordByField(word, left, right) {
        let result = new Word(true, 0);
        if (left == 0) {
            if (word._sign.isNegative()) {
                result._sign.setNegative();
            }
            left++;
        }

        for (let i = left; i <= right; i++) {
            let byte = word.getByte(i);
            result.assignByte(5 + i - right, byte);
        }

        return result;
    }

    execute(instrument) {
        switch (instrument.operator) {
        case 8:
            let operand = this.readMemory(instrument.address);
            let left = Math.floor(instrument.field / 8);
            let right = instrument.field % 8;
            operand = MixMachine.adjustWordByField(operand, left, right);
            this.register_a.set(operand);
            break;
        }
    }
}
