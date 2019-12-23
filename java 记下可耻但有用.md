[toc]

# 写在前面

名字来源于日剧 "逃避可耻但有用" （" 逃げるは恥だが役に立つ "）。

我原来是不想专门把平常看到的不熟悉的 java 知识给记在一个单独的类似笔记本的地方的，因为我太弱了，不熟悉的地方是在是太多了，一个一个都记下来得多累。

但是今晚（20191218）想想还是得有这么个东西：一来是自己写笔记的过程也相当于是个梳理的过程，能加深对不熟悉部分的理解程度；二来是方便以后的复习。

有些人看过的东西能永远记住，并且能灵活运用，可惜的是我不是那类人，还是有些不甘心的。所以就将这份笔记的名称暂且设置为 “心有可耻但有用”。

# 正文

## 其它笔记（未分类）

## Iterator

jdk 源码上面都写着，非常详细。

### 光标位置 cursor position

Iterator 的光标是在**内部两个元素的之间**，如果还没有执行 `next()` 方法就在首元素前面，如果所有元素都遍历完后就在末尾元素之后。

图示：

```java
// 假设链表为 ABC
// | 为光标
// 开始 |ABC
// next() A|BC
// next() AB|C
// next() ABC|
```

`remove()` 删除上个 `next()` 返回的元素，如果还未调用 `next()`, 将会抛出 `IlleagalStateException` 。这部分接下来会讲得更多一些。

## ListIterator

接口，继承了 Iterator 接口。**ListIterator 是有序的，因此有涉及索引，在特定位置添加新元素等方法。**

### add(E)

与 `Collection.add(E)` 不同，其不返回 boolean 类型的值，它假定添加操作总是会改变链表。

**`ListIterator.add(E)` 会在光标之前添加一个新的元素。** （不要搞混）

```java
List<String> staff = new LinkedList<>();
ListIterator<String> iter = staff.ListIterator();

iter.add("chen"); // "chen" |
iter.add("lele"); // "chen" "lele" |

//报错 NoSuchElementException, 因为光标之后没有元素了
System.out.println(iter.next()) 
```

### remove()

与 `Iterator.remove()` 必须在 `next()` 方法调用后使用不同，ListIterator 既有 `previous()` 方法又有 `next()` 方法，所以可以跟在这两个方法的任何一个后面，结果也如此：如果跟在 `previous()`之后，就会删除之前 `previous()` 所返回的元素。

### add 只依赖光标的位置，而 remove 和 set 依赖迭代器的状态

解释后半部分，直接看相关 jdk 源码会更容易说明一些, 解释。

**以 `LinkedList` 源码中的 `ListItr` 内部类为例，他有个内部成员变量 `lastReturned`,  专门用来记录 `next()` 或是 `previous()`  方法返回的值。 如果其值为 `null` 那么将会抛出 `IlleagalStateException` 错误。并且在最后会把 `lastReturned` 重新设置为 `null`（仅 remove 方法会这样做，set 方法不会将其重新设置为 `null`）。add 方法也会将其设置为 `null`**

明白了以上这一段，基本就能搞清楚 ListIterator 一系列的操作了。

注：`listIterator.add` 不会返回 boolean 值，这与 `Collection.add` 不同。

## ArrayList

### EMPTY_ELEMENTDATA, DEFAULTCAPACITY_EMPTY_ELEMENTDATA

`ArrayList` 源码中有这两个类变量，其注释说： We distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when first element is added.   简单来讲就是第一次添加元素时知道该 elementData 从空的构造函数还是有参构造函数被初始化的。以便确认如何扩容。 

当调用无参数构造函数时候：

```java
/**
* Constructs an empty list with an initial capacity of ten.
*/
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
```

`DEFAULTCAPACITY_EMPTY_ELEMENTDATA` 赋值给 `elementData`。 

当进行接下来的 `add` 操作：

```java
public boolean add(E e) {
    modCount++  // Increments modCount!!
    add(e, elementData, size);
    return true;
}

private void add(E e, Object[] elementData, int s) {
    if (s == elementData.length)
        elementData = grow();
    elementData[s] = e;
    size = s + 1;
}

private Object[] grow() {
    return grow(size + 1);
}


private Object[] grow(int minCapacity) {
    return elementData = Arrays.copyOf(elementData,
                                       newCapacity(minCapacity));
}


private int newCapacity(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity <= 0) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return minCapacity;
    }
    return (newCapacity - MAX_ARRAY_SIZE <= 0)
        ? newCapacity
        : hugeCapacity(minCapacity);
}
```

`newCapacity` 函数中判断如果 **`elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA` **就取 `DEFAULT_CAPACITY` 和 `minCapacity` 的最大值也就是 10 。

这里还有一个注意的地方 `private void add(E e, Object[] elementData, int s)`.

改方法是一个 helpler 方法，在以前的 jdk 中是没有单独分裂出来的。其注释:

```JAVA
/**
     * This helper method split out from add(E) to keep method
     * bytecode size under 35 (the -XX:MaxInlineSize default value),
     * which helps when add(E) is called in a C1-compiled loop.
     */
```

和 JIT 的 c1 编译器优化有关。

## 允许存储 null 的 Collection 和 Map

- HashSet

## Map

### hashCode 方法 和 equals 方法

如果 `a.euquals(b) == true` 那么 `a.hashCode() == b.hashCode()` ，把 `a` 或 `b` 可能为 `null`

的情况考虑进去：如果 `Objects.hashCode(a) == Objects.hashCode(b)` 那么 `Objects.equals(a, b) == true`.

## 排序 HashMap

## 关于函数式接口 Functional Interface

### 只有一个抽象方法的接口，Object 的 public 方法除外

对于只有一个抽象方法的接口，需要这种接口的对象时，就可以提供一个 lambda 表达式。这种接口称为函数式接口 (Functional Interface)。

另外，函数式接口中也可以定义 `Object` 类的 `pulibc`  的方法，注意：必须是 `public` 方法。

```java
@FunctionalInterface
public interface Func {
    boolean equals(Object obj);
    void run();
}
```

上面代码中的函数式接口 `Func` 是行得通的。

下面的就不行.

```java
@FunctionalInterface
public interface Func {
    Object clone();
    void run();
}
```

因为 `clone()`  方法在 `Object` 类中是 `protected`。

java API 中的一些接口会重新声明 `Object`  方法来附加 javadoc 注释。例如：

```java
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
    
    /**
     * Indicates whether some other object is &quot;equal to&quot; this
     * comparator.  This method must obey the general contract of
     * {@link Object#equals(Object)}.  Additionally, this method can return
     * {@code true} <i>only</i> if the specified object is also a comparator
     * and it imposes the same ordering as this comparator.  Thus,
     * {@code comp1.equals(comp2)} implies that {@code sgn(comp1.compare(o1,
     * o2))==sgn(comp2.compare(o1, o2))} for every object reference
     * {@code o1} and {@code o2}.<p>
     *
     * Note that it is <i>always</i> safe <i>not</i> to override
     * {@code Object.equals(Object)}.  However, overriding this method may,
     * in some cases, improve performance by allowing programs to determine
     * that two distinct comparators impose the same order.
     *
     * @param   obj   the reference object with which to compare.
     * @return  {@code true} only if the specified object is also
     *          a comparator and it imposes the same ordering as this
     *          comparator.
     * @see Object#equals(Object)
     * @see Object#hashCode()
     */
    boolean equals(Object obj);
}
```

### 不能把 lambda 表达式赋给 Object 变量，Object 不是一个函数式接口

如下：

```java
// 编译错误
Object cons = (String x) -> System.out.println(x);
```

可以这样：

```java
Consumer<String> cons = (String x) -> System.out.println(x);
Object obj = cons;
```

 ### 方法引用

方法引用的三种形式：

- instance::instanceMethod
- class::staticMethod
- class::instanceMethod

都相当于一个 lambda 表达式。

- instance::instanceMethod 相当于 (T... args)  -> **instance.instanceMethod(args);**

- class::staticMethod 相当于 (T... args) -> **class.staticMethod(args);**

- class::instanceMethod 相当于 (U instance, T... args) -> **instance.instanceMethod(args);**

举个例子：

```java
public class Test {
    private String attr;
    private static String classAttr = "Test Class";

    public Test(String arg) {
        attr = arg;
    }

    public String getAttr(String description) {
        return "instance " + attr + ": " + description;
    }

    public static String getClassAttr(String description) {
        return "static " + classAttr + ": " + description;
    }

    public static void main(String[] args) {
        Test aTest = new Test("chen");
        Test bTest = new Test("lele");

        // 相当于 aTestMethodRef = (String s) -> aTest.getAttr(s)
        Function<String, String> aTestMethodRef = aTest::getAttr;
        // 相当于 bTestMethodRef = (String s) => bTest.getAttr(s)
        Function<String, String> bTestMethodRef = bTest::getAttr;

        // instance chen: nihao
        System.out.println(aTestMethodRef.apply("nihao"));
        // instance lele: nihao
        System.out.println(bTestMethodRef.apply("nihao"));

        // -----------------------------------------------------------
        Function<String, String> staticMethodRef = Test::getClassAttr;

        System.out.println(staticMethodRef.apply("nihao"));

        // -----------------------------------------------------------
        BiFunction<Test, String, String> instanceMethodRef = Test::getAttr;

        // instance chen: nihao
        System.out.println(instanceMethodRef.apply(aTest, "nihao"));
        // instance lele: nihao
        System.out.println(instanceMethodRef.apply(bTest, "nihao"));
    }
}
```

## Generic 泛型

关于泛型，说实话掌握得也不是很好，但是碰到一点需要记住的就记下来吧，以后经常翻过来看看。

### PECS 原则

 *effective java* 一书中给出了该原则，即 Producer: <? extends T>; Consumer: <? super T>



