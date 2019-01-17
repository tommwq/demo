
var app = new Vue({
    el: "#app",
    data: {
	username: "Adam",
	password: "123456",
	message: "",
	money: 50,
	account: 1
    },
    methods: {
	login: function() {
	    axios.post('http://localhost:8082/login', {
		username: this.username,
		password: this.password
	    }).then(function(result) {
		console.log("logged in");
	    }).catch(function(error) {
		console.log("fail to log in");
	    });
	},
	logout: function() {
	    axios.post('http://localhost:8082/logout', {})
		.then(function(result) {
		    console.log("logged out");
		}).catch(function(error) {
		    console.log("fail to log out");
		});
	},
	listAccount: function() {
	    axios.post('http://localhost:8082/account', {})
		.then(function(result) {
		    console.log(result.data);
		}).catch(function(error) {
		    console.log(error);
		});
	},
	save: function() {
	    axios.post(`http://localhost:8082/${this.account}/save`, {
		account: this.account,
		money: this.money
	    }).then(function(result) {
		console.log("save");
	    }).catch(function(error) {
		console.log("fail to save");
	    });
	},
	withdraw: function() {
	    axios.post(`http://localhost:8082/${this.account}/withdraw`, {
		account: this.account,
		money: this.money
	    }).then(function(result) {
		console.log("withdraw");
	    }).catch(function(error) {
		console.log("fail to withdraw");
	    });
	}
    }
});
