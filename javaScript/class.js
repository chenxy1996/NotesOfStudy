class Person {
    constructor(name) {
        this.name = name;
    }

    sayName() {
        return this.name;
    }
}

class Student extends Person {
    constructor(name, age) {
        super();
        this.name = name;
        this.age = age;
        // super(name);
    }
}


// console.log(
//     Object.getPrototypeOf(Student.prototype) === Person.prototype
// )

function SupType() {
}

SupType.prototype.sayHi = function () {
    return "Hi";
}

function SubType() {
}

SubType.prototype = SupType.prototype;

const newSubType = new SubType();

console.log(newSubType.sayHi())