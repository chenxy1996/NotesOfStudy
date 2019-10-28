class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }

    sayAge() {
        return this.age;
    }

    getAge() {
        let fun = () => {
            console.log("this: ", this);
            return this.age;
        };

        return fun();
    }
}

// let aPerson = new Person("chen", 22);
// console.log(aPerson.getAge())

// const obj = {age: 27};
// obj.sayAge = aPerson.getAge;
// console.log(obj.sayAge === aPerson.getAge);
// console.log(obj.sayAge());

const obj1 = {
    name: "chen",
    sayName: function() {
        const fun = () => this.name;
        return fun;
    }
}

obj2 = {
    name: "lele"
};

obj2.fun = obj1.sayName()

console.log(obj2.fun())
