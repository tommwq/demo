class Html {

    static body() {
        return document.body;
    }

    static pick(id) {
        return document.querySelector(`#${id}`);
    }

    static removeChildren(element) {
        while (element && element.firstChild) {
            element.removeChild(element.firstChild);
        }
    }

    static element(parent, type, attrDict) {
        var node = document.createElement(type);
        for (var attr in attrDict) {
            var value = attrDict[attr];
            node[attr] = value;
        }
        
        if (parent) {
            parent.appendChild(node);
        }
        
        return node;
    }

    static suicide(element) {
        element.parentNode.removeChild(element);
    }

    static listen(event, listener) {
        Html.body().addEventListener(event, listener);
    }

    static emit(event, detail) {
        var evt = new CustomEvent(event, {'detail': detail});
        Html.body().dispatchEvent(evt);
    }

    static on(element, event, listener) {
        element.addEventListener(event, listener);
    }

    static queryString(data) {
        var pieces = [];
        for (var index in data) {
            pieces.push(`${index}=${data[index]}`);
        }

        return pieces.join('&');
    }

    static get(url, data, event) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", url, true);
        ["load", "error", "timeout"].forEach((originEvent) => {
            xhr.addEventListener(originEvent, (e) => {
                Html.emit(event, e);
            });
        });
        xhr.send(queryString(data));
    }

    static post(url, data, event) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", url, true);
        ["load", "error", "timeout"].forEach((originEvent) => {
            xhr.addEventListener(originEvent, (e) => {
                Html.emit(event, e);
            });
        });
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(Html.queryString(data));
    }

    static listMethod(obj) {
        var methods = Object.getOwnPropertyNames(Object.getPrototypeOf(obj));
        // 第一个是constructor，无论是否显式定义。
        methods.shift();
        return methods;
    }

    static parseJson(text) {
        try {
            return JSON.parse(text);
        } catch (error) {
            return null;
        }
    }

    static cookie(name) {
        var blocks = document.cookie.split(';');
        for (var index in blocks) {
            var pieces = blocks[index].split('=');
            var key = pieces[0].trim();
            if (key == name) {
                return pieces[1].trim();
            }
        }

        return null;
    }

    static setCookie(name, value, expireSecond=null) {
        var exp = '';
        
        if (expireSecond) {
            var time = new Date();
            time.setTime(time.getTime() + expireSecond * 1000);
            exp = `;exipres=${time.toUTCString()}`;
        }

        document.cookie = `${name}=${value} ${exp}`;
    }
}

class App {
    constructor() {
        this.user = new User();
        this.blog = new Blog();
        this.start();
    }

    onLogin(event) {
        if (this.user.is_logged_in) {
            this.blog.list();
            return;
        }

        var payload = Html.parseJson(event.detail.target.responseText);
        if (payload == null || payload.code != 0) {
            // login failed
            this.showLogin();
        } else {
            this.user.onLogin(payload.username, payload.user_id);
            this.blog.list();
        }
    }
    
    onRegister(event) {
        var payload = Html.parseJson(event.detail.target.responseText);

        if (payload == null || payload.code != 0) {
            // login failed
            this.showLogin();
        } else {
            this.user.onLogin(payload.username, payload.user_id);
            this.blog.list();
        }
    }

    onBlog(event) {
        var payload = Html.parseJson(event.detail.target.responseText);
        if (payload == null || payload.code != 0) {
            // login failed
            // todo
        } else {
            this.blog.setBlogList(payload.payload);
            this.showBlog();
        }
    }

    onBlogDetail(event) {
        var payload = Html.parseJson(event.detail.target.responseText);
        this.showBlogDetail(payload.payload);
    }

    setupEventListener() {
        var methods = Html.listMethod(this);
        for (var index in methods) {
            var method = methods[index];
            if (method.startsWith('on')) {
                var event = method.substring(2).toUpperCase();
                var listener = this[method].bind(this);
                Html.listen(event, listener);
            }
        }
    }

    start() {
        this.setupEventListener();
        this.showLoading();
        this.user.login();
    }

    showLoading() {
        this.clear();
        Html.element(Html.body(), "p").textContent = "LOADING";
    }

    showLogin() {
        this.clear();
        var root = Html.element(Html.body(), "div");
        Html.element(root, 'span').innerText = '用户';
        Html.element(root, 'input', {'id': 'input-username'}).value = 'john';
        Html.element(root, 'span').innerText = '密码';
        Html.element(root, 'input', {'id': 'input-password'}).value = 'john';
        var buttonLogin = Html.element(root, 'button');
        buttonLogin.textContent = '登录';
        Html.on(buttonLogin, 'click', (e) => {
            this.user.username = Html.pick('input-username').value;
            this.user.password = Html.pick('input-password').value;
            this.user.login();
        });
        var buttonRegister = Html.element(root, 'button');
        buttonRegister.textContent = '注册';
        Html.on(buttonRegister, 'click', (e) => {
            this.user.username = Html.pick('input-username').value;
            this.user.password = Html.pick('input-password').value;
            this.user.register();
        });
    }

    showBlog() {
        this.clear();
        var root = Html.element(Html.body(), 'table');
        this.blog.blogList.forEach((item) => {
            var tr = Html.element(root, 'tr');
            Html.element(tr, 'td', {'textContent': item.id});
            Html.on(Html.element(Html.element(tr, 'td'), 'span', {'textContent': item.title}),
                    'click', (e) => {
                        this.blog.getBlog(item.id);
                    });
            Html.element(tr, 'td', {'textContent': item.author});
            Html.element(tr, 'td', {'textContent': item.publish_date});
        });
    }

    showBlogDetail(payload) {
        this.clear();
        var root = Html.element(Html.body(), 'div');
        Html.element(root, 'p', {'textContent': payload.title});
        Html.element(root, 'p', {'textContent': payload.author});
        Html.element(root, 'p', {'textContent': payload.publish_date});
        Html.element(root, 'p', {'textContent': payload.content});
    }

    clear() {
        Html.removeChildren(Html.body());
    }
}

class User {
    constructor() {
        this.is_logged_in = false;
        this.username = null;
        this.password = null;
        this.user_id = null;
    }

    onLogin(username, user_id) {
        this.username = username;
        this.user_id = user_id;
        this.is_logged_in = true;
        Html.setCookie('username', username);
        Html.setCookie('user_id', user_id);
    }

    readCookie() {
        if (Html.cookie('username') == null) {
            return;
        }

        this.username = Html.cookie('username');
        this.user_id = Html.cookie('user_id');
        this.is_logged_in = true;
    }

    login() {
        this.readCookie();
        
        if (this.is_logged_in) {
            Html.emit('LOGIN');
        } else {
            Html.post("/api/login", {
                'username': this.username,
                'password': this.password,
            }, "LOGIN");
        }
    }

    logout() {
    }

    register() {
        if (this.is_logged_in) {
            Html.emit('LOGIN');
        } else {
            Html.post("/api/register", {
                'username': this.username,
                'password': this.password,
            }, "REGISTER");
        }
    }
}

class Blog {

    constructor() {
        this.blogList = [];
    }
    
    list() {
        Html.post("/api/blog", {}, "BLOG");
    }

    setBlogList(list) {
        this.blogList = list;
    }

    getBlog(id) {
        Html.post(`/api/blog/${id}`, {}, 'BLOGDETAIL');
    }
}

new App();

