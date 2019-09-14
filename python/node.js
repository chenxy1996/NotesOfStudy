const obj = {
    name: "chen",
    age: 23,

    sayName() {
        return this.name;
    },

    sayAge() {
        return this.age;
    }
}

console.log(obj.sayName())