function object(o) {
    function F() {};
    F.prototype = o;
    return new F();
}

// Person Class
function Person(name, age) {
    this.name = name;
    this.age = age;
}

Person.prototype.getName = function() {
    return this.name;
};

Person.prototype.getAge = function() {
    return this.age;
};

//Student Class
function Student(name, age, major) {
    Person.call(this, name, age);
    this.major = major;
}

Student.prototype = object(Person.prototype);
Student.prototype.constructor = Student;
Student.prototype.getMajor = function() {
    return this.major;
}

const aStudent = new Student("chen", 23, "cs");

console.log(
    aStudent.getName(),
    aStudent.constructor,
    aStudent.getMajor()
)

const aPerson = new Person("lele", 10);
console.log(
    aPerson.getName(),
    aPerson.constructor
)