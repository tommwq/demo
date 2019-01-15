var app = new Vue({
    el: "#app",
    data: {
	todoList: [
	    { text: "学习 JavaScript" },
	    { text: "学习 Vue" },
	    { text: "整个牛项目" }
	]
    }
});

var count = 0;

function addTodoItem() {
    count = count + 1;
    app.todoList.push({text: 'todo item #' + count});
}
