class Student {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    sayName() {
        return this.name;
    }

    sayAge() {
        return this.age;
    }
}

function add(a, b) {
    return a + b;
}

console.log(add(1, 3))

const aStudent = new Student("chen", 23);
console.log(aStudent.sayAge())
