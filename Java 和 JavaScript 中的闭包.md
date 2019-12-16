[toc]

# Closure: Difference Between Java And JavaScript (闭包：Java 和 JavaScript 的不同)

## Conclusion

先说结论：

**java 中的闭包只有值捕获 (capture by value); javaScript 中的闭包则是引用捕获 (capture by reference)**

如果有人对值 value 和引用 reference 有不清楚的地方可以看看笔者之前写的 *C++ 中值传递、引用传递、指针传递*，或许会受到一些启发。

## Closure In Java

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

可能有的读者会问了第二次循环的时候为什么一开始在这个新的块作用域中新的 `i` 就被赋值成 1 了呢， 它是怎么知道是 1 的呢？照理来说第一次循环产生的块作用域中的最后 `i++` 使得 `i = 1` 这个值应该随着程序流离开第一次循环的块作用域而消失才对。这是因为 java 的内部机制保存了这个值，在下一次循环的块作用域中可以赋值在相同名称的局部变量中 （这里就是 `i`），更具体的实现在此不再深究（笔者也不清楚）。

