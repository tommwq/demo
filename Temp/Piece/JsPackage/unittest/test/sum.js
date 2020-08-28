(function(){
    window.sum = function(){
        var result = 0;
        for (var i in arguments){
            result += arguments[i];
        };
        return result;
    };
})();