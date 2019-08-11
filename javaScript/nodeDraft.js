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

let aPerson = new Person("chen");
let bPerson = new Person("lele");

console.log(
    aPerson.getName(),
    bPerson.getName()
)

aPerson = null;
bPerson = null;

console.log(
    aPerson.getName(),
    bPerson.getName()
)