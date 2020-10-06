class Character {
    static isUpperCase(ch) {
        return ch.length == 1 && "A" <= ch && ch <= "Z";
    }

    static isLowerCase(ch) {
        return ch.length == 1 && "a" <= ch && ch <= "z";
    }
}

class Text {
    static pascalToKebab(text) {
        let s = "";

        for (let i = 0; i < text.length; i++) {
            let current = text.charAt(i);
            let previous = i > 0 ? text.charAt(i - 1) : "";
            let next = i + 1 < text.length ? text.charAt(i + 1) : "";
            
            if (Character.isLowerCase(current)) {
                s = s.concat(current);
                continue;
            }

            if (Character.isLowerCase(next)) {
                if (s.length > 0) {
                    s = s.concat("-");
                }
                s = s.concat(current.toLowerCase());
                continue;
            }

            s = s.concat(current.toLowerCase());
        }

        return s;
    }
}

/**
 * Web组件
 */
class Component extends HTMLElement {

    static define(componentClass) {
        customElements.define(Text.pascalToKebab(componentClass.name), componentClass);
    }
    
    static descendants(node) {
        let result = [];
        if (node instanceof Node) {
            for (let c of node.children) {
                result.push(c);
            }
            for (let c of node.children) {
                for (let d of Component.descendants(c)) {
                    result.push(d);
                }
            }
        }
        return result;
    }
    
    constructor() {
        super();

        this.attributeSlots = {}; // componentAttribute: {element: X, elementAttribute: Y}
    }

    connectedCallback() {
        this.append(this.createContent());
    }

    disconnectedCallback() {
    }

    attributeChangedCallback(attributeName, oldValue, newValue) {
        this.update(attributeName, oldValue, newValue);
    }

    update(attributeName, oldValue, newValue) {
        if (attributeName in this.attributeSlots) {
            for (let slot of this.attributeSlots[attributeName]) {
                slot.element[slot.elementAttribute] = newValue;
            }
        }
    }

    componentName() {
        return this.constructor.name;
    }

    componentTagName() {
        return Text.pascalToKebab(this.componentName());
    }

    componentTemplateId() {
        return Text.pascalToKebab(this.componentName()).concat("-template");
    }

    static removeAllChildren(element) {
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }
    }

    createContent() {
        let template = document.getElementById(this.componentTemplateId());
        let content = template.content.cloneNode(true);
        let re = /^<!--#([a-zA-Z0-9-]*)-->$/g;

        for (let c of Component.descendants(content)) {
            let attrs = c.getAttributeNames();
            attrs.push("innerHTML");
            for (let attr of attrs) {
                if (attr == "class") {
                    attr = "className";
                }
                
                let result = re.exec(c[attr]);
                if (result == null) {
                    continue;
                }
                let componentAttribute = result[1];
                let slot = {
                    element: c,
                    elementAttribute: attr
                };
                if (componentAttribute in this.attributeSlots) {
                    this.attributeSlots[componentAttribute].push(slot);
                } else {
                    this.attributeSlots[componentAttribute] = [slot];
                };
            }
        }

        for (let name of this.getAttributeNames()) {
            this.update(name, "", this.getAttribute(name));
        }

        return content;
    }

    child(id) {
        let children = this.querySelector("#" + id);
        if (children instanceof HTMLCollection) {
            return children.item(children.length - 1);
        } else {
            return children;
        }
    }
}
