// mix数据类型

import { mix } from './value.js';

// 检查值value是否大于min且小于max。
function checkValue(value, min, max, error) {
    let e = mix.error.InvalidValueError;
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
        this._value = mix.constant.Zero;
        if (value != null) {
            this.assign(value);
        }
    }

    assign(value) {
        checkValue(value, mix.constant.MIN_BYTE, mix.constant.MAX_BYTE);
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
        this._value = mix.constant.Zero;
        if (value != null) {
            this.assign(value);
        }
    }
    flipSign() {
        this._sign.flip();
    }
    print() {
        let text = [this._sign.isPositive() ? '+' : '-'];
        for (let i = mix.constant.MIN_BYTE_FIELD; i <= mix.constant.MAX_BYTE_FIELD; i++) {
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
        checkValue(Math.abs(value), mix.constant.MIN_WORD, mix.constant.MAX_WORD);
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
        checkValue(field, mix.constant.MIN_BYTE_FIELD, mix.constant.MAX_BYTE_FIELD);
        let byteOffset = (mix.constant.MAX_BYTE_FIELD - field) * mix.constant.BITS_PER_BYTE;
        let mask = ~((~0) << mix.constant.BITS_PER_BYTE);
        let byte = new Byte((this._value >>> byteOffset) & mask);
        return byte;
    }
    assignByte(field, mixByte) {
        checkValue(field, mix.constant.MIN_BYTE_FIELD, mix.constant.MAX_BYTE_FIELD);
        let byteOffset = (mix.constant.MAX_BYTE_FIELD - field) * mix.constant.BITS_PER_BYTE;
        let byteMask = ~((~0) << mix.constant.BITS_PER_BYTE) << byteOffset;
        this.assign((this._value & ~byteMask) | (mixByte.value() << byteOffset));
    }
}

export {
    Byte,
    SignBit,
    Word,
};
