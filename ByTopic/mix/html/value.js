// 常量

const mix = {};
mix.error = {};
mix.constant = {};
mix.code = {};
mix.alphabeta = [];

mix.error.InvalidValueError = "invalid value";

// 数据类型定义
mix.constant.MIN_BYTE = 0;
mix.constant.MAX_BYTE = 63;
mix.constant.BITS_PER_BYTE = 6;
mix.constant.MIN_WORD = 0;
mix.constant.MAX_WORD = 1073741823;
mix.constant.BITS_PER_WORD = 30;
mix.constant.MIN_BYTE_FIELD = 1;
mix.constant.MAX_BYTE_FIELD = 5;

// 常量
mix.constant.Zero = 0;
mix.constant.MemorySize = 4000;

mix.code.NOP = 0;
mix.code.ADD = 1;
mix.code.SUB = 2;
mix.code.MUL = 3;
mix.code.DIV = 4;
mix.code.HLT = 5;
mix.code.NUM = 5;
mix.code.CHAR = 5;
mix.code.SLA = 6;
mix.code.SRA = 6;
mix.code.SLAX = 6;
mix.code.SRAX = 6;
mix.code.SLC = 6;
mix.code.SRC = 6;
mix.code.MOVE = 7;
mix.code.LDA = 8;
mix.code.LD1 = 9;
mix.code.LD2 = 10;
mix.code.LD3 = 11;
mix.code.LD4 = 12;
mix.code.LD5 = 13;
mix.code.LD6 = 14;
mix.code.LDX = 15;
mix.code.LDAN = 16;
mix.code.LD1N = 17;
mix.code.LD2N = 18;
mix.code.LD3N = 19;
mix.code.LD4N = 20;
mix.code.LD5N = 21;
mix.code.LD6N = 22;
mix.code.LDXN = 23;
mix.code.STA = 24;
mix.code.ST1 = 25;
mix.code.ST2 = 26;
mix.code.ST3 = 27;
mix.code.ST4 = 28;
mix.code.ST5 = 29;
mix.code.ST6 = 30;
mix.code.STX = 31;
mix.code.STJ = 32;
mix.code.STZ = 33;
mix.code.JBUS = 34;
mix.code.IOC = 35;
mix.code.IN = 36;
mix.code.OUT = 37;
mix.code.JRED = 38;
mix.code.JMP = 39;
mix.code.JSJ = 39;
mix.code.JOV = 39;
mix.code.JNOV = 39;
mix.code.JL = 39;
mix.code.JE = 39;
mix.code.JG = 39;
mix.code.JGE = 39;
mix.code.JNE = 39;
mix.code.JLE = 39;
mix.code.JAN = 40;
mix.code.JAZ = 40;
mix.code.JAP = 40;
mix.code.JANN = 40;
mix.code.JANZ = 40;
mix.code.JANP = 40;
mix.code.J1N = 41;
mix.code.J1Z = 41;
mix.code.J1P = 41;
mix.code.J1NN = 41;
mix.code.J1NZ = 41;
mix.code.J1NP = 41;
mix.code.J2N = 42;
mix.code.J2Z = 42;
mix.code.J2P = 42;
mix.code.J2NN = 42;
mix.code.J2NZ = 42;
mix.code.J2NP = 42;
mix.code.J3N = 43;
mix.code.J3Z = 43;
mix.code.J3P = 43;
mix.code.J3NN = 43;
mix.code.J3NZ = 43;
mix.code.J3NP = 43;
mix.code.J4N = 44;
mix.code.J4Z = 44;
mix.code.J4P = 44;
mix.code.J4NN = 44;
mix.code.J4NZ = 44;
mix.code.J4NP = 44;
mix.code.J5N = 45;
mix.code.J5Z = 45;
mix.code.J5P = 45;
mix.code.J5NN = 45;
mix.code.J5NZ = 45;
mix.code.J5NP = 45;
mix.code.J6N = 46;
mix.code.J6Z = 46;
mix.code.J6P = 46;
mix.code.J6NN = 46;
mix.code.J6NZ = 46;
mix.code.J6NP = 46;
mix.code.JXN = 47;
mix.code.JXZ = 47;
mix.code.JXP = 47;
mix.code.JXNN = 47;
mix.code.JXNZ = 47;
mix.code.JXNP = 47;
mix.code.ENTA = 48;
mix.code.ENT1 = 49;
mix.code.ENT2 = 50;
mix.code.ENT3 = 51;
mix.code.ENT4 = 52;
mix.code.ENT5 = 53;
mix.code.ENT6 = 54;
mix.code.ENTX = 55;
mix.code.ENNA = 48;
mix.code.ENN1 = 49;
mix.code.ENN2 = 50;
mix.code.ENN3 = 51;
mix.code.ENN4 = 52;
mix.code.ENN5 = 53;
mix.code.ENN6 = 54;
mix.code.ENNX = 55;
mix.code.INCA = 48;
mix.code.INC1 = 49;
mix.code.INC2 = 50;
mix.code.INC3 = 51;
mix.code.INC4 = 52;
mix.code.INC5 = 53;
mix.code.INC6 = 54;
mix.code.INCX = 55;
mix.code.DECA = 48;
mix.code.DEC1 = 49;
mix.code.DEC2 = 50;
mix.code.DEC3 = 51;
mix.code.DEC4 = 52;
mix.code.DEC5 = 53;
mix.code.DEC6 = 54;
mix.code.DECX = 55;
mix.code.CMPA = 56;
mix.code.CMP1 = 57;
mix.code.CMP2 = 58;
mix.code.CMP3 = 59;
mix.code.CMP4 = 60;
mix.code.CMP5 = 61;
mix.code.CMP6 = 62;
mix.code.CMPX = 63;

mix.code.Alphabet = [
    " ",
    "A",
    "B",
    "C",
    "D",
    "E",
    "F",
    "G",
    "H",
    "I",
    "\u0394",
    "J",
    "K",
    "L",
    "M",
    "N",
    "O",
    "P",
    "Q",
    "R",
    "\u03a3",
    "\u03a0",
    "S",
    "T",
    "U",
    "V",
    "W",
    "X",
    "Y",
    "Z",
    "0",
    "1",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    ".",
    ",",
    "(",
    ")",
    "+",
    "-",
    "*",
    "/",
    "=",
    "$",
    "<",
    ">",
    "@",
    ";",
    ":",
    "'",
];

export { mix };
