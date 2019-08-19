"use strict"

// let Person; // Constructor function to create Person instances

// // Block 
// {   
//     const  privateData = new WeakMap(); 

//     Person = function(name) {
//         if (new.target === Person) {
//             privateData.set(this, {name});
//         } 
//         else {
//             console.log("use new operator to create a new Person instance");
//         }
//     };

//     Person.prototype.getName = function() {
//         return privateData.get(this).name;
//     }
// }

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


function object(obj) {
    function F() {}
    F.prototype = obj;
    return new F();
}

function setPrototype(subObj, supObj) {
    const constructorFunction = subObj.constructor;
    const proto = object(supObj);
    proto.constructor = constructorFunction;
    subObj.constructor.prototype = proto;
}   

function Person(name) {
    this.name = name;
}

Person.prototype.sayName = function() {
    return this.name;
};

Person.create = function(name) {
    return new Person(name);
};

function Student(name, major) {
    Person.call(this, name);
    this.major = major;
}

// Object.setPrototypeOf(Student.prototype, Person.prototype);
setPrototype(Student.prototype, Person.prototype);

// setPrototype(Student, Person);

// Student.__proto__ = Person;
// Object.setPrototypeOf(Student, Person);

const newStudent = new Student("chen", "Computer Science");


// const newStudent1 = Student.create("lele");

const obj1 = {
    name: "chen",
    sayName() {
        return this.name;
    }
}

const obj2 = {
    name: "lele"
}

Object.setPrototypeOf(obj2, obj1);

class Animal {
    constructor(name) {
        this.name = name;
    }
}

const aAnimal = new Animal("lele");
const bAnimal = new aAnimal.constructor("xiaobai");

// console.log(
//     aAnimal instanceof Animal,
//     bAnimal instanceof Animal
// )

class MyClass {
    static get [Symbol.species]() {
        return this;
    }

    constructor(value) {
        this.value = value;
    }

    clone() {
        // return new this.constructor[Symbol.species](this.value);
        return new this.constructor[Symbol.species](this.value);
    }
}

class MyDerivedClass1 extends MyClass {
    // empty
}

class MyDerivedClass2 extends MyClass {
    static get [Symbol.species]() {
        return MyClass;
    }
}

let instance1 = new MyDerivedClass1("foo"),
    clone1 = instance1.clone(),
    instance2 = new MyDerivedClass2("bar"),
    clone2 = instance2.clone();

console.log(clone1 instanceof MyClass);             // true
console.log(clone1 instanceof MyDerivedClass1);     // true
console.log(clone2 instanceof MyClass);             // true
console.log(clone2 instanceof MyDerivedClass2); 