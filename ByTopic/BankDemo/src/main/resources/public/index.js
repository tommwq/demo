
var app = new Vue({
    el: "#app",
    data: {
	username: "",
	password: "",
	message: ""
    },
    methods: {
	login: function() {
	    axios.post('http://localhost:8082/login', {
		username: this.username,
		password: this.password
	    }).then(function(result) {
		// TODO
	    }).catch(function(error) {
		// TODO
	    });
	}
    }
});
