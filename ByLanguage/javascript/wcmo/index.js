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
    static observerCallback(mutations, observer) {
        for (let mutation of mutations) {
            if (mutation.target instanceof Component && mutation.type == "attributes") {
                mutation.target.update(mutation.attributeName);
            }
        }
    }

    static define(componentClass) {
        customElements.define(Text.pascalToKebab(componentClass.name), componentClass);
    }
    
    constructor() {
        super();

        this.attributeSlots = {}; // componentAttribute: {element: X, elementAttribute: Y}

        let observer = new MutationObserver(Component.observerCallback);
        observer.observe(this, {
            attributes: true,
            childList: false,
            subtree: false
        });
        this.observer = observer;        
    }

    update(attributeName) {
        if (attributeName in this.attributeSlots) {
            for (let slot of this.attributeSlots[attributeName]) {
                slot.element[slot.elementAttribute] = this.getAttribute(attributeName);
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

    createContent() {
        let template = document.getElementById(this.componentTemplateId());
        let content= template.content.cloneNode(true);
        let re = /<!--#([a-zA-Z0-9]*)-->/g;
        
        for (let c of content.children) {
            let attrs = c.getAttributeNames();
            attrs.push("innerHTML");
            for (let attr of attrs) {
                if (attr == "class") {
                    attr = "className";
                }
                
                let result = re.exec(c[attr]);
                console.log(attr, c[attr]);
                if (result == null) {
                    continue;
                }
                let componentAttribute = result[1];
                let slot = {
                    element: c,
                    elementAttribute: attr
                };
                console.log(slot, componentAttribute);
                if (componentAttribute in this.attributeSlots) {
                    this.attributeSlots[componentAttribute].push(slot);
                } else {
                    this.attributeSlots[componentAttribute] = [slot];
                };
            }
        }

        // for (let c of content.children) {
        //     let html = c.innerHTML;
        //     if (!html.startsWith("<!--#") || !html.endsWith("-->")) {
        //         continue;
        //     }
        //     let attribute = html.substring(5, html.length - 3);
        //     let slot = {
        //         element: c,
        //         elementAttribute: "textContent"
        //     };
        
        //     if (attribute in this.attributeSlots) {
        //         this.attributeSlots[attribute].push(slot);
        //     } else {
        //         this.attributeSlots[attribute] = [slot];
        //     }
        // }
        
        for (let name of this.getAttributeNames()) {
            this.update(name);
        }

        return content;
    }

    child(id) {
        return this.children.namedItem(id);
    }

    release() {
        this.observer.disconnect();
    }
}


class InputCard extends Component {
    
    constructor() {
        super();
        this.appendChild(this.createContent());
        saveButton.addEventListener('click', () => this.setAttribute("text", input.value));
    }

    update(attributeName) {
        super.update(attributeName);
    }
}


window.onload = () => {
    Component.define(InputCard);
};
