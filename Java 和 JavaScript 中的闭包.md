[toc]

# Closure: Difference Between Java And JavaScript, Python (闭包：Java 和 JavaScript 以及 Python 的不同)

## Conclusion

先说结论：

**java 中的闭包只有值捕获 (capture by value); javaScript 中的闭包则是引用捕获 (capture by reference)**

如果有人对值 value 和引用 reference 有不清楚的地方可以看看笔者之前写的 *C++ 中值传递、引用传递、指针传递*，或许会受到一些启发。

## Closure In Java Ⅰ

下面的列表清单：

```java
package closure;

public class ClosureTest {
    public static void main(String[] args) {
        // java 中是没有函数的概念的, 这里用了
        // java 库中的 Runnable 接口。
        Runnable[] functionList = new Runnable[5];

        // 给上面的数组添加 Runnable 元素
        for (int i = 0; i < 5; i++) {
            int j = i;
            functionList[i] = () -> System.out.println(j);
        }

        // 依次执行数组中的 Runnable 元素
        for (Runnable elem : functionList) {
            elem.run();
        }
    }
}
```

其中在第一个 `for` 循环中有 `int j = i`; 如果省略这一步，直接将 `System.out.println(j)` 改为 `System.out.println(i)` , 编译器会报错，提示 <u>variable captured by lambda expression should be final or effectively final.</u> 此时可能就会有疑问：既然 `for` 循环中每一次循环都会有产生一个新的块作用域，里面包括一个新的的 `i` ， 那么各个块中的 `i` 应该互不影响，是不变的也就是 `final`, 那么为什么编译器会报错呢? 为了解决这个问题，可以另开一小节来具体说明。

## Details hidden in for loop （for 循环隐藏的细节）

如果上面第一个 `for` 循环这么写：

```java
for (int i = 0; i < 5; i++) {
    functionList[i] = () -> System.out.println(i);
}
```

将 `for` 循环拆分来看。

第一次循环:

```
// The first block scope
{
    int i = 0;
    functionList[i] = () -> System.out.println(i); // i = 0
    
    // 最后一步
    i++;
}
```

**最后一步中改变了 `i` 的值，违背了 java 的闭包捕获的外界变量必须是 final or effectively final 这个原则，所以编译器会报错。**

如果将上述的 `for`  循环改为:

```java
for (int i = 0; i < 5; i++) {
    int j =i;
    functionList[i] = () -> System.out.println(j);
}
```

那么第一次循环：

```java
// The first block scope
{
    int i = 0;
    int j = i;
    functionList[i] = () -> System.out.println(j); // j = 0
    
    // 最后一步
    i++;
}
```

被闭包所捕获的值只有 `j` ，且其符合闭包捕获外界变量的原则。

接下来我们看第二次循环:

```java
// The second block scope
{
    int i = 1;
    int j =i;
    functionList[i] = () -> System.out.println(j); // j = 0
    
    // 最后一步
    i++;
}
```

可能有的读者会问了第二次循环的时候为什么一开始在这个新的块作用域中新的 `i` 就被赋值成 1 了呢， 它是怎么知道是 1 的呢？<u>照理来说第一次循环产生的块作用域中的最后 `i++` 使得 `i = 1` 这个值应该随着程序流离开第一次循环的块作用域而消失才对。这是因为 java 的内部机制保存了这个值，在下一次循环的块作用域中可以赋值在相同名称的局部变量中 （这里就是 `i`），</u>更具体的实现在此不再深究（笔者也不清楚）。

同样的道理，如果我们在 javaScript 中写了如下的代码：

```js
for (const i = 0; i < 5; i++) {
    console.log(i);
}
```

最后在控制台只能看见打印出一个 0 后就报错：TypeError: Assignment to constant variable. 也是出于同样的原因，最后一步的 `i++` 。

##  Closure In Java Ⅱ

回到 java 闭包这一话题。

之前的结论是 java 闭包捕获外界变量的方式是值捕获。**<u>编译器会复制一份被捕捉的外界变量。</u>** 对 java 虚拟机来说是无法识别出一个变量是否是由编译器复制捕获而复制产生的。内部类、局部方法中的类、lambda表达式如果涉及到闭包都是采用该方式。

如下面的代码：

```java
int i = 0;
Runnable r = () -> System.out.println(i);
```

编译器会在 `r` 对象中拷贝一份 `i` 的值。

其具体的大概实现是：

第一步：java 编译器会创建一个新类, 起名为 Runnable$1, 该名字是笔者随便取的，其名称由编译器自己决定。

```java
class Runnable$1 {
    private int copyi;
    public Runnable$1(int arg) {
        copyi = arg;
    }
    public void run() {
        System.out.println(copyi);
    }
}
```

第二部：实例化一个 `Runnable$1` 对象， 并将 `i` 作为构造函数的参数传入。

```java
Runnble r = new Runnable$1(i);
```

## Closure In JavaScript

与 Java 相反， **JavaScript 闭包获取外界变量是通过引用捕获的方式**。

看如下经典代码：

```js
function closureTest() {
    var functionList =[];

    // 开始给上面的数组中添加元素
    for (var i = 0; i < 5; i++) {
        functionList.push(() => {
            console.log(i);
        })
    }

    // 依次执行上面数组中所储存的函数
    functionList.forEach((elem) => elem());
}

closureTest();
```

最后控制台打印的结果是：

```js
5
5
5
5
5
```

`i` 的 标识符为 `var` ，说明 `i` 是不在 `for` 循环的块状作用域中的，而是在函数 `closureTest` 的作用域中，`functionList` 里面的每一个函数都会捕获 `i` 的引用，而 `i` 的值是变化的直到等于5。

仿照 java 的做法，在循环中定义一个新的变量 `j`：

```js
function closureTest() {
    var functionList = [];
    
    for (var i = 0; i < 5; i++) {
        var j = i;
        functionList.push(() => {
            console.log(j);
        })
    }
}
```

但是结果确实：

```js
4
4
4
4
4
```

其原因和之前一样，`j` 看似是在循环的块作用域中，其实不是，它和直接声明在 `for` 之外的效果是一样的。

为了得到想要的结果，就必须创建一个新的作用域来保存每一次循环中 `i` 的值，ECMA6 之前没有 `let` 和 `const`, 为了实现想要的效果，只能通过函数的作用域。所以可以创建一个立即执行函数：

```js
function closureTest() {
    var functionList = [];
    
    for (var i = 0; i < 5; i++) {
		(function(j) {
            functionList.push(() => {
                console.log(j);
            })
        })(i);
    }
}
```

ECMA6 之后则可以直接将 `for` 循环中的 `var i =0` 改为 `let i = 0`， 从而让每次循环都会产生一个块作用域。

## 拓展：Python 的闭包

Python 的闭包是引用捕获，但是无法对外界捕获变量进行赋值, 除非声明捕获的变量为 `nonlocal`

```python
def test():
    function_list = []
    arg = 1
    i = 0

    # initialization
    while (i < 5):
        def innerFunction():
            nonlocal arg
            print(arg + i)
            arg += 1
            
        function_list.append(innerFunction)
        i += 1
    
    for each in function_list:
        each()

if __name__ == "__main__":
    test()
```

## 修改捕获的变量的值

前面写那么多，读者自然会想到闭包能否修改捕获的外界变量的值呢？

对于 java 来说是不行的，毕竟语言规定其捕获的变量必须是 final or effectively final. 但是对于 javaScript 来说则是完全可行的。

先来看 javaScript：

```js
function alterVariable() {
    let a = 0;
    for (let i = 0; i < 5; i++) {
        (function() {
            console.log(a);
            a++;
        })();
    }
}

alterVariable();
```

其在控制台输出的结果是：

```js
0
1
2
3
4
```

可以看到，javaScript 是可以通过引用来改变闭包中所捕获的外界变量的值的。

再来看看 java:

```java
public class ClosureTest {
    public static void main(String[] args) {
        int a = 0;
        
        for (int i = 0; i < 5; i++) {
            Runnable r = () -> {
                System.out.println(a);
                a++;
            };
            r.run();
        }
    }
}
```

上述代码会被编译器检测到错误，原因之前也说了。

但是既然不能直接改变 `a` 的值，我们可以通过将 `a` 声明成一个引用类型来改变，从而间接达到我们的目的：

```java
public class ClosureTest {
    public static void main(String[] args) {
        int[] a = {0};
        
        for (int i = 0; i < 5; i++) {
            Runnable r = () -> {
                System.out.println(a[0]);
                a[0] += 1;
            }；
            r.run();
        }
    }
}
```

`a` 是一个引用类型（int 数组）, `Runnbale r` 闭包捕获 `a`, 其 `run` 方法中并没有改变引用的值而是改变了引用所指向的对象的值。这样子间接可以达到我们想要的效果。读者可能会觉得这种写法会不会是一个“邪道”，大可不必有这样的担忧，在 jdk 源码内部也有不少代码是利用这种方式间接实现“改变”闭包捕获的外界的变量值的目的。

最后作为一个拓展，也来看看 python 中能否实现：

```python
def closure_test():
    a = 0 
    
    for i in range(0, 5):
        def run():
            print(a)
            a += 1
        run()
        
	return

if __name__ == "__main__":
    closure_test()
```

运行的时候发现出错，错误信息为：local variable 'a' referenced before assignment.

原因之前也说过：Python 的闭包是引用捕获，但是无法对外界捕获变量进行赋值, 除非声明捕获的变量为 `nonlocal`。在 `run` 函数前面加上一句 `nonlocal a`, 就能运行成功.

当然，也可以用把刚刚在 java 中用的方法:

```python
def closure_test():
    a = [0]
    
    for i in range(0, 5):
        def run():
            print(a[0])
            a[0] == 1
        run()

if __name__ == "__main__":
    closure_test()
```

