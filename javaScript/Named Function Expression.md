[toc]
# Introduction
==The whole article is based on the ECMAScript2==

In a nutshell, named function expressions are usefull for one thing only - **decriptive function names in debuggers and profilers**. There is also a possibility of using function names for recursion.

# Function Expression vs. Function Declaration
The only thing ECMA specs make clear is that <u>*Function Declaration* must always hava an identifier</u> (or a function name, if you prefer), and *Function Expression* may omit it:
```js
FunctionDeclaration:
function identifier (FormalParameterList_opt){FunctionBody}

FunctionExpression:
function identifier_opt (FormalParameterList_opt){FunctionBody}
```

We can see that when identifier is omitted, that "something" can only bee an expression. <u>But what if identifier is present? How can one tell whether it is a function declaration or a function expression ---they look identical after all?</u> It appears that ECMAScript differentiates bwteen two based on a context. **if a ```function foo(){}``` is part of, say, ==assignment expression==, it is considered a function expression. If, on the other hand, ```function foo(){}``` is ==contained in a function body or in a (top level of) program iteself== --- it is parsed as a function declaretion.**
```js
function foo(){} // declaration, since it's part of a <em>Program</em>
var bar = function foo(){}; // expression, since it's part of an <em>AssignmentExpression</em>

new function bar(){}; // expression, since it's part of a <em>NewExpression</em>

(function(){
  function bar(){} // declaration, since it's part of a <em>FunctionBody</em>
})();
```

## Grouping Operator ()

A somewhat less obvious expression is the one where funciton is wrapped with parenthesis ---```(function foo(){})```.  The reason it is an expression is again **due to a context: "(" and ")" constitute a grouping oeprator can only contain an expression**:

To demonstrate with examples:
```js
function foo(){} // function declaration
(function foo(){}); // function expression: due to grouping operator

try {
  (var x = 5); // grouping operator can only contain expression, not a statement (which `var` is)
} catch(err) {
  // SyntaxError
}
```

**Note:** When evaluating JSON with ```eval```, the string is usually wrapped with parenthesis ---```eval('(' + json + ')')```. This is of course done for the same reason ---**grouping operator, which parenthesis are, forces JSON brackets to be parsed as expression rather than a block**.

## Subtle difference in behavior of declarations and expressions

- First of all, **Function hoisting**, function declarations are parsed and evaluated before any other expressions are. Even if declaration is positioned last in a source, it will be evaluated foremost any other expressions contained in a scope. The following example demonstrates how fn function is already defined by the time alert is executed, even though it’s being declared right after it.


- Another important trait of function declarations is that declaring them conditionally is non-standardized and varies across different environments. You should never rely on functions being declared conditionally and use function expressions instead.
    ```js
    // Never do this!
    // Some browsers will declare `foo` as the one returning 'first',
    // while others — returning 'second'
    
    if (true) {
      function foo() {
        return 'first';
      }
    }
    else {
      function foo() {
        return 'second';
      }
    }
    foo();
    
    // Instead, use function expressions:
    var foo;
    if (true) {
      foo = function() {
        return 'first';
      };
    }
    else {
      foo = function() {
        return 'second';
      };
    }
    foo();
    ```
## Production rules of function declarations
FunctionDeclarations are only allowed to appear in Program or FunctionBody. Syntactically, they can not appear in Block ({ ... }) — such as that of if, while or for statements. **This is because Blocks can only contain Statements, not SourceElements, which FunctionDeclaration is.** If we look at production rules carefully, we can see that the only way Expression is allowed directly within Block is when it is part of ExpressionStatement. However, ExpressionStatement is explicitly defined to not begin with "function" keyword, and this is exactly why FunctionDeclaration cannot appear directly within a Statement or Block (note that Block is merely a list of Statements).

Because of these restrictions, whenever function appears directly in a block (such as in the previous example) it should actually be considered a syntax error, not function declaration or expression. The problem is that almost none of the implementations I've seen parse these functions strictly per rules (exceptions are BESEN and DMDScript). They interpret them in proprietary ways instead.

# Name Function Expressions
```js
// `contains` is part of "APE Javascript library" (http://dhtmlkitchen.com/ape/) by Garrett Smith
var contains = (function() {
  var docEl = document.documentElement;

  if (typeof docEl.compareDocumentPosition != 'undefined') {
    return function(el, b) {
      return (el.compareDocumentPosition(b) & 16) !== 0;
    };
  }
  else if (typeof docEl.contains != 'undefined') {
    return function(el, b) {
      return el !== b && el.contains(b);
    };
  }
  return function(el, b) {
    if (el === b) return false;
    while (el != b && (b = b.parentNode) != null);
    return el === b;
  };
})();
```
Quite obviously, when a function expression has a name (technically — Identifier), it is called a named function expression. What you've seen in the very first example —```var bar = function foo(){}``` --- was exactly that --- a named function expression with ```foo``` being a function name. **An important detail to remember is that ==this name is only available in the scoped of a newly-defined function;==** specs mandate that an identifier should be available to an enclosing scope:
```js
var f = function foo(){
  return typeof foo; // "foo" is available in this inner scope
};
// `foo` is never visible "outside"
typeof foo; // "undefined"
f(); // "function"
```

It appears that named functions make for a much more pleasant debugging experience. When debugging an application, having a call stack with descriptive items makes a huge difference.
# Function names in debuggers
# Function names bugs in JScript
==**By 20190704, nearly all bugs below have been fixed!**==
## Example #1: Function expression identifier leaks into an enclosing scope
```js
var f = function g(){};
typeof g; // "function"
```
## Example #2: Named function expression is treated as BOTH — function declaration AND function expression
```js
typeof g; // "function"
var f = function g(){};
```
## Example #3: Named function expression creates TWO DISTINCT function objects!

```js
var f = function g(){};
f === g; // false

f.expando = 'foo';
g.expando; // undefined
```
## Example #4: Function declarations are parsed sequentially and are not affected by conditional blocks
```js
var f = function g() {
  return 1;
};
if (false) {
  f = function g(){
    return 2;
  };
}
g(); // 2
```
# Two interesting Examples
## First example
```js
var f = function g(){
  return [
    arguments.callee == f,
    arguments.callee == g
  ];
};
f(); // [true, true]
```
## Second example
```js
(function(){
  f = function f(){};
})();
// Here, identifier f is defined as a global variable.
```

# JScript memory management
Let's look at a simple example:
```js
var f = (function(){
  if (true) {
    return function g(){};
  }
  return function g(){};
})();
```
```js
var f = (function(){
  var f, g;
  if (true) {
    f = function g(){};
  }
  else {
    f = function g(){};
  }
  // null `g`, so that it doesn't reference extraneous function any longer
  g = null;
  return f;
})();
```




