(function(){
    window.sub = function(){
        var result = 0;
        if (arguments.length == 0){
            return 0;
        }
        result = arguments[0];
        if (arguments.length == 1){
            return result;
        }
        for (var i = 1; i < arguments.length; ++i){
            result -= arguments[i];
        }
        return result;
    };
})();