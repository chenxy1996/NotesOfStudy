[TOC]

#### 5.1 Object类型 

1. 创建Object实例:
    - new操作符后跟Object构造函数```var newObj = new Object();newObj.age = 29;```
    - 使用对象字面量```var newObj = {age : 29, name : "chen"}```
2. 通过对象字面量定义对象时，实际上是不会调用Object构造函数的。

#### 5.2 Array类型
3. ECMAScript中数组每一项可以保存任何类型数据，数组大小可以动态调整。
4. 创建方法:
    - ```var colors = new Array(); var colors = new Array(20); var colors = new Array("red", "green", "blue")```
    - 使用Arary字面量```var colors = ["red", "green", "blue"]```
5. 与对象一样，在使用数组字面量表示法时候，也不会调用Array构造函数
6. 检测是否是数组```Array.isArray(target)```
7. ```arrayObj.push(sth) <=> arrayObj.pop(); arrayObj.shift() <=> arrayObj.unshift(sth)```
8. 排序```arrayObj.sort()```注:<u>sort()方法会调用每个数组项的==toString()==转型方法，比较得到的字符串</u>，然后按照升序进行排序。
    ```js
    function compare(num1, num2) {
        return num1 - num2;
    }
    
    function compareReverse(num1. num2) {
        return num2 - num1;
    }
    
    var a = [1, -3, 2];
    a.sort(compare); // [-3, 1, 2];
    a.sort(compareReverse) // [2, 1, -3];
    ```
9. ==splice()==
    - 删除: splice(0, 2): 要删除的第一项位置和要删除的项数
    - 插入：splice(2, 0, "red", "green"): 起始位置，0（要删除的项数），最后是要插入的项目
    - 替换 splice(2, 1, "red", "green")：起始位置，要删除的项数，最后要插入的项目
10. indexOf(). lastIndexOf().

##### 迭代方法
11. every(), filter(), forEach(), map(), some();<u>这些方法都不会改变原来的数组</u>
##### 归并方法
12. nums.reduce(); nums.reduceRight();
    ```js
    var nums = [1, 2, 3, 4, 5];
    var sum = nums.reduce(function(prev, cur, index, array) {
        return prev + cur;
    }) // sum = 15;
    ```
#### 5.5 Function 类型
13. **函数实际上是对象**， 每个函数都是Function类型的实例,而且都与其他引用类型一样具有属性和方法。<u>函数名实际上是一个指向函数对象的指针</u>。
14. 函数声明和函数表达式几乎一致。
    ```js
    //函数声明
    function sum(num1. num2) {
        return num1 + num2;
    }
    
    //函数表达式
    var sum = function(num1, num2) {
        return num1 + num2;
    };
    ```
    但是在解析器向执行环境中加载数据时候，解析器会率先读取函数声明，并使其在执行任何代码之前可用。
    所以下面代码是可行的
    ```js
    alert(sum(1, 2));
    function sum(num1, num2) {
        return num1 + num2;
    }
    ```
    但是用函数表达式就不行了,下面的会报错
    ```js
    alert(sum(1, 2));
    var sum = function(num1, num2) {
        return num1 + num2;
    };
    ```
    原因在于函数位于一个初始化的语句中，而不是一个函数声明。

##### 函数的内部属性
15. arguments.callee
    ```js
    function factorial(num) {
        return num <= 1 ? 1 : num * arguments.callee(num - 1);7
    }
    ```
16. <u>this引用的是函数执行的**环境对象**</u>
17. 每个函数都包含两个非继承而来的方法: apply()和call(),用来扩充函数的作用域
    ```js
    window.color = "red";
    var o = {color: "blue"};
    
    function sayColor() {
        alert(this.color);
    }
    
    sayColor(); //red
    sayColor.call(this); //red;
    sayColor.call(window); //red;
    sayColor.call(o); //blue;
    ```
18. 函数的bind()方法.
    ```js
    var objSayColor = sayColor.bind(o);
    objSayColor(); //blue;
    ```
#### ==5.6 基本包装类型==
19. 三个特殊的引用类型: Boolean, Number, String;
20. <u>每当**读取一个基本类型值**的时候，后台就会**创建一个对应的基本包装类型的对象**</u>
21. 访问处于一种读取模式
    ```js
    var s1 = "Some text";
    var s2 = s1.substring((2);
    ```
    当第二行代码访问s1时，访问处于一种读取模式, 也就是要从内存中读取这个字符串的值，而在读取模式中访问字符串时，后台会自动完成下列处理：
    1. 创建String类型的一个实例
    2. 在实例上调用指定的方法
    3. 销毁这个实例
    
    可以想象成执行了以下ECMAScript代码
    ```js
    var s1 = new String("Some text");
    var s2 = s1.substring(2);
    s1 = null;
    ```
22. 引用类型和基本包装类型的主要区别就是**对象的生存期**。使用new操作符创建的引用类型的实例，在执行流离开当前作用域之前一直保存在内存中。而自动创建的基本包装类型的对象，则只存在与一行代码的执行瞬间，然后立即被销毁。
    ```js
    var s1 = "some text";
    s1.color = "red";
    alert(s1.color);
    ```
    第二行创建的String对象在执行第三行代码时已经被销毁了。第三行又创建了新的String对象，这个新的对象没有color属性.
23. 对基本包装类型的实例调用typeof 会返回"object", 而且<u>==所有基本包装类型的对象在转换为布尔值时都是true==.</u>
    ```js
    var s1 = new String("");
    var s2 = "";
    Boolean(s1); // true
    Boolean(s2); //false
    ```
24. ```var a = new Boolean(false); a && true; //truew```
25. replace(): 接受两个参数，第一个可以是一个**RegExp对象**或者是一个**字符串**（这个字符串不会被转换成正则表达式）,第二个参数可以是字符串或者是一个函数。<u>如果第一个参数是字符串，那么只会替换第一个子字符串。想要替换全部，就**必须要用正则表达式**，而且要**指定全局(g)**的标志。</u>
    ```js
    var text = " abcd  123 efg";
    text.replace(" ", ""); //abcd  123 efg
    text.replace(/ /g, ""); //abcd123efg
    ```
#### 单体内置对象
26. Global全局对象: 不属于任何其他对象的属性和方法，最终都是它的属性和方法。事实上没有全局变量或全局函数，所有在全局作用域中定义的属性和函数都是Global对象的属性和函数。例如isNaN(), isFinite(), parseInt()等等
27. <u>在浏览器中global对象是等于window对象的</u>;<u>在大多数ECMAScript实现中都不能直接访问Global对象；**不过Web浏览器实现了承担该角色的window对象**</u>。
28. ==一个例子==：
    ```js
    var a = "abcd";
    var b = new String(a);
    a == b; //true
    
    var aStr1 = new String("abcd");
    var aStr2 = new String("abcd");
    aStr1 == aStr2; //false
    ```
    原因是==比较的机制：<u>a和b相比较时候, a是基本类型数据，b是对象，则首先调用b的valueOf()方法，然后得到"abcd",与a相等</u>；<u>比较aStr1和aStr2时候，因为两个都是对象，则比较aStr1和aStr2是否是指向同一个对象，显然不是</u>。
29. ==找数组最大值的小trick==：Math.max(); 但是此函数的参数是一个一个的值而不是数组：```Math.max(1, 2, -1);//-1 ```现在想要传递对数组使用这个方法
    ```js
    var values = [-1, 0, -3, 1, 2, 5];
    var max = Math.max.apply(Math, values);
    ```
30. ```Math.floor(); Math.round(); Math.ceil();```
31. ```Math.random();```返回一个**大于等于0**且**小于1**的数
32. 通过Math.random()实现在于两个数之间的（包括这两个数）的函数：
    ```js
    function selectFrom(lowerValue, upperValue) {
        var choices = upperValue - lowerValue;
        return Math.floor(Math.random() * choices + lowervalue);
    }
    ```