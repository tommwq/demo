var app = new Vue({
    el: "#app",
    data: {
        counterpartList: []
    },
    methods: {
        update: function() {
            axios.get("/api/counterpart")
                .then(payload => this.counterpartList = payload.data)
                .catch(error => console.log(error));
        }
    }
});

app.update();


