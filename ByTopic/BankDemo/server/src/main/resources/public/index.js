/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/index.js");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/common/returnCode.js":
/*!**********************************!*\
  !*** ./src/common/returnCode.js ***!
  \**********************************/
/*! no static exports found */
/***/ (function(module, exports) {

eval("\nconst codeTable = {\n    \"OK\": 0,\n    \"LOGIN_FAILED\":1,\n    \"NOT_LOGGED_IN\":2,\n    \"SAVE_FAILED\":3,\n    \"WITHDRAW_FAILED\":4,\n    \"GET_BALANCE_FAILED\":5,\n    \"OPEN_ACCOUNT_FAILED\":6,\n    \"CLOSE_ACCOUNT_FAILED\":7,\n    \"REVERSED\":999999\n};\n\nvar messageTable = {};\nvar returnCode = {\n    getMessage: function(returnCode) {\n\treturn messageTable[returnCode];\n    }\n};\n\nfunction init() {\n    for (let message in codeTable) {\n\tlet code = codeTable[message];\n\tmessageTable[message] = code;\n\treturnCode[code] = message;\n    }\n}\n\ninit();\n\nmodule.exports = returnCode;\n\n\n//# sourceURL=webpack:///./src/common/returnCode.js?");

/***/ }),

/***/ "./src/index.js":
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _common_returnCode_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/returnCode.js */ \"./src/common/returnCode.js\");\n/* harmony import */ var _common_returnCode_js__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_common_returnCode_js__WEBPACK_IMPORTED_MODULE_0__);\n\n\nvar app = new Vue({\n    el: \"#app\",\n    data: {\n\tusername: \"Adam\",\n\tpassword: \"123456\",\n\tmessage: \"hello\",\n\tmoney: 50,\n\taccount: 1\n    },\n    methods: {\n\tlog: function(line) {\n            app.message += `${line}`;\n\t},\n\tcallback: function(data) {\n            app.log(returnCodeMessage[data.returnCode]);\n\t},\n\tlogin: function() {\n\t    axios.post('/api/login', {\n\t\tusername: this.username,\n\t\tpassword: this.password\n\t    }).then(function(result) {\n\t        app.log(\"login\");\n\t        app.callback(result.data);\n\t    }).catch(function(error) {\n\t\tapp.log(\"fail to log in\");\n\t    });\n\t},\n\tlogout: function() {\n\t    axios.post('/api/logout', {})\n\t\t.then(function(result) {\n\t\t    this.message += \"ok\";\n\t\t    app.log(\"logged out\");\n\t\t    app.callback(result.data);\n\t\t}).catch(function(error) {\n\t\t    app.log(\"fail to log out\");\n\t\t});\n\t},\n\tlistAccount: function() {\n\t    axios.post('/api/account', {})\n\t\t.then(function(result) {\n\t\t    app.log(result.data);\n\t    \t    console.log(result.data);\n\t    \t    app.callback(result.data);\n\t\t}).catch(function(error) {\n\t\t    app.log(error);\n\t\t});\n\t},\n\tsave: function() {\n\t    axios.post(`/api/${this.account}/save`, {\n\t\taccount: this.account,\n\t\tmoney: this.money\n\t    }).then(function(result) {\n\t\tapp.log(\"save\");\n\t\tapp.callback(result.data);\n\t    }).catch(function(error) {\n\t\tapp.log(\"fail to save\");\n\t    });\n\t},\n\twithdraw: function() {\n\t    axios.post(`/api/${this.account}/withdraw`, {\n\t\taccount: this.account,\n\t\tmoney: this.money\n\t    }).then(function(result) {\n\t\tapp.log(\"withdraw\");\n\t\tapp.callback(result.data);\n\t    }).catch(function(error) {\n\t\tapp.log(\"fail to withdraw\");\n\t    });\n\t},\n\tbalance: function() {\n    \t    axios.post(`/api/${this.account}/balance`, {\n    \t\taccount: this.account,\n    \t    }).then(function(result) {\n    \t\tapp.log(\"balance\");\n    \t\tapp.callback(result.data);\n    \t\tconsole.log(result.data);\n    \t    }).catch(function(error) {\n    \t\tapp.log(\"fail to save\");\n    \t    });\n    \t},\n\topenAccount: function() {\n    \t    axios.post(`/api/account/open`, {})\n\t\t.then(function(result) {\n    \t\t    app.callback(result.data);\n    \t\t}).catch(function(error) {\n\t\t    console.log(error);\n    \t\t});\n    \t},\n\tcloseAccount: function() {\n    \t    axios.post(`/api/${this.account}/close`, {})\n\t\t.then(function(result) {\n    \t\t    app.callback(result.data);\n    \t\t}).catch(function(error) {\n\t\t    console.log(error);\n    \t\t});\n    \t},\n    }\n});\n\n\n\n//# sourceURL=webpack:///./src/index.js?");

/***/ })

/******/ });