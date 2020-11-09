const EQUAL = "等于";
const LESS = "小于";
const GREATER = "大于";

class MixSiumlatorView {
    constructor(parent) {
        if (parent == null) {
            parent = html.tag("body")[0];
        }

        this._createView(parent);
    }

    _createView(parent) {
        let leftPanel = html.div({"className":"left-panel"});
        let ra = new RegisterView(leftPanel, "寄存器A", new WordView());
        let rx = new RegisterView(leftPanel, "寄存器X", new WordView());
        let ri = [];
        for (let i = 1; i <= 6; i++) {
            ri.push(new RegisterView(leftPanel, "寄存器" + i, new AddressWordView()));
        }
        let rj = new RegisterView(leftPanel, "寄存器J", new AddressWordView());
        
        ra.update(Word.create(1, 2, 3, 4));
        ri[0].update(Word.create(1, 2, 3, 4));

        let overflowToggle = new ToggleView(leftPanel);
        let compareIndicator = new IndicatorView(leftPanel);

        let rightPanel = html.div({"className":"right-panel"});
        for (let i = 0; i < 4000; i++) {
            new MemoryView(rightPanel, i);
        }

        parent.appendChild(leftPanel);
        parent.appendChild(rightPanel);
    }
}

class RegisterView {
    constructor(parent, name, underlyingValueView) {
        this._name = name;
        this._createView(parent, name, underlyingValueView);
        this.update(new Word(true, 0));
    }

    _createView(parent, name, underlyingValueView) {
        this._root = html.setAttrs(html.div(), {"className":"register"});
        this._root.appendChild(html.span({"className":"register-name", "textContent": name}));
        this._wordView = underlyingValueView;
        underlyingValueView.setParent(this._root);
        parent.appendChild(this._root);
    }

    update(word) {
        this._wordView.update(word);
    }
}

class WordView {
    setParent(parent) {        
        this._createView(parent);
        this.update(new Word(true, 0));
    }

    _createView(parent) {
        this._root = html.setAttrs(html.div(), {"className":"word"});
        for (let i = 0; i <= 5; i++) {
            this._root.appendChild(html.span({"className":"word"}));
        }
        parent.appendChild(this._root);
    }

    update(word) {
        this._value = Word.copy(word);
        this._render();
    }

    _render() {
        this._root.children[0].textContent = this._value.isPositive() ? "+" : "-";
        for (let i = 1; i <= 5; i++) {
            this._root.children[i].textContent = this._value.getByte(i).value();
        }
    }
}

class AddressWordView {
    setParent(parent) {        
        this._createView(parent);
        this.update(new Word(true, 0));
    }

    _createView(parent) {
        this._root = html.setAttrs(html.div(), {"className":"word"});
        for (let i = 0; i <= 2; i++) {
            this._root.appendChild(html.span({"className":"word"}));
        }
        parent.appendChild(this._root);
    }

    update(word) {
        this._value = Word.copy(word);
        this._render();
    }

    _render() {
        this._root.children[0].textContent = this._value.isPositive() ? "+" : "-";
        for (let i = 1; i <= 2; i++) {
            this._root.children[i].textContent = this._value.getByte(3 + i).value();
        }
    }
}

class ToggleView {
    constructor(parent) {
        this._root = html.div();
        this._root.appendChild(html.span({"textContent":"溢出开关"}));
        this._root.appendChild(html.span({"className":"toggle"}));
        parent.appendChild(this._root);
        this.update(false);
    }

    update(on) {
        this._on = on;
        this._render();
    }

    _render() {
        this._root.children[1].textContent = this._on ? "ON" : "OFF";
    }
}

class IndicatorView {
    constructor(parent) {
        this._root = html.div();
        this._root.appendChild(html.span({"textContent":"比较指示器"}));
        this._root.appendChild(html.span());
        parent.appendChild(this._root);
        this.update(EQUAL);
    }

    update(indicator) {
        this._indicator = indicator;
        this._render();
    }

    _render() {
        this._root.children[1].textContent = this._indicator;
    }
}

class MemoryView {
    constructor(parent, address) {
        this._address = address;
        this._createView(parent, address);
        this.update(new Word(true, 0));
    }

    _createView(parent, address) {
        this._root = html.setAttrs(html.div(), {"className":"memory"});
        this._root.appendChild(html.span({
            "className": "memory-address",
            "textContent": address.toString().padStart(4, "0"),
            "width": "4ch"
        }));
        this._wordView = new WordView();
        this._wordView.setParent(this._root);
        parent.appendChild(this._root);
    }

    update(word) {
        this._wordView.update(word);
    }
}

new MixSiumlatorView();

