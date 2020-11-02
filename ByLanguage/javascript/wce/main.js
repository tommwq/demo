(() => {
    let server = new Server();

    document.addEventListener("finish-todo-item", (e) => {
        server.finishTodoItem(e.detail.todoItemId);
    });

    document.addEventListener("create-todo-item", (e) => {
        server.createTodoItem(e.detail.content);
        let todoItems = server.todoItems;
        let str = JSON.stringify(todoItems);
        document.getElementById("todo-list").setAttribute("data-todo-list", str);    
    });
    
    let todoItems = server.todoItems;
    let str = JSON.stringify(todoItems);
    document.getElementById("todo-list").setAttribute("data-todo-list", str);    
})();
