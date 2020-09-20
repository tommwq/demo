import { Reader } from "./reader.js";
import { assert } from "./test.js";

let reader = new Reader([1, 2, 3]);

assert(reader.get(), 1);
assert(!reader.isEnd());

assert(reader.get(), 2);
assert(reader.get(), 3);
assert(reader.isEnd());

reader.unget();
assert(!reader.isEnd());

assert(reader.get(), 3);
assert(reader.isEnd());
