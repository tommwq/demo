var app = new Vue({
    el: "#app",
    data: {
	seen: true
    }
});

function hide() {
    app.seen = false;
}
