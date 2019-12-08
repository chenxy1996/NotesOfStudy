let Student = function(name, age) {
    this.name = name;
    this.age = age;
};

Student.prototype.sayName = function() {
    return this.name;
};

let Teacher = function(name) {
    this.name = name;
}

Teacher.prototype.sayName = function() {
    return this.name;
};

function getObjName() {
    return this.name;
}

let aStudent = new Student("chen", 23);
let aTeacher = new Teacher("lele");

getObjNameNew = getObjName.bind(aStudent);

console.log(getObjName.call(aTeacher));