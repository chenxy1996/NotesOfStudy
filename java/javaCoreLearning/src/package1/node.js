class Student {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    sayName() {
        return this.name;
    }
}

const aStudent = new Student("chenxiangyu", 23);
console.log(aStudent.sayName());
