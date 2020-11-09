class html {
    // 创建div标签。
    static div(attrs) {
        return html.setAttrs(document.createElement("div"), attrs);
    }

    // 创建span标签。
    static span(attrs) {
        return html.setAttrs(document.createElement("span"), attrs);
    }

    // 为element设置属性attrs。
    static setAttrs(element, attrs) {
        for (let key in attrs) {
            element[key] = attrs[key];
        }
        return element;
    }

    // 根据id选择标签。
    static element(id) {
        return document.getElementById(id);
    }

    // 根据tag选择标签。
    static tag(tagName) {
        return document.getElementsByTagName(tagName);
    }
}
