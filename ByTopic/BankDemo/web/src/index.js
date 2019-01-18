import "./common/returnCode.js"

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
            app.log(returnCodeMessage[data.returnCode]);
	},
	login: function() {
	    axios.post('/api/login', {
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
	    axios.post('/api/logout', {})
		.then(function(result) {
		    this.message += "ok";
		    app.log("logged out");
		    app.callback(result.data);
		}).catch(function(error) {
		    app.log("fail to log out");
		});
	},
	listAccount: function() {
	    axios.post('/api/account', {})
		.then(function(result) {
		    app.log(result.data);
	    	    console.log(result.data);
	    	    app.callback(result.data);
		}).catch(function(error) {
		    app.log(error);
		});
	},
	save: function() {
	    axios.post(`/api/${this.account}/save`, {
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
	    axios.post(`/api/${this.account}/withdraw`, {
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
    	    axios.post(`/api/${this.account}/balance`, {
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
    	    axios.post(`/api/account/open`, {})
		.then(function(result) {
    		    app.callback(result.data);
    		}).catch(function(error) {
		    console.log(error);
    		});
    	},
	closeAccount: function() {
    	    axios.post(`/api/${this.account}/close`, {})
		.then(function(result) {
    		    app.callback(result.data);
    		}).catch(function(error) {
		    console.log(error);
    		});
    	},
    }
});

