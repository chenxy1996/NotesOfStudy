class Person {
    constructor(name) {
        this.name = name;
    }

    sayName() {
        return "class: " + this.constructor.name + "; name: " + this.name;
    }
}

// let aPerson = new Person("chen");
// console.log(aPerson.sayName())
class Student extends Person {
    constructor(name, grade) {
        super(name);
        this.grade = grade;
    }

    sayName() {
        return "Parent class: " + Object.getPrototypeOf(Object.getPrototypeOf(this)).constructor.name + '\n'
                + super.sayName();
    }
}

let aPerson = new Student("chen", 1);
console.log(aPerson.sayName())