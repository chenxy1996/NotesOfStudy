'use strict'

var log = console.log.bind(console);

var obj = {
    name: "chenxiangyu",
    age: 23
};

// log(obj);

// [[configurable]] 默认设置为 true, 属性 name 可以被删除
delete obj.name;

obj.name = "chenxiangyu";

// [[configurable]] 设置为 false, 属性 name 不能被删除
// Object.defineProperty(obj, "name", {
//     configurable: false
// })

// 严格模式下会导致错误
//delete obj.name;
// log(obj);

// 一旦把属性变为不可配置后，除了可以修改 writable 以及 value 外， 其余都会导致错误（严格模式下）
// Object.defineProperty(obj, "name", {
//     value: "lele",
//     // enumerable: false
// })

// log(obj);

// var descriptor = Object.getOwnPropertyDescriptor(obj, "name");
// log(descriptor);


// 访问器属性
// log(obj);
Object.defineProperty(obj, "newAge", {
    get: function() {
        return this.age;
    },

    set: function(newAge) {
        this.age = newAge;
    },

    // enumerable: true
})

// log(obj);
log(Object.getOwnPropertyDescriptor(obj, "newAge"));