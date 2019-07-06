[toc]
# Variable Scope
## intro
The scope of a variable is controlled by the <u>location of the variable declaration, and defines the part of the program where a particular variable is accessible.</u>
## scope rules
### global scope
Any variable declared **outside of a function** belongs to the global scope, and is therefore accessible from anywhere in your code.
### local scope
Each function has its own scope, and any variable declared **within that function** <u>is only accessible from that function and any nested functions</u>. Because local scope in JavaScript is created by functions, it’s also called function scope. When we put a function inside another function, then we create nested scope.

The type of look up is called *lexical(static) scope*. <u>**The static structure** of a program determines the variable scope.</u>

**The scope of a variable is defined by its location within the source code,** and nested functions have access to variables declared in their outer scope. <u>**No matter where a function is called from, or even how it’s called, its lexical scope depends only by where the function was declared.**</u>

#### shadowing
In JavaScript, variables with the same name can be specified at multiple layers of nested scope. In such case local variables gain priority over global variables. If you declare a local variable and a global variable with the same name, the local variable will take precedence when you use it inside a function. This type of behavior is called shadowing. Simply put, the inner variable shadows the outer.

```js
var test = "I'm global";

function testScope() {
  var test = "I'm local";

  console.log (test);     
}

testScope();           // output: I'm local

console.log(test);     // output: I'm global
```

# Hoisting
A JavaScript interpreter performs many things behind the scene, and one of them is called hoisting. If you are not aware of this "hidden" behavior, it can cause a lot of confusion. The best way of thinking about the behavior of JavaScript variables is to always visualize them as consisting of two parts: a declaration and an assignment:
```js
var state;             // variable declaration
state = "ready";       // variable definition (assignment)

var state = "ready";   // declaration plus definition
```

The JavaScript engine treats the third single statement as two separate statements, just like in the first two lines in the example.

We already know that any variable declared within a scope belongs to that scope. But what we don’t know yet, is that <u>**no matter where variables are declared within a particular scope, all variable declarations are moved to the top of their scope (global or local).**</u> This is called hoisting, as the variable declarations are hoisted to the top of the scope. **==Note that hoisting only moves the declaration. Any assignments are left in place.==** Let’s see an example:

```js
console.log(state);   // output: undefined
var state = "ready";
```
 Here is how the code is interpreted by a JavaScript engine:
 ```js
 var state;           // moved to the top
console.log(state);   
state = "ready";     // left in place
 ```
 
 Another example on hoisting of function declarations:
 ```js
 showState();            // output: Ready

function showState() {
  console.log("Ready");
} 

var showState = function() {
  console.log("Idle");
};
 ```
 The code is interpreted like this:
 ```js
 function showState() {     // moved to the top (function declaration)
  console.log("Ready");
} 

var showState;            // moved to the top (variable declaration)

showState();  

showState = function() {   // left in place (variable assignment)
  console.log("Idle");
};
 ```

In the code above we saw that the function declaration takes precedence over the variable declaration. 

## Summary
- All declarations, both functions and variables, are hoisted to the top of the containing scope, before any part of your code is executed.
- Functions are hoisted first, and then variables.
- Function declarations have priority over variable declarations, but not over variable assignments.