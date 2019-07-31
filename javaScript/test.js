// var stu = {
//     major: "computer science",
//     __proto__: person,
// }

function object(o) {
    function F(){};
    F.prototype = o;
    return new F();
}

function inheritFrom(subType, supType) {
    var prototype = object(supType.prototype); // 创建supType.protoType的一个副本
    subType.prototype = prototype;
    subType.prototype.constructor = subType;
}

function Person(name, age) {
    this.name = name;
    this.age = age;
}

Person.prototype = {
    sayName() {
        return this.name;
    },

    sayAge() {
        return this.age;
    },

    constructor: Person,
}

function Student(name, age, major) {
    Person.call(this, name, age);
    this.major = major;
}

inheritFrom(Student, Person);

Student.prototype.sayMajor = function() {
    return this.major;
}

var aStudent = new Student("chen", 23, "computer science");

class Letter {
    constructor(number) {
        this.number = number;
    }

    getNumber() {
        return this.number;
    }
}

var obj = {
    name: "chen",
};

function NewConstrctor(initialNum) {
    this.x = initialNum;
}

NewConstrctor.prototype.recursiveIncrement = function f() {
    if (this.x < 10) {
        this.x = this.x + 1;
        // var newF = f.bind(this);
        // newF();
        f.call(this);
    }
};

// NewConstrctor.prototype.recursiveIncrement = function () {
//     if (this.x < 10) {
//         this.x = this.x + 1;
//         this.recursiveIncrement();
//     }
// };

function outer() {
    let x = 3;
    function inner() {
        x++;
        console.log(x);
    }
    return inner;
}

outer()()




