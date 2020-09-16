function assert() {
    try {
        
        switch (arguments.length) {
        case 1:
            let condition = arguments[0];
            throw `assertion failed`;
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
