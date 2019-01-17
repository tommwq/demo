const ReturnCode = {
    "OK": 0,
    "LOGIN_FAILED":1,
    "NOT_LOGGED_IN":2,
    "SAVE_FAILED":3,
    "WITHDRAW_FAILED":4,
    "GET_BALANCE_FAILED":5,
    "OPEN_ACCOUNT_FAILED":6,
    "REVERSED":999999
};

const ReturnCodeMessage = {
    0:"OK",
    1:"LOGIN_FAILED",
    2:"NOT_LOGGED_IN",
    3:"SAVE_FAILED",
    4:"WITHDRAW_FAILED",
    5:"GET_BALANCE_FAILED",
    6:"OPEN_ACCOUNT_FAILED",
    999999:"REVERSED",
};


var app = new Vue({
    el: "#app",
    data: {
	username: "Adam",
	password: "123456",
	message: "hello",
	money: 50,
	account: 1
    },
    methods: {
	log: function(line) {
            app.message += `${line}`;
	},
	callback: function(data) {
            app.log(ReturnCodeMessage[data.returnCode]);
	},
	login: function() {
	    axios.post('http://localhost:8082/login', {
		username: this.username,
		password: this.password
	    }).then(function(result) {
	        app.log("login");
	        app.callback(result.data);
	    }).catch(function(error) {
		app.log("fail to log in");
	    });
	},
	logout: function() {
	    axios.post('http://localhost:8082/logout', {})
		.then(function(result) {
		    this.message += "ok";
		    app.log("logged out");
		    app.callback(result.data);
		}).catch(function(error) {
		    app.log("fail to log out");
		});
	},
	listAccount: function() {
	    axios.post('http://localhost:8082/account', {})
		.then(function(result) {
		    app.log(result.data);
	    	    console.log(result.data);
	    	    app.callback(result.data);
		}).catch(function(error) {
		    app.log(error);
		});
	},
	save: function() {
	    axios.post(`http://localhost:8082/${this.account}/save`, {
		account: this.account,
		money: this.money
	    }).then(function(result) {
		app.log("save");
		app.callback(result.data);
	    }).catch(function(error) {
		app.log("fail to save");
	    });
	},
	withdraw: function() {
	    axios.post(`http://localhost:8082/${this.account}/withdraw`, {
		account: this.account,
		money: this.money
	    }).then(function(result) {
		app.log("withdraw");
		app.callback(result.data);
	    }).catch(function(error) {
		app.log("fail to withdraw");
	    });
	},
	balance: function() {
    	    axios.post(`http://localhost:8082/${this.account}/balance`, {
    		account: this.account,
    	    }).then(function(result) {
    		app.log("balance");
    		app.callback(result.data);
    		console.log(result.data);
    	    }).catch(function(error) {
    		app.log("fail to save");
    	    });
    	},
	openAccount: function() {
    	    axios.post(`http://localhost:8082/account/open`, {})
		.then(function(result) {
    		    app.callback(result.data);
    		}).catch(function(error) {
		    console.log(error);
    		});
    	},
	closeAccount: function() {
    	    axios.post(`http://localhost:8082/{$this.account}/close`, {})
		.then(function(result) {
    		    app.callback(result.data);
    		}).catch(function(error) {
		    console.log(error);
    		});
    	},
    }
});

