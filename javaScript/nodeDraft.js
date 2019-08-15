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

function *createIterator(...itmes) {
    for (let i = 0; i < itmes.length; i++) {
        console.log("inner" + i);
        yield itmes[i];
    }
}

let newIterator = createIterator(1, 2, 3, 4);

console.log(newIterator.next());

// for (let eachVaule of newIterator) {
//     console.log(eachVaule);
// }