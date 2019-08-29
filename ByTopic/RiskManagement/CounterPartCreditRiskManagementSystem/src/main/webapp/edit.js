var app = new Vue({
    el: "#app",
    data: {
        counterpartList: []
    },
    methods: {
        // update: function() {
        //     axios.get("/api/counterpart")
        //         .then(payload => this.counterpartList = payload.data)
        //         .catch(error => console.log(error));
        // },
        createIndustry: function(industryName) {
            let trimmedName = industryName.trim();
            console.log(trimmedName);
            axios.post("/api/industry/new", {name:trimmedName})
                .then(payload => console.log(payload));
            // TODO
        },

    }
});

