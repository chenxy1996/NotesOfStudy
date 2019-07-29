function Student(name, age) {
    var o = {};

    o.sayName = function() {
        console.log(name);
    }

    o.sayAge = function() {
        console.log(age);
    }

    return o;
}

var aStudent = Student("chen", 23);
var fun1 = aStudent.sayName;
fun1();