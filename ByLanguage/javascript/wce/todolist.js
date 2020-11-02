class TodoItem extends Component {

    static get observedAttributes() {
        return ['data-status', 'data-content'];
    }

    connectedCallback() {
        super.connectedCallback();
        let cb = this.child("checkbox")
        cb.addEventListener("click", () => {
            let finish = cb.checked;
            this.setAttribute("data-status", finish ? "done" : "");

            if (finish) {
                let todoItemId = this.getAttribute("data-id");
                document.dispatchEvent(new CustomEvent("finish-todo-item", {
                    detail: {
                        todoItemId: todoItemId
                    }
                }));
            }
        });
    }

    update(attributeName, oldValue, newValue) {
        super.update(attributeName, oldValue, newValue);
    }
}

class TodoList extends Component {

    static get observedAttributes() {
        return ["data-todo-list"];
    }
    
    connectedCallback() {
        super.connectedCallback();

        this.child("create").addEventListener("click", (e) => {
            let content = this.child("content").value;
            if (content) {
                document.dispatchEvent(new CustomEvent("create-todo-item", {
                    detail: {
                        content: content
                    }
                }));
            }
        });
    }

    update(attributeName, oldValue, newValue) {
        super.update(attributeName, oldValue, newValue);

        let todoList;
        
        switch (attributeName) {
        case "data-todo-list":
            if (newValue == "") {
                break;
            }
            todoList = JSON.parse(newValue);
            Component.removeAllChildren(this.child("todo-items"));
            for (let todoItem of todoList) {
                let item = document.createElement("todo-item");
                item.setAttribute("data-id", todoItem.id);
                item.setAttribute("data-content", todoItem.content);
                item.setAttribute("data-status", todoItem.finish ? "done" : "");
                this.child("todo-items").appendChild(item);
            }
            break;
        default:
            break;
        }
    }
}


(() => {
    Component.define(TodoItem);
    Component.define(TodoList);
})();
