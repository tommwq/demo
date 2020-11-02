// 模拟服务器
class Server {
    constructor() {
        this.todoList = new TodoListEntity();
        this.importTestData();
    }

    createTodoItem(content) {
        return this.todoList.createTodoItem(content);
    }

    finishTodoItem(id) {
        this.todoList.finishTodoItem(id);
    }

    get todoItems() {
        return this.todoList.items;
    }

    importTestData() {
        for (let todo of ["读书", "听音乐", "看电影"]) {
            this.createTodoItem(todo);
        }

        this.finishTodoItem(1);
    }
}

class TodoListEntity {
    constructor() {
        this.items = [];
        this.nextId = 1;
    }

    createTodoItem(content) {
        let item = new TodoItemEntity(this.nextId, content);
        this.items.push(item);
        this.nextId++;
        return item.id;
    }

    finishTodoItem(id) {
        for (let item of this.items) {
            if (item.id == id) {
                item.finish = true;
                break;
            }
        }
    }
}

class TodoItemEntity {
    constructor(id, content) {
        this.id = id;
        this.content = content;
        this.finish = false;
    }
}

