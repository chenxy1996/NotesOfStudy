[toc]
# Function Declaration
```js
function x() {
    console.log('x');
}
```
A function declaration is a *declaration*; <u>it's not a statement or expression.</u> As sun, you don't follow it with a `;`(althougu doing so is harmless)

## Function Hoisting
A function declaration is processed when execution enters the context in which it appears, ==**before** any step-by-step code is executed==. The function it creates is given a proper name(`x` in the example above), and <u>that name is put in the scope in which the declaration appears.</u>

**Note**: <u>function declaration is also scoped</u>. In the example below, the name ```abc``` is defined in the scope where this definition is encountered. <u>The situation that a nested function is embeded into an outer function is called closure.</u> 
```js
function abc() {
    function abc(){};
    // abc is defined here...
}
// ... but not here.
```

Due to the mechanism of function hoisting, you can call a function before the position in which you declare a function.
```js
x(); // Works even it's above the declaration
function x() {
    console.log('x');
}
```
**Note**: Putting a function daclaration inside a control structure like ```try, if, switch, while,```etc should be avoided. like this:
```js
if (someCondition) {
    function foo() {
        
    }
}
```

# Anonymous Function Expression
The form *anonymous function expression* is also called *function expression*.
```js
var y = function() {
    console.log('y');
};
```
Like all expressions, <u>it's evaluated when is's **==reached==** in the step-by-step execution of the code</u>.

The function is assigned a name if possible by inferring it from context. In the example above, the name would be y. 

# Named Function Expression
```js
var z = function w() {
    console.log('zw');
};
```
As anonymous function expression, <u>this is evaluated when it's **==reached==** in the step-by-step execution of the code.</u>

The **name** of the function is **==not== added to the scope in which the expression appears. The name is ==in== the scope within the function itself**:
```js
var z = function w() {
    console.log(typeof w); // "function"
};
console.log(typeof w); // "undefined"
```

# Accessor Function Initializer (ES5+)
```js
var obj = {
    value: 0,
    get f() {
        return this.value;
    },
    set f(v) {
        this.value = v;
    }
};

console.log(obj.v); // 0
console.log(typeof obj.v) // "number"
```
Note that when I used the function, I didn't use ()! That's because <u>**it's an accessor function for a property.**</u> <u>We get and set the property in the normal way, but behind the scenes, the function is called.</u>

You can also create accessor functions with `Object.defineProperty, Object.defineProperties`, and the lesser-known second argument to `Object.create`.

# Arrow Function Expression
```js
var a = [1, 2, 3];
var b = a.map(n => n * 2);
console.log(b.join(", ")); 
```

- <u>They don't have their own this. Instead, they close over the this of the context where they're defined.</u> (They also close over arguments and, where relevant, super.) <u>This means that **the this within them is the same as the this where they're created, and cannot be changed**.</u>
- As you'll have noticed with the above, you don't use the keyword function; instead, you use =>.

# Method Declaration in Object Initializer
ES2015 allows a shorter form of declaring a property that references a function called a *method definition*:
```js
var o = {
    foo() {
    }
};
```
The almost-equivalent in ES5 and earlier would be:
```js
var o = {
    foo: function(){
    }
};
```
**Difference**:
<u>**A method can use ```super```, but a function cannot.**</u> So for instance, if you had an object that defined(say) ```valueOf``` using method syntax, it could use ```super.valueOf()``` to get the value ```Object.prototype.valueOf``` would have returned (before presumably doing something else withit), whereas the ES5 version would have to do ```Object.prototype.valueOf.call(this)``` instead.

```js
var aStudent = {
    name: "chenxiangyu",
    toString() {
        return "The student is " + super.toString(); 
    }
}

var bStudent = {
    name: "lele",
    toString: function() {
        return "The student is " + Object.valueOf.call(this);
    }
}
```

That also means <u>the method has a reference to the object it was defined on</u>,  so if that object is temporary (for instance, you're passing it into Object.assign as one of the source objects), method syntax could mean that the object is retained in memory when otherwise it could have been garbage collected (if the JavaScript engine doesn't detect that situation and handle it if none of the methods uses ```super```).

# Constructor and Method Declaration in class
```js
class Person {
    constructor(firstName, lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
```












