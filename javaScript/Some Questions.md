Q1: console.log(this)为什么输出的是window对象而不是console对象？

A1: console.log(this) 中这个this是传入log的参数，此时this指向window;只有在函数体内的this才指向console. 

Q2：A very interesting example:
```js
function Test(x) {
    this.x = x;
    this.sayX = function() {
		console.log("1: " + this);
        console.log("1: " + this.x);
        var func = function() {
            console.log("2: " + this);
            console.log("2: " + this.x);
        }
		return func;
    }
}

var x = 10;
var aTest = new Test(8);
var fun1 = aTest.sayX(); 
// 1: [object Object]
// 1: 8
fun1(); 
// 2: [object Window]
// 2: 10
var fun2 = aTest.sayX();
// 1: [object Object]
// 1: 8
aTest.anotherFun = fun2;
aTest.anotherFun();
// 2: [object Object]
// 2: 8
```

```js
function Test(x) {
    this.x = x;
    this.sayX = function() {
		console.log("1: " + this);
        console.log("1: " + this.x);
        var that = this;
        this.func = function() {
            console.log("2: " + this);
            console.log("2: " + this.x);
        }
		return this.func;
    }
}

var x = 10;
var aTest = new Test(8);
aTest.sayX();
// 1: [object Object]
// 1: 8
aTest.fun();
// 2: [object Object]
// 2: 8
```

```js
function Test(x) {
    this.x = x;
    this.sayX = function() {
		console.log("1: " + this);
        console.log("1: " + this.x);
        var that = this;
        return function() {
            console.log("2: " + this);
            console.log("2: " + this.x);
        }
    }
}
```