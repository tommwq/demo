
QUnit.test("util.assigned", function(assert){
    assert.ok(!jtybase.util.assigned(x));
    var x = null;
    assert.ok(!jtybase.util.assigned(x));
    x = 1;
    assert.ok(jtybase.util.assigned(x));
});

QUnit.test("util.fireEvent", function(assert){
    var done = assert.async();
    var eventType = 'foo';
    window.addEventListener(eventType, function(){
        assert.ok(true);
        done();
    });
    jtybase.util.fireEvent(window, eventType);
});

QUnit.test("module.load-1", function(assert){
    var done = assert.async();
    var fail = setTimeout(function(){
        assert.ok(false);
        done();
    }, 5000);
    var options = {
        timeout: 3000,
        handlers: {
        },
        events: {
            'load': 'myload-1',
        },
    };
    jtybase.util.on(window, 'myload-1', function(){
        clearTimeout(fail);
        assert.ok(true);
        done();
    });
    jtybase.module.load(["http://code.jquery.com/jquery-2.1.4.js",
                         "https://raw.githubusercontent.com/julianshapiro/velocity/master/velocity.min.js"],
                        options);
});

QUnit.test("module.load-2", function(assert){
    var done = assert.async();
    var fail = setTimeout(function(){
        assert.ok(false);
        done();
    }, 5000);
    var options = {
        timeout: 3000,
        handlers: {
        },
        events: {
            'load': 'myload-2',
        },
    };
    jtybase.util.on(window, 'myload-2', function(){
        clearTimeout(fail);
        assert.equal(sum(1, 2), 3);
        assert.equal(sub(1, 2), -1);
        done();
    });
    jtybase.module.load(["test/sum.js",
                         "test/substract.js"],
                        options);
});

QUnit.test("module.load-error-1", function(assert){
    var done = assert.async();
    var fail = setTimeout(function(){
        assert.ok(false);
        done();
    }, 3000);
    var options = {
        timeout: 3000,
        handlers: {
        },
        events: {
            'error': 'myerror-1'
        },
    };
    var error = false;
    jtybase.util.on(window, 'myerror-1', function(){
        if (!error){
            clearTimeout(fail);
            assert.ok(true);
            done();
        }
        error = true;
    });
    jtybase.module.load(["test/none.js",
                         "test/noSuchFile.js"],
                        options);
});

