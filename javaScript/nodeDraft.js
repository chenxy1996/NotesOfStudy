"use strict"
const log = console.log.bind(console);


const items = Array.of(1, 2);

let helper = {
    diff: 1,

    add() {
        return (value) => this.diff + value
    }
};

function translate() {
    return Array.from(arguments, helper.add());
}

const nums = [25, 30, 35, 40, 45];

// log(nums.find((undefined, index) => index === 3));

const newBuffer = new ArrayBuffer(10);

const newView = new DataView(newBuffer);




