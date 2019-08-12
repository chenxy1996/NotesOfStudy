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

// mimic iterator before ES6
function createIterator(items) {
    var i = 0;

    return {
        next: function() {
            
            var done = (i >= items.length);
            var value = !done ? items[i++] : undefined;

            return {
                done: done,
                value: value
            };
        }
    }
}

var iterator = createIterator([1, 2, 3]);

function Person() {
    return name;
}