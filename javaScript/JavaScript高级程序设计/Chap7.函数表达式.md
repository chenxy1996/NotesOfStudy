[TOC]

# Function Expressions

##  function declaration & function expression

More info:

1.  [Function Expression & Function Declaration & Named Function Expression.md](../Function Expression & Function Declaration & Named Function Expression.md)
2. [Hoisting and JavaScript Variable Scope.md](../Hoisting and JavaScript Variable Scope.md)
3.  [Named Function Expression.md](../Named Function Expression.md)

## Closure

closure: 有权访问另一个函数作用域中的变量的函数。创建闭包的常见方式，就是在一个函数内部创建另一个函数。

```js
function createComparisonFunction(propertyName) {
    return function(object1, object2) {
        var value1 = object1[propertyName];
        var value2 = object2[propertyName];
        
        return value1 - value2
    }
}
```



<u>当某个函数被调用时，会创建一个**执行环境  (execution context)**， 以及相应的**作用域链**。然后，使用 **arguments** 和其他命名参数的值来初始化函数的**活动对象 (activation object)**。但在作用链中，外部函数的活动对象始终处于第二位，外部函数的外部函数的活动对象处于第三位，……直至作为作用域链终点的全局执行环境。</u>

*When a function is called, an execution context is created, and its scope chain is created. The activation object for the function is initialized with values for arguments and any named arguments. The outer function’s activation object is the second object in the scope chain. This process continues for all containing functions until the scope chain terminates with the global execution context.*

**A function that is defined inside another function adds the containing function's activation object into its scope chain.**

### Closures and Variables

The closure always gets the last value of any variable from the containing function.

```js
function createFunctions() {
    var result = new Array();
    
    for (var i=0; i < 10; i++) {
        result[i] = function () {
            return i;
        };
    }
    
    return result;
}
```



Since each function has the createFunctions() activation object in its scope chain, they are all referring to the same variable, i. When createFunctions() finishes running, the value of i is 10, and since every function references the same variable object in which i exists, the value of i inside each function is 10.



A fix:

```js
function createFunctions() {
    var result = new Array();
    
    for (var i=1; i <= 10; i++) {
        result[i] = function fun1(num) {
            return function fun2() {
                return num;
            };
        }(i);
    }
    
    return result;
}
```



此时for循环进行第一次时候，如下图所示:

![scopeChain_1](../../image/scopeChain_1.jpg)

for 循环进行第三次时候，如下图所示:

![scopeChain_2](../../image/scopeChain_2.jpg)



###  The `this` Object

The `this` object is bound at runtime based on the **context in which a function is executed**:

1. when used inside global functions, `this` is equal to `window` in non-strict mode and `undefined` in strict mode.
2. whereas this is equal to the object when called as an object method.
3. Anonymous functions are not bound to an object in this context, meaning the this object points to window unless executing in strict mode (where `this` is undefined).

```js
var name = "The Window";
var object = {
    name: "My Object",
    getNameFunc: function() {
        var that = this;
        return function() {
            return that.name;
        };
    }
};

alert(object.getNameFunc()());
```



### Memory Leaks

## Mimicking block scope

The basic syntax of an anonymous function used as a block scope (often called a *private scope*) is as follows:

```js
(function() {
    //block code here
})();
```

Remember a function declaration followed by parentheses won't work. However, a function expression can work. So to turn the function declaration into a function expression, you need only **surround it with parentheses**.

**Note:** Now, we can use **`let`** to declare a variable instead of `var` to realize the block scope.

## Private variables

Any variable inside a function is considered private since it is inaccessible outside that function. This includes **function arguments, local variable, function defined inside other functions*

```js
function add(num1, num2) {
    var sum = num1 + num2;
    return sum;
}
```

### privileged method

Two ways to create privileged methods on objects.

- ```js
  function MyObject() {
      // private variable and functions
      var privateVariable = 10;
      
      function privateFunction() {
          return false;
      }
      
      this.publicMethod = function() {
          privateVariable++;
          return privateFunction();
      }
  }
  ```

- ```js
  function Person(name){
      this.getName = function(){
      	return name;
      };
      this.setName = function (value) {
      	name = value;
      };
  }
  var person = new Person(“Nicholas”);
  alert(person.getName()); //”Nicholas”
  person.setName(“Greg”);
  alert(person.getName()); //”Greg”
  ```

### Static Private Variables

```js
(function(){
    //private variables and functions
    var privateVariable = 10;
    function privateFunction(){
    	return false;
    }
    //constructor
    MyObject = function(){
    };
    //public and privileged methods
    MyObject.prototype.publicMethod = function() {
        privateVariable++;
        return privateFunction();
    };
})();
```

This pattern defined the constructor not by using a function but instead by using a function expression. Function declarations always create local functions, which is undesiable in this case. For this same reason, the _var_ key word is not used with `MyObject`.

Remember: **initializing an undeclared variable always creates a global variable, so `MyObject` becomes global and available outside the private scope. Also keep in mind that assigning to an undeclared variable in strict mode causes an error.**

==In this pattern, private variables and functions are shared among instances==

```js
(function() {
    var name = "";
    Person = function(value) {
        name = value;
    };
    Person.prototype.getName = function() {
        return name;
    };
    Person.prototype.setName = function(value) {
        name = value;
    };
})();

var person1 = new Person(“Nicholas”);
alert(person1.getName()); //”Nicholas”
person1.setName(“Greg”);
alert(person1.getName()); //”Greg”
var person2 = new Person(“Michael”);
alert(person1.getName()); //”Michael”
alert(person2.getName()); //”Michael”
```

Using this pattern, the `name` variable becomes static and will be used among all instances.

### The Module Pattern

The module pattern augments the basic singleton to allow for private variables and privileged methods.

```js
var singleton = function() {
    //private variables and functions
    var privateVariable = 10;
    
    function privateFunction() {
        return false;
    }
    
    //privileged/public methods and properties
    return {
        publicProperty: true,
        publicMethod: function() {
            privateVariable++;
            return privateFunction();
        }
    };
}();
```

That object literal contains only properties and methods that should be public. Since the object is defined inside the anonymous function, all of the public methods have access to the private variables and functions. Essentially, the object literal defines the public interface for the singleton.

```js
var application = function() {
    //private variables and functions
    var components = new Array();
    
    //initialization
    components.push(new BaseComponent());
    
    //public interface
    return {
        getComponentCount: function() {
            return components.length;
        },
        
        registerComponent: function(component) {
            if (typeof component === "object") {
                components.push(component);
            }
        }
    };
}();
```

### The Module-Augmentation Pattern

```js
var singleton = function(){
	//private variables and functions
	var privateVariable = 10;
	function privateFunction(){
		return false;
	}
	//create object
	var object = new CustomType();
	//add privileged/public properties and methods
	object.publicProperty = true;
	object.publicMethod = function(){
		privateVariable++;
		return privateFunction();
	};
	//return the object
	return object;
}();
```

```js
var application = function(){
    //private variables and functions
    var components = new Array();
    //initialization
    components.push(new BaseComponent());
    //create a local copy of application
    var app = new BaseComponent();
    //public interface
    app.getComponentCount = function(){
        return components.length;
    };
    app.registerComponent = function(component){
        if (typeof component == “object”){
        	components.push(component);
        }
    };
        //return it
    return app;
}();
```































