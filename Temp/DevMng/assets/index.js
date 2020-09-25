
function updateLBMTableMapping(lbmTableMappings) {
    let mapping = {};
    for (let x of lbmTableMappings) {
        if (x.LBM in mapping) {
            mapping[x.LBM].push(x.TableName);
        } else {
            mapping[x.LBM] = [x.TableName];
        }
    }
    
    let lbmSelect = document.getElementById("lbm-select");
    let tableUl = document.getElementById("table-ul");

    lbmSelect.innerHTML = "";
    tableUl.innerHTML = "";

    lbmSelect.addEventListener('change', (e) => {
        let lbm = lbmSelect.selectedOptions[0].value;
        let tables = mapping[lbm];

        tableUl.innerHTML = "";
        for (let table of tables) {
            let li = document.createElement("li");
            li.innerHTML = `<a target=_blank href="http://172.24.141.35/券商综合业务支持系统(KBSS)中登.pdm.html#LOCAL_1-${table}">${table}</a>`;
            tableUl.append(li);
        }
    });

    for (let lbm in mapping) {
        let op = document.createElement("option");
        op.textContent = lbm;
        op.value = lbm;
        lbmSelect.appendChild(op);
    }
}

function listLBMTableMapping() {
    axios.get('/api/LBMTableMapping')
        .then(response => {
            let data = response.data;
            if (data.error != "") {
                console.log(data.err);
                return;
            }

            updateLBMTableMapping(data.payload);
        }).catch(error => {
            console.log(error);
        });
}


listLBMTableMapping()
