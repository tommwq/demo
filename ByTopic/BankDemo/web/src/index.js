import ElementUI from 'element-ui';
import Vue from 'vue';
import axios from 'axios';


import * as returnCode from "./common/returnCode.js";
import {User} from "./domain/user.js";
import * as loginPanel from "./component/login_panel.js"


Vue.component('login-panel', loginPanel.create());

let app = new Vue({
    el: "#app",
    data: {
	user: new User(axios),
	message: "消息",
	ajax: axios
    },
    methods: {
	showMessage: function(line) {
            this.message = line;
	},
	callback: function(data) {
            this.log(returnCode[data.returnCode]);
	},
	login: function(username, password) {
	    this.showMessage("");
	    this.ajax.post('/api/logout', {username, password})
		.then((result) => {
		    const code = result.data.returnCode;
		    if (code == returnCode.OK) {
			this.user.login(username);
			console.log(this.user.username);
			console.log(this.user.isLoggedIn);
			console.log(this.user);
			this.showMessage("登陆成功");
		    } else {
			this.showMessage("登陆失败" + returnCode[code]);
		    }})
		.catch((error) => {
		    this.showMessage(error.toString());
		});
	},
	logout: function() {
	    this.ajax.post('/api/logout', {})
		.then(function(result) {
		    user.logout();
		}).catch(function(error) {
		    user.logout();
		    this.showMessage(error);
		});
	}
	
	// listAccount: function() {
	//     axios.post('/api/account', {})
	// 	.then(function(result) {
	// 	    this.log(result.data);
	//     	    console.log(result.data);
	//     	    this.callback(result.data);
	// 	}).catch(function(error) {
	// 	    this.log(error);
	// 	});
	// },
	// save: function() {
	//     axios.post(`/api/${this.account}/save`, {
	// 	account: this.account,
	// 	money: this.money
	//     }).then(function(result) {
	// 	this.log("save");
	// 	this.callback(result.data);
	//     }).catch(function(error) {
	// 	this.log("fail to save");
	//     });
	// },
	// withdraw: function() {
	//     axios.post(`/api/${this.account}/withdraw`, {
	// 	account: this.account,
	// 	money: this.money
	//     }).then(function(result) {
	// 	this.log("withdraw");
	// 	this.callback(result.data);
	//     }).catch(function(error) {
	// 	this.log("fail to withdraw");
	//     });
	// },
	// balance: function() {
    	//     axios.post(`/api/${this.account}/balance`, {
    	// 	account: this.account,
    	//     }).then(function(result) {
    	// 	this.log("balance");
    	// 	this.callback(result.data);
    	// 	console.log(result.data);
    	//     }).catch(function(error) {
    	// 	this.log("fail to save");
    	//     });
    	// },
	// openAccount: function() {
    	//     axios.post(`/api/account/open`, {})
	// 	.then(function(result) {
    	// 	    this.callback(result.data);
    	// 	}).catch(function(error) {
	// 	    console.log(error);
    	// 	});
    	// },
	// closeAccount: function() {
    	//     axios.post(`/api/${this.account}/close`, {})
	// 	.then(function(result) {
    	// 	    this.callback(result.data);
    	// 	}).catch(function(error) {
	// 	    console.log(error);
    	// 	});
    	// },
    }});


