
1. 严格模式：脚本的顶部或者是指定函数内部的顶部
    ```js
    "use strict;"
    
    function doSomething() {
        "use strict";
        //function body
    }
    ```
2. 未经初始化的变量为保存一个特殊的值undefined
3. 通过var操作符定义的变量将成为定义该变量的作用域中的局部变量。如果省略var操作符就会创建一个全局变量。
4. 数据类型：
    - 5种基本数据类型（简单数据类型）: Undefined, Null, Boolean, Number, String.
    - 1种复杂数据类型：Object.
5. <u>typeof</u> ： Note: "typeof" is an <u>operator</u> rather than a function, so it's not neccessary to use a pair of round brackets. 
    ```js
    typeof null; //object
    ```
    特殊值null被认为是一个空的对象引用。
6. <u>Literal undefined is mainly used to distinct **pointer which refers to null object from the variable that hasn't been initiated.**</u>
7. <u>undefined is derived from null</u>, so the expression "null == defined" will always return true.
8. 任何基于IEEE754格式的语言0.1 + 0.2 = 0.30000000000000004，因此永远不要测试某个特定浮点数值。
9. 在ECMAScript中，NaN用于表示一个本来要返回数值的操作数未返回数值的情况（这样就不会抛出错误）, 且任何数值除以非数值都会返回NaN.
10. ==NaN与任何值都不想等，包括其本身==.
    ```js
    NaN == NaN // false
    ```
11. function: isNaN(),判断参数是否能被转化为数值.
    ```js
    isNaN(NaN); // true
    isNaN(10); // false
    isNaN("10b"); // false
    isNaN("Blue"); // true
    isNaN(true); // false
    isNaN(undefined); // true
    isNaN(null); //false
    ```
12. parseInt("")返回NaN; Number("")返回0;parseInt()第二个参数用来指定基数，parseInt("123", 16)指定16进制.
13. obj.toString() // toString is a method; String(obj) // String() is a function.
14. 要把某个值转化为字符串可以使用加号操作符把它与一个空字符串相加
    ```js
    var a;
    alert(a + ""); // undefined
    ```
15. ==对某个对象进行操作时（一元操作符，二元等），先调用对象的valueOf方法，以取得一个可供操作的值，如果结果是NaN,则再调用toString方法后再应用前述规则讲对象变量变成数值变量==。
16. ECMAScript中所有数以IEEE-754 64位格式存储，但是位操作符不直接操作64位的值，而是先将64位值转换为32位的整数，然后执行操作。
17. 左移不会影响符号位，如果将2向左边移动5位，结果将是-64而不是64.
18. 有符号的右移，用符号位的数字填充右边出现的空位。
19. ```"10" == 10 / true; "10" === 10 / false;```
20. 如果参与乘性计算的某个操作数不是数值，后台会先用Number()转型函数将其转换为数值，空字符串被当作0，布尔值true将被当作1.
21. ```Math.floor(7 / 5); // 1; Math.ceil(7 / 5); // 2```
22. 数值转换：有三个函数Number(), parseInt(), parseFloat(),第一个函数可以用于任何数据类型，另外两个专门用于把字符串转换为数值
    - **Number()**:  null ->0; undefined -> NaN; "" -> 0;
    - **parseInt()**:  "" -> NaN;
23. ```null == undefined; // true; null === undefined; // false```
24. ECMAScript中不存在块级作用域, 因此在循环内部定义的变量可以在外部访问到。
    ```js
    var count = 10;
    for (var i = 0; i < count; i++) {
        alert(i)''
    }
    alert(i); // 10
    ```
25. ECMAScript中switch借鉴其他语言，但也有自己的特色，switch语句可以使用任何数据类型，无论是字符串还是对象都没有问题，其次每个case的值不一定是常量，可以是表达式，变量。
    ```js
    var num = -10;
    switch (true) {
        case num < 0:
            alert("num < 0");
            break;
        case num > 0:
            alert("num > 0");
            break;
        default:
            alert("ok");
    }
    ```
26. switch语句在比较时用的是全等"==="操作符.
27. ECMAScript中函数的参数在内部是用一个数组来表示的，函数接收到的始终都是这个数组，而不关心数组中包含哪个参数。即便定义函数只接收两个参数，在调用的时候也未必一定要传递两个参数，可以传递一个或者三个。以下两个函数执行后，得到同样的结果。函数体内可以用arguments对象来访问这个参数数组。
    ```js
    function sayHi(name, message) {
        return "Hello " + name + ', ' + message;
    }
    
    function sayHi1() {
        return "Hello " + arguments[0] + ", " + arguments[1];
    }
    ```
28. arguments对象可以与命名参数一起使用
    ```js
    function doAdd(num1, num2) {
        if (arguments.length === 1) {
            alert(num1 + 10);
        } else if (arguments.length === 2) {
            alert(arguments[0] + num2);
        }
    }
    ```
29. arguments的值永远与对应命名参数的值保持同步，这并不是说读取这两个值会访问相同的内存空间，它们的内存空间是独立的，但它们的值会同步。

