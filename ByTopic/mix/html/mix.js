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

// 字
class Word {
    constructor(value) {
        this.value = Zero;
        if (value != null) {
            this.set(value);
        }
    }
    
    set(value) {
        checkValue(value, WordMin, WordMax);
        this.value = value;
    }

    get() {
        return this.value;
    }

    getByte(field) {
        checkValue(field, ByteFieldMin, ByteFieldMax);
        let byteOffset = (ByteFieldMax - field) * ByteBits;
        let mask = (~0) << ByteBits;
        return Byte((this.value >>> byteOffset) & mask);
    }

    setByte(field, mixByte) {
        checkValue(field, ByteFieldMin, ByteFieldMax);
        let byteOffset = (ByteFieldMax - field) * ByteBits;
        let byteMask = ~((~0) << ByteBits) << byteOffset;
        this.set((this.value & ~byteMask) | (mixByte.get() << byteOffset));
    }
}

// 符号位
class SignBit {
    constructor() {
        this.isPositive = true;
    }

    isPositive() {
        return this.isPositive;
    }

    isNegative() {
        return !this.isPositive;
    }

    setPositive() {
        this.isPositive = true;
    }

    setNegative() {
        this.isPositive = false;
    }
}

// 寄存器
class MixRegister {
    constructor() {
        this.signBit = new SignBit();
        this.word = new Word();
    }
}

class MixMachine {
    
}
