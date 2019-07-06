[toc]
In most cases, the value of this is determined by <u>**how a function is called**.</u> <u>It can't be set by assignment during execution.</u>

# Syntax
## Value
A property of an **execution context** (<u>global, function or eval</u>) that, <u>in not-strict mode, is always a reference to an object and in strict mode can be any value.</u>

---

# Global context
In the global execution context (**outside of any function**), this refers to **==the global object==** whether in strict mode or not.

```js
// In web browsers, the window object is also the global object:
console.log(this === window); // true

a = 37;
console.log(window.a); // 37

this.b = "MDN";
console.log(window.b)  // "MDN"
console.log(b)         // "MDN"
```

==Note==: You can always easily get the global object using the **globalThis** operator, regardless of the current context in which your code is running.

---

# Function context
**Inside a funtion**, the value of this depends on ==**how the function is called**==.

## Simple call
Since the following code is not in strict mode, and because the value of this is not set by the call, this will default to the global object, which is window in a browser.

```js
function f1() {
    return this;
}

// In a browser:
f1() === window; // true

// In node:
f1() === global; // true
```

In strict mode, howerver, if the value of this is not set when entering an exevcution context, it remains as undefined, as shown in the following example:

```js
unction f2() {
  'use strict'; // see strict mode
  return this;
}

f2() === undefined; // true
```

==<u>In the second example, this should be undefined, because f2 was called directly and not as a method or property of an object(e.g. window.f2()).</u>==

To set the value of this to particular value when calling a function, use call(), or apply() as in the following examples.

### Example 1
```js
// An object can be passed as the first argument to call or apply and this will be bound to it.
var obj = {a: 'Custom'};

// This property is set on the global object
var a = 'Global';

function whatsThis() {
  return this.a;  // The value of this is dependent on how the function is called
}

whatsThis();          // 'Global'
whatsThis.call(obj);  // 'Custom'
whatsThis.apply(obj); // 'Custom'
```
### Example 2
```js
function add(c, d) {
  return this.a + this.b + c + d;
}

var o = {a: 1, b: 3};

// The first parameter is the object to use as
// 'this', subsequent parameters are passed as 
// arguments in the function call
add.call(o, 5, 7); // 16

// The first parameter is the object to use as
// 'this', the second is an array whose
// members are used as the arguments in the function call
add.apply(o, [10, 20]); // 34
```

Note that in non–strict mode, with call and apply, <u>if the value passed as **this is not an object,** an attempt will be made to convert it to an object using the internal ToObject operation.</u> So if the value passed is a primitive like 7 or 'foo', it will be converted to an Object using the related constructor, so the primitive number 7 is converted to an object as if by new Number(7) and the string 'foo' to an object as if by new String('foo'), e.g.

```js
function bar() {
  console.log(Object.prototype.toString.call(this));
}

bar.call(7);     // [object Number]
bar.call('foo'); // [object String]
```

## The bind method
ECMAScript 5 introduced Function.prototype.bind(). Calling f.bind(someObject) creates a new function with the same body and scope as f, but where this occurs in the original function, <u>in the new function it is permanently **bound to the first argument of bind**,</u> regardless of how the function is being used.

```js
function f() {
  return this.a;
}

var g = f.bind({a: 'azerty'});
console.log(g()); // azerty

var h = g.bind({a: 'yoo'}); // bind only works once!
console.log(h()); // azerty

var o = {a: 37, f: f, g: g, h: h};
console.log(o.a, o.f(), o.g(), o.h()); // 37,37, azerty, azerty
```

## Arrow functions
In arrow functions, this retains the value of the enclosing lexical context's this. In global code, it will be set to the global object.

```js
var globalObject = this;
var foo = (() => this);
console.log(foo() === globalObject); // true
```

==Note==: <u>if this arg is passed to call, bind, or apply on invocation of an arrow function it will be **ignored.**</u> You can still prepend arguments to this call, but the first argument(thisArg) should be set to null.

```js
// Call as a method of an object
var obj = {func: foo};
console.log(obj.func() === globalObject); // true

// Attempt to set this using call
console.log(foo.call(obj) === globalObject); // true

// Attempt to set this using bind
foo = foo.bind(obj);
console.log(foo() === globalObject); // true
```

No matter what, <u>foo's this is set to what it was when it was created (in the example above, the global object).</u> The same applies to arrow functions created inside other functions: their this remains that of the enclosing lexical context.

```js
// Create obj with a method bar that returns a function that
// returns its this. The returned function is created as 
// an arrow function, so its this is permanently bound to the
// this of its enclosing function. The value of bar can be set
// in the call, which in turn sets the value of the 
// returned function.
var obj = {
  bar: function() {
    var x = (() => this);
    return x;
  }
};

// Call bar as a method of obj, setting its this to obj
// Assign a reference to the returned function to fn

var fn = obj.bar();

// Call fn without setting this, would normally default
// to the global object or undefined in strict mode

console.log(fn() === obj); // true

// But caution if you reference the method of obj without calling it

var fn2 = obj.bar;

// Calling the arrow function's this from inside the bar method()
// will now return window, because it follows the this from fn2.(window.fn2)

console.log(fn2()() == window); // true
```
## As an object method
<u>When a function is called as a method of an object, its this is set to the object the method is called on.</u>

The **this** binding is only affected by **==the most immediate member reference==**.

### this on the object's prototype chain
### this with a getter or setter

## As a constructor
When a function is used as a constructor (with the new **keyword**), its **this** is bound to the new object being constructed.


**While the default for a constructor is to return the object referenced by this, it can instead return some other object (==if the return value isn't an object, then the this object is returned==).**

```js
/*
 * Constructors work like this:
 *
 * function MyConstructor(){
 *   // Actual function body code goes here.  
 *   // Create properties on |this| as
 *   // desired by assigning to them.  E.g.,
 *   this.fum = "nom";
 *   // et cetera...
 *
 *   // If the function has a return statement that
 *   // returns an object, that object will be the
 *   // result of the |new| expression.  Otherwise,
 *   // the result of the expression is the object
 *   // currently bound to |this|
 *   // (i.e., the common case most usually seen).
 * }
 */

function C() {
  this.a = 37;
}

var o = new C();
console.log(o.a); // 37


function C2() {
  this.a = 37;
  return {a: 38};
}

o = new C2();
console.log(o.a); // 38
```

## As a DOM event handler
## In an inline event handler
## Example:
```js
const videos = {
    title: "a",
    tags: ["1", "2", "3"],
    showTitles: function() {
        this.tags.forEach((eachTag) => console.log(this.title + eachTag));
    }
}

const videos = {
    title: "a",
    tags: ["1", "2", "3"],
    showTitles: function() {
        this.tags.forEach(function(eachTag) {
            console.log(this.title + eachTag);
        }.bind(this));
    }
}

const videos = {
    title: "a",
    tags: ["1", "2", "3],
    showTitles: function() {
        this.tags.forEach(function(eachTag) {
            console.log(this.title + eachTag);
        }, this);
    }
}
```