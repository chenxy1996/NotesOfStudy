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

console.log(
    aStudent.sayName(),
    aStudent.sayAge(),
    aStudent.sayMajor(),
    aStudent.constructor
)

