function assert(expect, actural) {
    if (expect != actural) {
        throw `expect ${expect} while get ${actural}`;
    }
}

// assert(1, 10);

let word1 = new Word();
assert(0, word1.get());

let word2 = new Word(10);
assert(10, word2.get());

word2.set(100);
assert(100, word2.get());

word2.set(0);
word2.setByte(5, new Byte(11));
assert(11, word2.get());

word2.set(0);
word2.setByte(4, new Byte(10));
assert(640, word2.get());


console.log("test passed");
