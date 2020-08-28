/**
 * @todo
 * uri.param(key, [default])
 * 增加文档和测试。
 */
;(function(){
    /**
     * 通用函数。
     */
    var util = {};
    /**
     * 检查变量是否已绑定对象。
     */
    util.assigned = function(object){
        return (typeof(object) != typeof(undefined) && object != null);
    };
    /**
     * 分发事件。
     */
    util.fireEvent = function(element, eventType){
        var event = document.createEvent('HTMLEvents');
        event.initEvent(eventType, true, true);
        element.dispatchEvent(event);
    };
    /**
     * 增加事件监听器。
     */
    util.on = function(element, event, handler){
        return element.addEventListener(event, handler);
    };
    /**
     * 返回变量的值。如果变量未绑定对象，返回默认值。
     */
    util.value = function(object, defaultValue){
        return (this.assigned(object) ? object : defaultValue);
    };
    /**
     * 判断超时。
     */
    util.timeout = function(startTime, timeoutMilliseconds){
        return (new Date().getTime() - startTime.getTime() >= timeoutMilliseconds);
    };
    /**
     * 如果对象为空，则赋值。
     */
    util.init = function(object, value){
        object = this.value(object, value);
    };

    /**
     * 字符串函数
     */
    var string = {};
    string.trim = function(str){
        return str.replace(/(^\s*)|(\s*$)/g, "");
    };
    string.ltrim = function(str){ //删除左边的空格
        return str.replace(/(^\s*)/g,"");
    };
    string.rtrim = function(str){ //删除右边的空格
        return str.replace(/(\s*$)/g,"");
    };

    /**
     * 名字空间。
     */
    var namespace = {};
    /* 名字空间。 */
    namespace.declare = function(name){
        var ns = "";
        var code = "";
        var pos;
        do {
            pos = name.indexOf(".", pos + 1);
            ns = name.substr(name, pos);
            if (pos == -1){
                ns = name;
            }
            code = "if (typeof(" + ns + ") == 'undefined'){ " + ns + "={}; }";
            eval(code);
        } while (pos != -1);
    };
    namespace.set = function(name){
        /* set(ns) set(ns,"foo", 1) set(ns,"bar", {a:1,b:2}) */
        if (arguments.length <= 1){
            return;
        } 
        var code = "";
        if (arguments.length == 2){
            for (var k in arguments[1]){
                var v = arguments[1][k];
                if (typeof(v) == "function"){
                    v = v.toString();
                } else {
                    v = "'" + v + "'";
                }
                code = [name, ".", k, "=", v, ""].join("");
                eval(code);        
            }
        } else if (arguments.length > 2){
            var k = arguments[1];
            var v = arguments[2];
            code = name + "." + k + "=" + v.toString();
            eval(code);
        }
    };
    namespace.get = function(ns, variable){
        var code = ns + "." + variable;
        return eval(code);
    };
    namespace.remove = function(ns, variableName){
        var code = ["delete ", ns, ".", variableName].join("");
        eval(code);
    };

    /**
     * 模块。
     * 一个模块是一个js文件。
     */
    var module = {
        /** 模块状态 */
        UNLOADED: 0,
        PENDING: 1,
        ERROR: 2,
        LOADED: 3,
    };
    /** 已加载的模块。 */
    module["#modules"] = {};
    /** 正在加载中的任务。 */
    module["#loadings"] = {};
    module["#moduleState"] = function(path){
        var modules = this["#modules"];
        if (path in modules){
            return modules[path].state;
        }
        return this.UNLOADED;
    };
    /**
     * 检查模块是否正在加载中。
     */
    module.isPending = function(path){
        return (this["#moduleState"](path) == this.PENDING);
    };
    /**
     * 检查模块是否已经加载。
     */
    module.isLoaded = function(path){
        return (this["#moduleState"](path) == this.LOADED);
    };
    /**
     * 加载一个模块。如果js文件已经加载，或者正在加载中，不做任何操作。
     * @param path js文件路径。
     * @param options 同#load。
     */
    module.loadOne = function(path, options){
        if (this.isLoaded(path) || this.isPending(path)){
            return;
        }
        var modules = this["#modules"];
        var elem = document.createElement('script');
        elem.src = path;
        if (jtybase.util.assigned(options.handlers)){
            for (var event in options.handlers){
                elem.addEventListener(event, options.handlers[event]);
            }
        }
        modules[path] = { 
            state: this.PENDING,
            path: path, 
            element: elem,
        };
        var loadings = this["#loadings"];
        elem.addEventListener('load', function(){
            modules[path]["state"] = this.LOADED;

            var count = jtybase.util.value(loadings[options.modules], 0) + 1;
            loadings[options.modules] = count;
            var event = jtybase.util.value(options.events['load'], null);
            if (event && count == options.modules.length){
                jtybase.util.fireEvent(window, event);
            }
        });
        elem.addEventListener('error', function(){
            modules[path]["state"] = this.ERROR;
            var event = jtybase.util.value(options.events['error'], null);
            if (event){
                jtybase.util.fireEvent(window, event);
            }
        });
        document.body.appendChild(elem);
    };
    /**
     * 检查模块是否已全部加载。
     * @param modules 模块列表。
     */
    module.isAllLoaded = function(modules){
        for (var m in modules){
            if (!this.isLoaded(m)){
                return false;
            }
        }
        return true;
    };
    /**
     * 加载多个模块。
     * @param options 一个json对象，包括字段modules，handlers，events，timeout，startTime和remain。
     * @todo 加入eventTarget。
     * modules是全部要加载的模块列表，如['a.js', 'b.js']。
     * handlers是k-v对。k是event name。v是handler。
     * events是一个json对象，key包括error、timeout、load，值是当一个模块加载失败/超时/完成时发出的事件的类型。timeout和load只会触发一次。error可能触发多次。
     * timeout是单个模块加载超时时间。
     * remain是还未开始加载的模块列表。
     */
    module["#load"] = function(options){
        if (options.remain.length == 0){
            return;
        }
        var timeout = jtybase.util.value(options.timeout, 0);
        if (timeout > 0 && jtybase.util.timeout(options.startTime, timeout)){
            var event = jtybase.util.value(options.events['timeout'], null);
            if (event){
                jtybase.util.fireEvent(window, event);
            }
            return;
        }
        var path = options.remain.shift();
        this.loadOne(path, options);
        this["#load"](options);
    };
    /**
     * 加载多个模块。
     * @param modules 模块列表。例如['a.js', 'b.js']。
     */
    module.load = function(modules, options){
        // @todo
        options.modules = modules.concat();
        options.remain = modules.concat();
        options.startTime = new Date();
        jtybase.util.init(options.events, {});
        jtybase.util.init(options.timeout, 0);
        this["#load"](options);
    };

    /* init jtybase library. */
    (function(){
        var jtybase = {};
        if (util.assigned(window.jtybase)){
            jtybase = window.jtybase;
        }
        jtybase.module = module;
        jtybase.util = util;
        jtybase.namespace = namespace;
        jtybase.string = string;
        window.jtybase = jtybase;
    })();
})();