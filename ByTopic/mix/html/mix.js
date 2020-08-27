// TODO 规范get方法名，改为value()。

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
        this.value = Zero;
        if (value != null) {
            this.set(value);
        }
    }

    set(value) {
        checkValue(value, ByteMin, ByteMax);
        this.value = value;
    }

    get() {
        return this.value;
    }
}

// 符号位
class SignBit {
    constructor() {
        this.is_positive = true;
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
    constructor(value) {
        this.sign = new SignBit();
        this.value = Zero;
        if (value != null) {
            this.set(value);
        }
    }

    print() {
        let text = [this.sign.isPositive() ? '+' : '-'];
        for (let i = ByteFieldMin; i <= ByteFieldMax; i++) {
            text.push(this.getByte(i).get());
        }
        return text.join(' ');
    }

    static copy(word) {
        let result = new Word();
        result.set(word.get());
        if (word.sign.isNegative()) {
            result.sign.setNegative();
        }
        return result;
    }

    static create(address, index, field, code) {
        let word = new Word();
        if (address < 0) {
            word.setNegative();
            address = -1 * address;
        }

        word.setByte(1, new Byte(address / 64));
        word.setByte(2, new Byte(address % 64));
        word.setByte(3, new Byte(index));
        word.setByte(4, new Byte(field));
        word.setByte(5, new Byte(code));
        return word;
    }

    equal(other) {
        return this.value == other.value && this.sign.equal(other.sign);
    }
    
    set(value) {
        checkValue(Math.abs(value), WordMin, WordMax);
        this.value = Math.abs(value);
        if (value < 0) {
            this.setNegative();
        }
    }

    setNegative() {
        this.sign.setNegative();
    }

    setPositive() {
        this.sign.setPositive();
    }

    get() {
        return this.value;
    }

    getByte(field) {
        checkValue(field, ByteFieldMin, ByteFieldMax);
        let byteOffset = (ByteFieldMax - field) * ByteBits;
        let mask = ~((~0) << ByteBits);
        let byte = new Byte((this.value >>> byteOffset) & mask);
        return byte;
    }

    setByte(field, mixByte) {
        checkValue(field, ByteFieldMin, ByteFieldMax);
        let byteOffset = (ByteFieldMax - field) * ByteBits;
        let byteMask = ~((~0) << ByteBits) << byteOffset;
        this.set((this.value & ~byteMask) | (mixByte.get() << byteOffset));
    }
}

// 寄存器
class Register {
    constructor() {
        this.word = new Word();
    }

    set(word) {
        this.word = word;
    }

    get() {
        return this.word;
    }
}

// 地址寄存器
class AddressRegister extends Register {
    set(word) {
        let copy = new Word(word.get());
        copy.setByte(3, new Byte(0));
        copy.setByte(4, new Byte(0));
        copy.setByte(5, new Byte(0));
        this.word = copy;
    }

    get() {
        return this.word;
    }
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
            this.memory.push(new Word());
        }
        // TODO 外设 U0 ~ U20
    }

    registerA() {
        return Word.copy(this.register_a.get());
    }

    writeMemory(offset, word) {
        this.memory[offset] = Word.copy(word);
    }

    readMemory(offset) {
        return Word.copy(this.memory[offset]);
    }

    static adjustWordByField(word, left, right) {
        let result = new Word();
        if (left == 0) {
            if (word.sign.isNegative()) {
                result.sign.setNegative();
            }
            left++;
        }

        for (let i = left; i <= right; i++) {
            let byte = word.getByte(i);
            result.setByte(5 + i - right, byte);
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
