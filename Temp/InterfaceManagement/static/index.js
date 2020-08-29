var managerRegistry = new IdentifiedObjectManagerRegistry();

const LBM_FUNCTION = 'LBM_FUNCTION';
const FUNCTION_PARAMETER = 'FUNCTION_PARAMETER';
const INTERFACE_TYPE = 'INTERFACE_TYPE';
const INTERFACE_TABLE = 'INTERFACE_TABLE';

managerRegistry.register(LBM_FUNCTION);
managerRegistry.register(FUNCTION_PARAMETER);
managerRegistry.register(INTERFACE_TYPE);
managerRegistry.register(INTERFACE_TABLE);

function displayInterfaceList(lbmFunctionMananger) {
    let main = document.getElementById("main");
    main.innerHTML = '';
    for (let f of managerRegistry.get(LBM_FUNCTION).list()) {
        appendInterfacePanel(main, f);
    }
}


function showInterfaceInput(parent, interface_type_id) {
    removeAllChildren(parent);

    let table = createTable();
    let type = managerRegistry.get(INTERFACE_TABLE).get(interface_type_id);
    for (let field of type.fields) {
        let row = tr();
        let cell1 = td();
        let cell2 = td();
        cell1.appendChild(createSpan(field));
        cell2.appendChild(createInput('dialog-interface-' + field));
        row.appendChild(cell1);
        row.appendChild(cell2);
        table.appendChild(row);
    }

    parent.appendChild(table);
}

function showDialog() {
    let dialogInterfaceType = element('dialog-interface-type');
    removeAllChildren(dialogInterfaceType);
    dialogInterfaceType.appendChild(createSpan('接口类型'));
    let selectElement = createSelect('dialog-interface-type-select', managerRegistry.get(INTERFACE_TYPE)
                                     .list()
                                     .map((item) => {
                                         return {
                                             name: item.table_name,
                                             value: item.id,
                                         }
                                     }));
    dialogInterfaceType.appendChild(selectElement);

    let dialogInterface = element('dialog-interface');
    if (selectElement.options.length > 0) {
        showInterfaceInput(dialogInterface, selectElement.options[0].value);
    }
    
    selectElement.addEventListener('change', (e) => {
        showInterfaceInput(dialogInterface, selectElement.options[selectElement.selectedIndex].value);
    });
    
    let dialogParameter = document.getElementById('dialog-parameter');
    removeAllChildren(dialogParameter);
    addParameterPanel(dialogParameter);
    
    dialog.showModal();
}

function addParameterPanel(element) {

    let parameterAttributes = [
        {name: 'name', title: "参数代码"},
        {name: 'type', title: "数据类型"},
        {name: 'desc', title: "参数含义"},
        {name: 'optional', title: "是否可选"},
        {name: 'required', title: "是否入参"}
    ];
    
    if (!element.firstChild) {
        let row = tr();
        for (let attr of parameterAttributes) {
            row.appendChild(th(attr.title));
        }
        element.appendChild(row);
    }

    let row = tr();
    for (let attr of parameterAttributes) {
        let cell = td();
        cell.appendChild(createInput('dialog-parameter-' + attr.name));
        row.appendChild(cell);
    }
    element.appendChild(row);


}

function postInterface() {
    let dialog = element('dialog');
    
    let dialogInterfaceType = element('dialog-interface-type-select');
    let type = dialogInterfaceType.options[dialogInterfaceType.selectedIndex].value;
    let fields = {};

    let inputList;
    inputList = dialog.getElementsByTagName("input");
    for (let input of inputList) {
        if (!input.id.startsWith("dialog-interface-")) {
            continue;
        }
        
        let key = input.id.substring(17);
        let value = input.value;
        fields[key] = value;
    }

    let parameters = [];
    let param = {};
    inputList = dialog.getElementsByTagName("input");
    for (let input of inputList) {
        if (!input.id.startsWith("dialog-parameter-")) {
            continue;
        }

        let key = input.id.substring(17);
        let value = input.value;
        param[key] = value;
        if (key == 'required') {
            let isEmpty = true;
            for (let k in param) {
                if (param[k] != '') {
                    isEmpty = false;
                }
            }
            
            if (!isEmpty) {
                parameters.push(param);
            }
            param = {};
        }
    }
    
    let interfaceData = {
        'type': type,
        'fields': fields,
        'parameters': parameters,
    };
    
    axios.post('/interface_main', interfaceData)
        .then((response) => {
            location.reload(true);
        });
}

function loadInterface(onLoad) {
    axios.get('/interface_main')
        .then(function (response) {
            onLoad(response);
        });
}

function loadInterfaceType(onLoad) {
    axios.get('/interface_type')
        .then(function (response) {
            onLoad(response);
        });
}

function loadInterfaceTable(onLoad) {
    axios.get('/interface_table')
        .then(function (response) {
            onLoad(response);
        });
}

function initializeEventListener() {
    document.getElementById("add").addEventListener("click", (e) => {
        showDialog();
    });
    let dialog = document.getElementById('dialog');
    document.getElementById("dialog-confirm").addEventListener("click", (e) => {
        postInterface();
        dialog.close();
    });
    document.getElementById("dialog-cancel").addEventListener("click", (e) => {
        dialog.close();
    });
    document.getElementById("dialog-add-param").addEventListener("click", (e) => {
        let dialogParameter = document.getElementById('dialog-parameter');
        addParameterPanel(dialogParameter);
    });
}

initializeEventListener();
loadInterfaceType((response) => {
    for (let item of response.data) {
        managerRegistry.get(INTERFACE_TYPE).put(item);
    }

    loadInterfaceTable((response) => {
        for (let item of response.data) {
            managerRegistry.get(INTERFACE_TABLE).put(item);
        }
    });
    
    loadInterface((response) => {
        // TODO 显示接口数据
        console.log(response.data);
    });
});

