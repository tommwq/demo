
const codeTable = {
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

var messageTable = {};
var returnCode = {
    getMessage: function(returnCode) {
	return messageTable[returnCode];
    }
};

function init() {
    for (let message in codeTable) {
	let code = codeTable[message];
	messageTable[message] = code;
	returnCode[code] = message;
    }
}

init();

module.exports = returnCode;
