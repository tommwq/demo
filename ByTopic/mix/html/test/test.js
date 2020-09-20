// 断言
// assert(condition)
// assert(expect, actural)
function assert() {
    try {
        
        switch (arguments.length) {
        case 1:
            let condition = arguments[0];
            if (!condition) {
                throw `assertion failed: ${condition}`;
            }
            break;
        case 2:
            let expect = arguments[0];
            let actural = arguments[1];
            if (expect != actural) {
                throw `expect ${expect} while get ${actural}`;
            }
            break;
        default:
            break;
        }
    } catch (error) {
        console.warn(`FAIL: ${error}`);
    }
}

export {
    assert
};
