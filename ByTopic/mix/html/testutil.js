function assert(expect, actural) {
    if (expect != actural) {
        throw `expect ${expect} while get ${actural}`;
    }
}

function assertTrue(condition) {
    if (!condition) {
        throw `assertion failed`;
    }
}
