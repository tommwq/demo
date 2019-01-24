
const messageCodePairs = {
    "OK": 0,
    "LOGIN_FAILED":1,
    "NOT_LOGGED_IN":2,
    "SAVE_FAILED":3,
    "WITHDRAW_FAILED":4,
    "GET_BALANCE_FAILED":5,
    "OPEN_ACCOUNT_FAILED":6,
    "CLOSE_ACCOUNT_FAILED":7,
    "REVERSED":999999
};

const unknownReturnCode = "UNKNOWN_RETURN_CODE";

function init() {
    let returnCode = {};
    
    for (let message in messageCodePairs) {
	let code = messageCodePairs[message];
	returnCode[code] = message;
	returnCode[message] = code;
    }

    return returnCode;
}


module.exports = init();
