var app = new Vue({
    el: "#app",
    data: {
        deviceIdList: []
    },
    methods: {
        update: function() {
            console.log("ok");
            axios.get("/api/devices")
                .then(payload => this.deviceIdList = payload.data)
                .catch(error => console.log(error));
        }
    }
});

app.update();


