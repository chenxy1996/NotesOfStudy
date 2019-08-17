"use strict"

let Person; // Constructor function to create Person instances

// Block 
{   
    const  privateData = new WeakMap(); 

    Person = function(name) {
        if (new.target === Person) {
            privateData.set(this, {name});
        } 
        else {
            console.log("use new operator to create a new Person instance");
        }
    };

    Person.prototype.getName = function() {
        return privateData.get(this).name;
    }
}

// mimicking iterator before ES6
// function createIterator(items) {
//     var i = 0;

//     return {
//         next: function() {
            
//             var done = (i >= items.length);
//             var value = !done ? items[i++] : undefined;

//             return {
//                 done: done,
//                 value: value
//             };
//         }
//     }
// }

// var iterator = createIterator([1, 2, 3]);

let aString = "chen";

let aArray = new Array();

aArray.push(1);
aArray.push(2)

// for (let key of aArray.keys()) {
//     console.log(key);
// }

function *createIterator() {
    let first = yield 1;
    let second;

    try {
        second = yield first + 2;
    } catch (ex) {
        second = 6;
    }

    yield second + 3;
}

let newIterator = createIterator();

function *createIterator1() {
    yield 1;
    yield 2;
    yield 3;
}

let newIterator1 = createIterator1();

function *iterate(nums) {
    yield * nums;
}

let a = iterate([1, 2, 3]);

let testArray = [[1, 2, 3], [4, 5, 6]];

testArray.forEach(
    (value) => setTimeout(() => console.log(value), 0)
);
console.log("hehe");
