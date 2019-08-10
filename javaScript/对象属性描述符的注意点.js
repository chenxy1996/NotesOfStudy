// 如果原型某个属性描述符对象的 writable 被设置为 false
// 那么原型后代的同名属性都不能被设置
// 严格模式下会报错，非严格模式则会被忽略

let supType = Object.defineProperty({}, "name", {
    value: "chen"
})

let subType = {};

Object.setPrototypeOf(subType, supType);

subType.name = "lele";

console.log(subType.name); // chen