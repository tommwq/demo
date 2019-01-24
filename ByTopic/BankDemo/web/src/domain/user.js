class User {
    
    constructor() {
	this.reset();
    }

    reset() {
	this.isLoggedIn = false;
	this.username = "";
	this.operateHistory = [];
    }

    login(username) {
	console.log(this);
	this.username = username;
	this.isLoggedIn = true;
	console.log(this);
    }

    login() {
	this.isLoggedIn = false;
    }

    recordOperate(operation) {
	this.operateHistory.push(operation);
    }
}


export { User };
