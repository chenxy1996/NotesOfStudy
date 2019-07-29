[TOC]

[原文](http://dmitrysoshnikov.com/ecmascript/javascript-the-core-2nd-edition/)

# Object

==**Def. 1: Object:** An *object* is a *collection of properties*, and has a *single prototype object*. The prototype may be either an object or the `null` value.==

**Note**: `Object.prototype__proto__` is `null`

A prototype of an object is referenced by the internal `[[Prototype]]` property, which to user-level code is exposed via the `__proto__` property.

For the code:

```js
let point = {
    x: 10,
    y: 20,
};
```

we have the structure with two *explicit own properties* and one *implicit* `__proto__`property, which is the reference to the prototype of `point`:

![*Figure 1*](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/js-object.png)

*Figure 1. A basic object with a prototype.*



# Prototype

Every object, when is created, receives its *prototype*. **If the prototype is not set *explicitly*, objects receive *default prototype* as their *inheritance object*.**

==**Def. 2: Prototype:** A *prototype* is a delegation object used to implement *prototype-based inheritance*.==

**Note**: <u>by default objects receive `Object.prototype` as  their inheritance object.</u>

The prototype can be set *explicitly* via either the `__proto__` property, or `Object.create` method:

```js
// Base object.
let point = {
  x: 10,
  y: 20,
};
 
// Inherit from `point` object via `__proto__` property
let point3D = {
  z: 30,
  __proto__: point,
};

console.log(
  point3D.x, // 10, inherited
  point3D.y, // 20, inherited
  point3D.z  // 30, own
);

// Inherit from `point` object via `Object.create` method
//let point3D = Object.create(point);
//point3D.z = 30;

```

Any object can be used as a prototype of another object, and the prototype itself can have its own prototype. If a prototype has a not-null reference to its prototype, and so on, it is called the prototype chain.

==**Def. 3: Prototype chain:** A *prototype chain* is a ***finite*** chain of objects used to implement *inheritance* and *shared properties*.==

![Figure 2](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/prototype-chain.png)

*Figure 2. A prototype chain.*



The rule is very simple: if a property is not found in the object itself, there is an attempt to *resolve* it in the prototype; in the prototype of the prototype, etc. — until the whole prototype chain is considered.

Technically this mechanism is known as ***dynamic dispatch*** or ***delegation***.

==**Def. 4: Delegation:** a mechanism used to resolve a property in the inheritance chain. The process happens at runtime, hence is also called **dynamic dispatch**.==

**Note**: in contrast with *static dispatch* when references are resolved at *compile time*, *dynamic dispatch* resolves the references at *runtime*.

And i<u>f a property eventually is not found in the prototype chain, the `undefined` value is returned:</u>

```js
// An "empty" object.
let empty = {};
 
console.log(
 
  // function, from default prototype
  empty.toString,
   
  // undefined
  empty.x,
 
);
```

As we can see, a default object is actually *never empty* — it always inherits *something* from the `Object.prototype`. ==To create a *prototype-less dictionary*, we have to explicitly set its prototype to `null`:==

```js
// Doesn't inherit from anything.
let dict = Object.create(null);
 
console.log(dict.toString); // undefined

let dict1 = {};
dict1.__proto__ = null;

console.log(dict1.toString); // undefined
```

The *dynamic dispatch* mechanism allows *full mutability* of the inheritance chain, providing an ability to change the delegation object:

```js
let protoA = {x: 10};
let protoB = {x: 20};
 
// Same as `let objectC = {__proto__: protoA};`:
let objectC = Object.create(protoA);
console.log(objectC.x); // 10
 
// Change the delegate:
Object.setPrototypeOf(objectC, protoB);
console.log(objectC.x); // 20
```

**Note**:even though the `__proto__` property is standardized today, and is easier to use for explanations, on practice prefer using API methods for prototype manipulations, such as `Object.create`, `Object.getPrototypeOf`, `Object.setPrototypeOf` , and similar on the `Reflect` module.