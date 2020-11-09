import { StateMachine } from "./statemachine.js";
import { assert } from "../test/test.js";

let machine = new StateMachine("a");
machine.addState("b");
machine.addState("c");
machine.addState("d", true);

function printTransfer(from, input, to) {
    // console.log(`${from.name}(${input}) => ${to.name}`);
}

machine.addTransfer("a", "1", "b", printTransfer);
machine.addTransfer("a", "2", "c", printTransfer);
machine.addTransfer("b", "1", "a", printTransfer);
machine.addTransfer("b", "2", "d", printTransfer);
machine.addTransfer("c", "1", "b", printTransfer);
machine.addTransfer("c", "2", "d", printTransfer);

["1", "1", "2", "1", "1", "1", "2"].forEach((x) => {
    machine.input(x);
});

