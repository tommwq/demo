let word1 = new Word(true, 0);
assert(0, word1.value());

let word2 = new Word(true, 10);
assert(10, word2.value());

word2.assign(100);
assert(100, word2.value());

word2.assign(0);
word2.assignByte(5, new Byte(11));
assert(11, word2.value());

word2.assign(0);
word2.assignByte(4, new Byte(10));
assert(640, word2.value());


word1 = Word.create(1, 2, 3, 4);
assert("[+ 0 1 2 3 4]", word1.print());

console.log("test mix pass");
