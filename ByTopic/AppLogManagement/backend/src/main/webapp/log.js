function ascend(array, field) {
    return array.sort((lhs, rhs) => lhs[field] - rhs[field]);
}

function descend(array, field) {
    return ascend(array, field).reverse();
}

// 根据指定域去重。
function unique(array, field) {
    let table = {}
    for (let index in array) {
        let element = array[index];
        let key = element[field];
        table[key] = element;
    }
    
    let newArray = [];
    for (let key in table) {
        newArray.push(table[key]);
    }

    return newArray;
}

// 解析URL中的参数部分，返回参数字典。
function getUrlParameters() {
    let result = {};
    
    let url = window.location.href;
    let index = url.indexOf("?");
    if (index == -1) {
        return result;
    }

    let params = url.substring(index + 1).split("&");
    
    for (let item in params) {
        let pair = params[item].split("=");
        if (pair.length == 0) {
            continue;
        }

        let key = pair[0];
        let value = "";
        if (pair.length >= 1) {
            value = pair[1];
        }

        result[key] = value;
    }
    
    return result;
}

var app = new Vue({
    el: "#app",
    data: {
        logList: [],
        deviceId: ""
    },
    methods: {
        request: function() {
            this.deviceId = getUrlParameters()["deviceId"];

            axios.get("/api/log/" + this.deviceId)
                .then(payload => app.update(payload.data))
                .catch(error => console.log(error));
        },
        update: function(data) {
            this.logList = descend(unique(data, "sequence"), "sequence");
        }
    }
});

app.request();



