import { Byte, Word, SignBit } from './datatype.js';
import { mix } from './value.js';

// MIX机器

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
class MIXMachine {
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
        for (let index = 0; index <= mix.constant.MemorySize; index++) {
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
        let result = MIXMachine.adjustWordByField(operand, left, right);
        register.set(result);
    }
    _loadNegative(register, instrument) {
        let operand = this.readMemory(this.getAddress(instrument));
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let result = MIXMachine.adjustWordByField(operand, left, right);
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
            result.assignByte(i, operand.getByte(mix.constant.MAX_BYTE_FIELD + i - right));
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
        let tmp = MIXMachine.adjustWordByField(operand, left, right);
        let rA = this.rA().get();

        let op1 = BigInt(rA.value() * (rA.isPositive() ? 1 : -1));
        let op2 = BigInt(tmp.value() * (tmp.isPositive() ? 1 : -1));

        let result = op1 + op2;
        let abs = result > 0 ? result : -result;

        if (abs > mix.constant.MAX_WORD) {
            this._overflow_toggle.turnOn();
            abs = mix.constant.MAX_WORD;
        }

        this.rA().set(new Word(result > 0, Number(abs)));
    }
    _sub(instrument) {
        let operand = this.readMemory(this.getAddress(instrument));
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MIXMachine.adjustWordByField(operand, left, right);
        let rA = this.rA().get();
        
        let op1 = BigInt(rA.value() * (rA.isPositive() ? 1 : -1));
        let op2 = BigInt(tmp.value() * (tmp.isPositive() ? 1 : -1));

        let result = op1 - op2;
        let abs = result > 0 ? result : -result;

        if (abs > mix.constant.MAX_WORD) {
            this._overflow_toggle.turnOn();
            abs = mix.constant.MAX_WORD;
        }

        this.rA().set(new Word(result > 0, Number(abs)));
    }
    _mul(instrument) {
        let operand = this.readMemory(this.getAddress(instrument));
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MIXMachine.adjustWordByField(operand, left, right);
        let rA = this.rA().get();

        let op1 = BigInt(rA.value() * (rA.isPositive() ? 1 : -1));
        let op2 = BigInt(tmp.value() * (tmp.isPositive() ? 1 : -1));

        let result = op1 * op2;
        let abs = result > 0 ? result : -result;

        let high = abs / BigInt(mix.constant.MAX_WORD + 1);
        let low = abs % BigInt(mix.constant.MAX_WORD + 1);

        this.rA().set(new Word(result > 0, Number(high)));
        this.rX().set(new Word(result > 0, Number(low)));
    }
    _div(instrument) {
        let high = this.rA().get().value();
        let low = this.rX().get().value();

        let operand = this.readMemory(this.getAddress(instrument));
        let left = Math.floor(instrument.field / 8);
        let right = instrument.field % 8;
        let tmp = MIXMachine.adjustWordByField(operand, left, right);
        if (high >= tmp.value()) {
            this._overflow_toggle().turnOn();
            return;
        }

        let positive = this.rA().get().isSameSign(tmp);
        let oldRASign = this.rA().get().isPositive();
        let op1 = BigInt(high) * BigInt(mix.constant.MAX_WORD + 1) + BigInt(low);
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
        if (Math.abs(result) > mix.constant.MAX_WORD) {
            result = result % (mix.constant.MAX_WORD + 1);
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
        if (Math.abs(result) > mix.constant.MAX_WORD) {
            result = result % (mix.constant.MAX_WORD + 1);
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
        value %= (mix.constant.MAX_WORD + 1);
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
    _fadd(instrument) {
        // todo
    }
    _fsub(instrument) {
        // todo
    }
    _fmul(instrument) {
        // todo
    }
    _fdiv(instrument) {
        // todo
    }
    execute(instrument) {
        switch (instrument.operator) {
        case mix.code.LDA:
            this._load(this._rA, instrument);
            break;
        case mix.code.LD1:
            this._load(this._rI[0], instrument);
            break;
        case mix.code.LD2:
            this._load(this._rI[1], instrument);
            break;
        case mix.code.LD3:
            this._load(this._rI[2], instrument);
            break;
        case mix.code.LD4:
            this._load(this._rI[3], instrument);
            break;
        case mix.code.LD5:
            this._load(this._rI[4], instrument);
            break;
        case mix.code.LD6:
            this._load(this._rI[5], instrument);
            break;
        case mix.code.LDX:
            this._load(this._rX, instrument);
            break;
        case mix.code.LDAN:
            this._loadNegative(this._rA, instrument);
            break;
        case mix.code.LD1N:
            this._loadNegative(this._rI[0], instrument);
            break;
        case mix.code.LD2N:
            this._loadNegative(this._rI[1], instrument);
            break;
        case mix.code.LD3N:
            this._loadNegative(this._rI[2], instrument);
            break;
        case mix.code.LD4N:
            this._loadNegative(this._rI[3], instrument);
            break;
        case mix.code.LD5N:
            this._loadNegative(this._rI[4], instrument);
            break;
        case mix.code.LD6N:
            this._loadNegative(this._rI[5], instrument);
            break;
        case mix.code.LDXN:
            this._loadNegative(this._rX, instrument);
            break;
        case mix.code.STA:
            this._store(this._rA, instrument);
            break;
        case mix.code.ST1:
            this._store(this._rI[0], instrument);
            break;
        case mix.code.ST2:
            this._store(this._rI[1], instrument);
            break;
        case mix.code.ST3:
            this._store(this._rI[2], instrument);
            break;
        case mix.code.ST4:
            this._store(this._rI[3], instrument);
            break;
        case mix.code.ST5:
            this._store(this._rI[4], instrument);
            break;
        case mix.code.ST6:
            this._store(this._rI[5], instrument);
            break;
        case mix.code.STX:
            this._store(this._rX, instrument);
            break;
        case mix.code.STJ:
            this._store(this._rJ, instrument);
            break;
        case mix.code.STZ:
            this._store_zero(instrument);
            break;
        case mix.code.ADD:
            this._add(instrument);
            break;
        case mix.code.SUB:
            this._sub(instrument);
            break;
        case mix.code.MUL:
            this._mul(instrument);
            break;
        case mix.code.DIV:
            this._div(instrument);
            break;
        case mix.code.ENTA:
            this._enter(instrument, this._rA);
            break;
        case mix.code.ENT1:
            this._enter(instrument, this._rI[0]);
            break;
        case mix.code.ENT2:
            this._enter(instrument, this._rI[1]);
            break;
        case mix.code.ENT3:
            this._enter(instrument, this._rI[2]);
            break;
        case mix.code.ENT4:
            this._enter(instrument, this._rI[3]);
            break;
        case mix.code.ENT5:
            this._enter(instrument, this._rI[4]);
            break;
        case mix.code.ENT6:
            this._enter(instrument, this._rI[5]);
            break;
        case mix.code.ENTX:
            this._enter(instrument, this._rX);
            break;
        case mix.code.CMPA:
            this._compare(instrument, this._rA);
            break;
        case mix.code.CMP1:
            this._compare(instrument, this._rI[0]);
            break;
        case mix.code.CMP2:
            this._compare(instrument, this._rI[1]);
            break;
        case mix.code.CMP3:
            this._compare(instrument, this._rI[2]);
            break;
        case mix.code.CMP4:
            this._compare(instrument, this._rI[3]);
            break;
        case mix.code.CMP5:
            this._compare(instrument, this._rI[4]);
            break;
        case mix.code.CMP6:
            this._compare(instrument, this._rI[5]);
            break;
        case mix.code.CMPX:
            this._compare(instrument, this._rX);
            break;
        case mix.code.JMP:
            this._jump(instrument);
            break;
        case mix.code.JAN:
            this._jump_reg(instrument, this._rA);
            break;
        case mix.code.J1N:
            this._jump_reg(instrument, this._rI[1]);
            break;
        case mix.code.J2N:
            this._jump_reg(instrument, this._rI[2]);
            break;
        case mix.code.J3N:
            this._jump_reg(instrument, this._rI[3]);
            break;
        case mix.code.J4N:
            this._jump_reg(instrument, this._rI[4]);
            break;
        case mix.code.J5N:
            this._jump_reg(instrument, this._rI[5]);
            break;
        case mix.code.J6N:
            this._jump_reg(instrument, this._rI[6]);
            break;
        case mix.code.JXN:
            this._jump_reg(instrument, this._rX);
            break;
        case mix.code.SLA:
            this._shift(instrument);
            break;
        case mix.code.MOVE:
            this._move(instrument);
            break;
        case mix.code.NOP:
            this._nop();
            break;
        case mix.code.HLT:
            [
                this._num, this._char, this._hlt
            ][instrument.field].bind(this)(instrument);
            break;
        case mix.code.IOC:
            this._ioc(instrument);
            break;
        case mix.code.IN:
            this._in(instrument);
            break;
        case mix.code.OUT:
            this._out(instrument);
            break;
        case mix.code.JRED:
            this._jred(instrument);
            break;
        case mix.code.JBUS:
            this._jbus(instrument);
            break;
        default:
            throw `invalid instrument ${instrument.operator}`;
            break;
        }
    }
}


export {
    Register,
    AddressRegister,
    TransferAddressRegister,
    Toggle,
    Indicator,
    Device,
    Tape,
    Disk,
    LinePrinter,
    PunchPaperTape,
    MIXMachine,
};

