// 状态
class State {
    constructor(name, isTerminal) {
        this.name = name;
        this._transferTable = {};
        this._transferCallback = {};
        
        if (isTerminal) {
            this._transferTable = null;
        }
    }

    // 是否终止状态
    isTerminal() {
        return this._transferTable == null;
    }

    // 添加状态转移
    addTransfer(input, newState, onTransferCallback) {
        if (this.isTerminal()) {
            return;
        }

        this._transferTable[input] = newState;
        this._transferCallback[input] = onTransferCallback;
    }

    // 转移状态
    input(value) {
        if (this.isTerminal()) {
            return this;
        }
        
        if (!(value in this._transferTable)) {
            return this;
        }
        
        let toState = this._transferTable[value];
        if (value in this._transferCallback) {
            let callback = this._transferCallback[value];
            if (callback != null) {
                callback(this, value, toState);
            }
        }
        return toState;
    }
}

// 状态机
class StateMachine {
    constructor(initialStateName) {
        this.states = {};        
        this.addState(initialStateName);
        this._currentState = this._getState(initialStateName);
    }

    _getState(stateName) {
        if (stateName in this.states) {
            return this.states[stateName];
        }
        throw `unknown state ${stateName}`;
    }

    isTerminated() {
        return this._currentState.isTerminal();
    }

    addState(stateName, isTerminalState) {
        this.states[stateName] = new State(stateName, isTerminalState);
    }

    addTransfer(fromStateName, input, toStateName, onTransferCallback) {
        let from = this._getState(fromStateName);
        let to = this._getState(toStateName);
        from.addTransfer(input, to, onTransferCallback);
    }

    input(value) {
        this._currentState = this._currentState.input(value);
    }
}

export {
    StateMachine
};
