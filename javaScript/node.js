function defaultParam(a=1, b={name: "chen", age: 22}, c=[1, 2, 3]) {
    console.log(
        a,
        b,
        c,
    );

    b.age += 1;
    c.push(5);

    console.log(
        a,
        b,
        c,
    );
}

function mixArgs(first, second = "b") {
    console.log(arguments.length);
    console.log(first === arguments[0]);
    console.log(second === arguments[1]);
    first = "c";
    second = "d";
    console.log(first === arguments[0]);
    console.log(second === arguments[1]);
}


function pick(object, ...keys) {
    console.log(
        arguments.length,
        keys.length
    );
}

let newPerson = (function(name) {
    return {
        sayName: () => name
    }
})("chen");

let  propertyName = "chen ";

let thisPerson = {
    [propertyName + "name"]: "chen",
    [propertyName + "age"]: 10
};

function F(name, age) {
    this.age = age;
    this.name = name;

    this.sayName = function() {
        return this.name;
    }
}

F.prototype.sayAge = function(){
    return this.age;
}

let aF = new F("chen", 23);

// console.log(Object.getOwnPropertyNames(aF));
// console.log(Object.keys(aF));
// console.log(aF.sayAge())

function mixin(receiver, supplier) {
    Object.keys(supplier).forEach(
        (key) => {
            if (!receiver.hasOwnProperty(key)) {
                receiver[key] = supplier[key];
            }
        }
    )

    return receiver;
}

let a = {
    "name": "chen",
}

let b = {
    "name": "lele",
    age: 23
}

let supplier = {
    _name: "chen",

    get name() {
        return this._name;
    },

    set name(newName) {
        this._name = newName;
    }
};

let newObj = {};

Object.defineProperties(newObj, {
    name: {
        value: "chen",
        enumerable: true,
        configurable: true,
        writable: true
    },

    _age: {
        value: "10",
        enumerable: false,
        configurable: true,
        writable: true
    },

    age: {
        get: function() {
            this._age += 1;
            return this._age;
        },

        set: function(newAge) {
            this._age = newAge; 
        },

        enumerable: true
    }
})

function object(o=null) {
    function F() {};
    F.prototype = null;
    console.log(o);
    let returnObj = new F();
    console.log(returnObj.__proto__);
    return returnObj;
}

function F() {
};

let prototypeObj = {
    name: "SuperType",

    sayName() {
        return this.name;
    }
}

let subtypeObj = {
    name: "SubType",

    sayName() {
        return Object.getPrototypeOf(this).sayName() + "//" + this.name;
    }
}

let subtypeObj1 = {
    name: "SubType",

    sayName() {
        return super.sayName() + "//" + this.name;
    }
}

Object.setPrototypeOf(subtypeObj, prototypeObj);
Object.setPrototypeOf(subtypeObj1, prototypeObj);

let person = {
    getGreeting() {
        return "Hello";
    }
};

// prototype is person
let friend = {
    getGreeting() {
        let that = this === friend ? this : friend;
        return Object.getPrototypeOf(that).getGreeting.call(that) + ", hi!";
    }
};

Object.setPrototypeOf(friend, person);
let relative = Object.create(friend);

let node = {
    type: "Identifier",
    name: "foo",
    loc: {
        start: {
            line: 1,
            column: 1
        },
        end: {
            line: 1,
            column: 4
        }
    }
};

let { loc: {start: {line: newLine}}} = node;
console.log(newLine);



