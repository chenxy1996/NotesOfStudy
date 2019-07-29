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

