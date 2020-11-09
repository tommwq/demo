// TODO 把tr/td等方法放到html对象中。

class Interface {
    constructor(id, type) {
        this.id = id;
        this.type = type;
        this.label = '';
        this.url = '';
        this.service = '';
        this.mfunc = '';
        this.sfunc = '';
        this.description = '';
        this.className = '';
        this.methodName = '';
        this.functionName = '';
        this.inputParameter = new Array();
        this.outputParameter = new Array();
    }
}

class InterfaceParameter {
    constructor(id, interfaceId, name, code, dataType, isRequired, isInput) {
        this.id = id;
        this.interfaceId = interfaceId;
        this.name = name;
        this.code = code;
        this.dataType = dataType;
        this.isRequired = isRequired;
        this.isInput = isInput;
        this.dataDict = {};
    }
}

class IdentifiedObjectManager {
    constructor() {
        this.table = {};
    }

    put(object) {
        this.table[object.id] = object
    }

    get(objectId) {
        return this.table[objectId];
    }

    list() {
        let array = new Array();
        for (let id in this.table) {
            array.push(this.table[id]);
        }
        return array;
    }
}

class IdentifiedObjectManagerRegistry {
    constructor() {
        this._manager_table = {};
    }
    register(name) {
        this._manager_table[name] = new IdentifiedObjectManager();
    }
    get(name) {
        return this._manager_table[name];
    }
}

function div() {
    return document.createElement("div");
}

function createSpan(text) {
    let e = document.createElement("span");
    if (text != null) {
        e.textContent = text;
    }
    return e;
}

function createTable() {
    return document.createElement("table");
}

function tr() {
    return document.createElement("tr");
}

function td(text) {
    let e = document.createElement("td");
    if (text != null) {
        e.textContent = text;
    }
    return e;
}

function th(text) {
    let e = document.createElement("th");
    if (text != null) {
        e.textContent = text;
    }
    return e;
}

function createInput(id) {
    let e = document.createElement("input");
    e.id = id;
    return e;
}

function showDataDictPanel(functionParameterId) {
    let panel = document.getElementById("data-dict-panel");
    panel.innerHTML = '';
    let param = managerRegistry.get(FUNCTION_PARAMETER).get(functionParameterId);
    let tableDict = createTable();
    let head = tr();
    head.appendChild(th("值"));
    head.appendChild(th("说明"));
    tableDict.appendChild(head);
    for (let k in param.dataDict) {
        let row = tr();
        row.appendChild(td(k));
        row.appendChild(td(param.dataDict[k]));
        tableDict.appendChild(row);
    }
    panel.appendChild(tableDict);
}

function appendInterfacePanel(element, lbmFunction) {
    let divLBMPanel = div();

    let tableFunc = createTable();
    tableFunc.className = "lbm-func";
    
    let row = tr();
    row.appendChild(th("LBM 编号"));
    row.appendChild(th(lbmFunction.lbmId));
    tableFunc.appendChild(row);

    row = tr();
    row.appendChild(td("功能"));
    row.appendChild(td(lbmFunction.feature));
    tableFunc.appendChild(row);

    row = tr();
    row.appendChild(td("函数"));
    row.appendChild(td(lbmFunction.functionName));
    tableFunc.appendChild(row);
    
    divLBMPanel.appendChild(tableFunc);

    let divParam = div();
    let tableParam = createTable();
    tableParam.className = "function-parameter";
    row = tr();
    row.appendChild(th("入参"));
    row.appendChild(th());
    row.appendChild(th());
    row.appendChild(th());
    tableParam.appendChild(row);
    row = tr();
    row.appendChild(th("参数"));
    row.appendChild(th("类型"));
    row.appendChild(th("含义"));
    row.appendChild(th("数据字典"));
    tableParam.appendChild(row);
    for (let p of lbmFunction.inputParameter) {
        let row = tr();
        row.appendChild(td(p.code));
        row.appendChild(td(p.dataType));
        row.appendChild(td(p.name));
        let dataDict = td();
        if (Object.keys(p.dataDict).length > 0) {
            let s = createSpan("数据字典");
            s.className = "data-dict";
            s.addEventListener('click', (e) => {
                showDataDictPanel(p.id);
            });
            dataDict.appendChild(s);
        }
        row.appendChild(dataDict);
        tableParam.appendChild(row);
    }

    row = tr();
    row.appendChild(th("出参"));
    row.appendChild(th());
    row.appendChild(th());
    row.appendChild(th());
    tableParam.appendChild(row);

    row = tr();
    row.appendChild(th("参数"));
    row.appendChild(th("类型"));
    row.appendChild(th("含义"));
    row.appendChild(th("数据字典"));
    tableParam.appendChild(row);
    for (let p of lbmFunction.outputParameter) {
        let row = tr();
        row.appendChild(td(p.code));
        row.appendChild(td(p.dataType));
        row.appendChild(td(p.name));
        let dataDict = td();
        if (Object.keys(p.dataDict).length > 0) {
            let s = createSpan("数据字典");
            s.className = "data-dict";
            s.addEventListener('click', (e) => {
                showDataDictPanel(p.id);
            });
            dataDict.appendChild(s);
        }
        row.appendChild(dataDict);
        tableParam.appendChild(row);
    }

    divParam.appendChild(tableParam);
    divLBMPanel.appendChild(divParam);
    
    element.appendChild(divLBMPanel);
}

function element(id) {
    return document.getElementById(id);
}

// 创建<tag>对象
function createElement(id, tag) {
    let element = document.createElement(tag);
    element.id = id;
    return element;
}

// 创建select对象。options = [{value:1,name:'abc'},{}]
function createSelect(id, options) {
    let element = createElement(id, "select");
    for (let op of options) {
        let sub = document.createElement("option");
        sub.value = op.value;
        sub.textContent = op.name;
        element.appendChild(sub);
    }

    if (element.options.length > 0) {
        element.options.item(0).selected = true;
    }
    return element;
}

// 移除全部子元素。
function removeAllChildren(parent) {
    while (parent.firstChild) {
        parent.firstChild.remove();
    }
}
