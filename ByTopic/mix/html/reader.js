class Reader {
    constructor(objects) {
        this.position = 0;
        this.objects = objects;
    }

    isEnd() {
        return this.position >= this.objects.length;
    }

    rewind() {
        this.position =0;
    }

    get() {
        if (this.isEnd()) {
            throw "out of range";
        }
        
        let obj = this.objects[this.position];
        this.position++;
        return obj;
    }

    unget() {
        if (this.position <= 0) {
            throw "out of range";
        } 
        this.position--;
    }
}

export {
    Reader
};
