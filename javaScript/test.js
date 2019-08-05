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

function createFunctions() {
    var result = new Array();

    for (let i = 0; i < 10; i++) {
        result[i] = function() {
            console.log(i);
        };
    }

    return result;
}



function sayNewVariable() {
    console.log(newVariable);
}

var SomeConstructor;

// {
//     let privateScope = {};

//     SomeConstructor = function SomeConstructor () {
//         this.someProperty = "foo";
//         privateScope.hiddenProperty = "bar";
//     }

//     SomeConstructor.prototype.showPublic = function () {
//         console.log(this.someProperty); // foo
//     }

//     SomeConstructor.prototype.showPrivate = function () {
//         console.log(privateScope.hiddenProperty); // bar
//     }

// }

(function() {
    var privateScope = {};

    SomeConstructor = function SomeConstructor () {
        this.someProperty = "foo";
        privateScope.hiddenProperty = "bar";
    }

    SomeConstructor.prototype.showPublic = function () {
        console.log(this.someProperty); // foo
    }

    SomeConstructor.prototype.showPrivate = function () {
        console.log(privateScope.hiddenProperty); // bar
    }

})();

var newConstructor = new SomeConstructor();

newConstructor.showPublic();
newConstructor.showPrivate();

console.log(1 + 3);





