[toc]

# 写在前面

名字来源于日剧 "逃避可耻但有用" （" 逃げるは恥だが役に立つ "）。

我原来是不想专门把平常看到的不熟悉的 java 知识给记在一个单独的类似笔记本的地方的，因为我太弱了，不熟悉的地方是在是太多了，一个一个都记下来得多累。

但是今晚（20191218）想想还是得有这么个东西：一来是自己写笔记的过程也相当于是个梳理的过程，能加深对不熟悉部分的理解程度；二来是方便以后的复习。

有些人看过的东西能永远记住，并且能灵活运用，可惜的是我不是那类人，还是有些不甘心（哈哈）。所以就将这份笔记的名称暂且设置为 “可耻但有用”。

# 正文

## 创建子类的同时不会创建父类

在 `java language specification` 中指出了子类调用构造函数时会先调用父类的构造函数，如果父类还有其父类，则再调用父类的父类的构造函数，依次递归 `recursion` 。

但是**这不代表在内存中创建子类对象的时候会同时创建父类对象。**

要了解这个问题到底是怎么回事，可以先看一下 《深入理解 Java 虚拟机》(Undestanding java virtual machine) 然后，如果还有兴趣了解，可以看 java 语言规范 和最新的 java 虚拟机规范。

如果还想继续了解，就需要自己在网上找相关内容，以及看看 openJDK 的源码。

首先我们来看一个对象在内存中的具体分布情况：

一个对象具体是由两个部分组成的：对象头 (Object header) 和实例的字段 Field 以及对齐填充 ( padding ), 这个**字段区域不止包括当前类的字段，还包括父类的字段，如果父类也有自己的父类，那么以此类推，都会写在这个部分当中，当然是否能够访问某个字段，要看有没有有权限，也就是 public private default protected 这些关键字的情况**。关于对象头，可以分为两个：一个是普通的对象，一个是数组对象。

对于普通对象，一般来说对象头可以分为三个个部分：标记信息、类行指针( Klass )。标记信息一般是32字节或64(没有压缩的情况)，这些字节中储存了一些关于这个类的基本信息：hashcode、锁状态标志、偏向线程ID、GC 分代年龄等等（可以看 《深入理解 Java 虚拟机》的相关部分）；类型指针指向方法区（JDK8 以前叫做方法区，也有时候被称作永久区，因为 GC 很少清理该区域的内存数据，但是在 JDK8 后 java 虚拟机规将该区域称之为元数据）中这个实例所属的类信息（class），这里我思考了一下，**`instanceof` 操作符是否就是利用了这个类型指针来判断的**；最后一部分是 padding。

如果是数组对象，那么除了上述部分后，还有一个记录该对象被创建时里面所储存的元素的数量。

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

**以 `LinkedList` 源码中的 `ListItr` 内部类为例，他有个内部成员变量 `lastReturned`,  专门用来记录 `next()` 或是 `previous()`  方法返回的值。 如果其值为 `null` 那么将会抛出 `IlleagalStateException` 错误。`remove` 在最后会把 `lastReturned` 重新设置为 `null`（仅 remove 方法会这样做，set 方法不会将其重新设置为 `null`）。add 方法也会将其设置为 `null`**

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

该方法是一个 helpler 方法，在以前的 jdk 中是没有单独分裂出来的。其注释:

```JAVA
/**
     * This helper method split out from add(E) to keep method
     * bytecode size under 35 (the -XX:MaxInlineSize default value),
     * which helps when add(E) is called in a C1-compiled loop.
     */
```

和 JIT 的 c1 编译器优化有关。

## 允许存储 null 的 Collection 和 Map

注: 键 key 可以为 null，但是值不能为 null。

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

### 延伸：接口引用是否可行？：`Interface::instanceMethod` 以及多态性

上面三种方法引用方式，其中 instance::instanceMethod 和 class:staticMethod 的调用的具体方法在调用的时候就已经确定了。看最后一种方法引用：class::instanceMethod，这里有两个问题：1. 其是否会有多态性。2. 将 class 换成 interface 是否可行.

先看第一个问题：

```java
public class InterfaceReferenceTest {
    interface Greeting {
        void sayHello(Object obj);
    }

    static class Dog implements Greeting {
        @Override
        public void sayHello(Object o) {
            System.out.println("wang! " + o);
        }
    }

    static class Cat implements Greeting {
        @Override
        public void sayHello(Object o) {
            System.out.println("miao! " + o);
        }
    }

    public static void main(String[] args) {
        Cat aCat = new Cat();
        Dog aDog = new Dog();
        BiConsumer<Greeting, Object> binaryFunction = Greeting::sayHello;
        // miao! chen
        binaryFunction.accept(aCat, "chen");
        // wang! chen
        binaryFunction.accept(aDog, "chen");
    }
}
```

可以使用，说明是在虚拟机运行中使用了 `invokeinterface` 指令.

第二个问题,:

```java
public class polymorphicTest {
    static class SupClass {
        public void saySomething() {
            System.out.println("I'm SupClass!");
        }
    }

    static class SubClass extends SupClass {
        @Override
        public void  saySomething() {
            System.out.println("I'm SubClass");
        }
    }

    public static void main(String[] args) {
        Consumer<SupClass> cons = SupClass::saySomething;
        cons.accept(new SupClass()); // I'm SupClass!
        cons.accept(new SubClass()); // I'm SubClass
    }
}
```

说明是可以使用的。

## Generic 泛型

关于泛型，说实话掌握得也不是很好，但是碰到一点需要记住的就记下来吧，以后经常翻过来看看。

### PECS 原则

 *effective java* 一书中给出了该原则，即 Producer: <? extends T>; Consumer: <? super T>

### （非常值得看）关于泛型的强制类型转换(自己摸索着总结的)

**有一个最基本的原则是（以 `List` 举例）：假设类 `B` 继承了 类 `A`。**

**那么 `List<B>` 是无法转强制换为 `List<A>` 的。**

```java
List<B> list = new ArrayList<>();
List<A> newList = (List<A>) list; // 编译器会报错
```

就算把 `A` 换成 `Object` 也不行。

```java
List<Object> newList = (List<Object>) list; // 编译器报错
```

但是我们可以直接"骗过"编译器：

```java
List newList = list; //不会报错，可以这样；
```

如上面所示，此时编译器通过类型消除。使得 `newList` 中可以存储的是 `Object`. 

```java
newList.add(new Integer(1));
```

如果接下来通过 `newList` 来获取该元素 `get()`, 那么获取的元素的类型都将是 `Object`.

但是如果通过 `list` 来获取该元素 `B b = list.get(1)`，将会出现`ClassCastException` 异常。

因为骗过编译器后, 编译器会将放回的 `Integer` 对象强制转换为  `B` 类型，所以会出现该异常。

### 泛型类向下强制转换

对于上面如果很想将 `List<B> ` 强制转换为 `List<A>`. 可以用下面的方法：

```java
List<B> list = new ArrayList<>();
List<?> helper = list; // List hlper = list 也可以
List<A> newList = (List<A>) helper;  // 会出现警告，但是编译是可以通过的
```

上面第二步也可以写成以下各种方式，都可以：

```java
List<? extends Employee> helper = list;
List<? extends Object> helper = list;
List<? extends Manager> helper = list;
List<? extends Executor> helper = list;

List<? super Executor> helper = list;

List helper = list;
```

都会出现警告，但是都可以编译成功。

可以得出结论：

<u>**（1）只要前一个泛型类型是个范围且包括后面要强制转换的泛型的类型 (或者是最原始的类型即什么都没有 `List` ), 就可以直接转换，在该步编译器和执行期都不会报错。**</u>

具体的例子就不放了，上面有。

<u>**（2）把一个静态类型 (static type) 为泛型类且其泛型类型是个范围(原始类型也可以，如 `List` ) 转换成具体泛型类型的时候编译器是不会报错的，只会出现警告。**</u>

这就非常容易导致出错，恶意代码的注入。

### 通配符

除了上面的 PECS 原则外, 还有一些可以说的，关于通配符 `?`.

```java
class Employee {
   	private final String name;
    private final int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public int getRank() { return rank; }

    @Override
    public String toString() {
        return id + " " + name;
    }
}

class Manager extends Empoyee {
    public Manager(String name, int id) {
        super(name, id);
    }
}

class Executor extends Manager {
    public Executor(String name, int id) {
        super(name, id);
    }
}

class WildCardTest {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("chen", 1));
        List<?> aList = list;
        Object obj = aList.get(0); // aList 取出的只能为 Object 类型
        aList.add(new Employee("lele", 2)); // 错误, aList 无法添加新的元素
    }
}
```

## 映射视图

之所以出现映射视图的原因是：**集合框架不认为映射本身是一个集合**。

不过，可以得到映射的视图（view)，也就是实现了 Collection 接口或者某个子接口的对象。

- 键集
- 值集合（不是一个集）
- 条目集

为什么键集是集（Set）而值集合是集合（Collection）?

**因为 Map 中的 key 都是不重复的，而值是可以重复的。**

注意：在键集和条目集中使用 `add` 都会抛出 `UnsupportedException` .

## 其他视图有关的注意点

### `Arrays.asList(T... args)` 返回的是是一个 `ArrayList` 视图（此 `ArrayList` 非彼 `ArrayList`）

注意：这里的 `ArrayList` 与经常使用的 ArrayList 是不同的，它是 Arrays 类中的一个私有静态内部类，直接继承了 AbstractList 类，并没有重写 remove 方法，在 AbstractList 中：remove 方法的调用会直接抛出一个 UnsupportedOperationException. 所以对该方法的返回对象使用 remove 方法也同样会抛出相同的异常。也就是说**任何改变原来数组（args）长度的操作都会报错。**

```java
public E remove(int index) {
        throw new UnsupportedOperationException();
}

public E set(int index, E element) {
        throw new UnsupportedOperationException();
}
```

### 	常与 addAll 结合使用的 Colletions.nCopies()

```java
public static <T> List<T> nCopies(int n, T o) {
        if (n < 0)
            throw new IllegalArgumentException("List length = " + n);
        return new CopiesList<>(n, o);
}

private static class CopiesList<E> extends AbstractList<E> implements RandomAccess, Serializable {
    final int n;
    final E element;
    
    CopiesList(int n, E e) {
        assert n >= 0;
        this.n = n;
        element = e;
    }
    
    public Object[] toArray() {
        final Object[] a = new Object[n];
        if (element != null)
            Arrays.fill(a, 0, n, element);
        return a;
    }
}
```

结合上面的源码知道：Collections.nCopies() 方法仅仅是创建了一个实行了 List 接口的 CopiesList 类，这个类中用 n 和 element 记录了相关值，并不是所想象的那样包含有一个数组（等）记录了 n 个 element. 这是一个非常节省内存的方法。

当用 List.addAll() 方法时候，以 ArrayList 中的 addAll 方法举例：

```java
public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        modCount++;
        int numNew = a.length;
        if (numNew == 0)
            return false;
        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size))
            elementData = grow(s + numNew);
        System.arraycopy(a, 0, elementData, s, numNew);
        size = s + numNew;
        return true;
    }
```

首先会调用 c 的 toArray 方法，而 CopiesList 的 toArray 方法这时候会创建一个数组并填充后返回。

### Collections.singleton(Objects) 静态方法返回一个实现了 Set 接口的视图对象，该视图对象不可修改，就相当于一个容器。

## 关于 Collection 框架中的相关类和排序（只针对 List 类， 除了 SortedSet 和 NavigableSet ）

我们晓得 Collection 框架中主要有两大类：Set 和 List ，其中 Set 是无序的 （除了 SortedSet 和 NavigableSet ），所以<u>对其来说排序是没有意义的，排序只是针对 List 。</u>

对于 List 类的排序，有一个静态方法：`Collections.sort(List<T> list)` 和 `Collections.sort(List<T> list, Comparator<? super T> c)` 两个方法。

```java
public static <T extends Comparable<? super T>> void sort(List<T> list) {
        list.sort(null);
}

public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
}
```

这连个方法都调用了 List 的实例方法 `List.sort(Comparator<? super E> c)`

```java
default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }
```

从上面的源码可以看出：对 List 的排序是将其转换为数组，然后再调用底层的 `Arrays.sort(T[] a, Comparator<? super T> c)` 静态方法的。

### 集合类库中使用的排序算法是归并排序，不是快速排序（快排），是出于“稳定性的考虑”。

这点在 《java 核心技术》书中有提及：**稳定指的是不需要交换相同的元素。**

假设有一个已经按照姓名排列的员工列表。现在要按照工资排序。如果两个雇员的工资相等，则将会保留按照名字的排列顺序。换言之，排序的结果将会产生这个么一个结果：首先按照工资排序，再按照姓名排序。

## Array, Set, List 互相转换

- Array => List:

```java
String[] strings = new String[] {"chen", "lele", "ahuang"};
List<String> list = Arrays.asList(strings);
```

- List => Array:

```java
List<String> list = new ArrayList<>();
list.add("chen");
list.add("lele");
list.add("ahuang");

Object[] objects = list.toArray();
String[] objects = list.toArray(new String[0]);
String[] strings = list.toArray(String[]::new);
```

上面的 `toArray` 是 Collection 接口中要求实现的三个方法，在 ArrayList 类中它们是这样实现的：

```java
public Object[] toArray() {
    return Arrays.copyOf(elementData, size);
}

// Collection 集合中的默认接口
default <T> T[] toArray(IntFunction<T[]> generator) {
    return toArray(generator.apply(0));
}

@SuppressWarnings("unchecked")
public <T> T[] toArray(T[] a) {
    if (a.length < size)
        // Make a new array of a's runtime type, but my contents:
        return (T[]) Arrays.copyOf(elementData, size, a.getClass());
    System.arraycopy(elementData, 0, a, 0, size);
    if (a.length > size)
        a[size] = null;
    return a;
}
```

- Set => Array

和上面的 List => Array 一样。

- Array => Set

```java
List<String> list = new ArrayList<>();
list.add("chen");
list.add("lele");
list.add("ahuang");

Set<String> set = new HashSet<>(list);
```

- Set => Array

```java
Set<String> set = new HashSet<>();
set.add("chen");
set.add("lele");

List<String> list = new ArrayList<>(list);
```

## jdk7 及以后字符串常量池的有关部分（`intern()`）

注意：关于字符串后面肯定会出一个大的笔记专题。

字符串常量池在 JDK7 之前是在方法区 (Method Area) 的，但是在JDK7之后，被移动到了堆 (Heap) 中, 大概实字符串对象太占用地方了。并且 `intern()` 方法也有一点变化。

这部分内容网上实在是太多了，什么关于面试的啊，一大堆，这次写这个笔记正好我也整理一下，自己重新写一下，也算是回顾。

首先，有一个大的前提：

```java
String aString = "chenxiangyu";
```

上述语句从头到尾只创建了一个对象，常量池中的字符串字面量："chenxiangyu".

```java
String anotherString = new String("chenxiangyu");
```

而上述语句则创建了两个对象：一个是常量池中的 "chenxiangyu", 还有一个是堆中常量池以外地方的 `anotherString`。

知道上面的后，下面的就好说了。

看源码中 `String.intern()` 的注释：

```java
 /**
     * Returns a canonical representation for the string object.
     * <p>
     * A pool of strings, initially empty, is maintained privately by the
     * class {@code String}.
     * <p>
     * When the intern method is invoked, if the pool already contains a
     * string equal to this {@code String} object as determined by
     * the {@link #equals(Object)} method, then the string from the pool is
     * returned. Otherwise, this {@code String} object is added to the
     * pool and a reference to this {@code String} object is returned.
     * <p>
     * It follows that for any two strings {@code s} and {@code t},
     * {@code s.intern() == t.intern()} is {@code true}
     * if and only if {@code s.equals(t)} is {@code true}.
     * <p>
     * All literal strings and string-valued constant expressions are
     * interned. String literals are defined in section 3.10.5 of the
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * @return  a string that has the same contents as this string, but is
     *          guaranteed to be from a pool of unique strings.
     * @jls 3.10.5 String Literals
     */
    public native String intern();
```

注释说的就很明确: 如果常量池出中已经有了一个和目标字符串相等（`equals` 方法判断）的字符串对象，那么常量池中的这个已经存在的字符串将会被返回。如果没有，就会在字符串常量池中添加目标字符串对象的引用。

### 关于 new String("1") + new String("2") 用两个字符串相加的问题。

```java
String s1 = "1";
```

上述代码中会直接在堆中的字符串常量池中创建一个字符串对象 "1";

当我们接下来继续用字符串字面量初始化一个新的变量，实际上就是把上面常量池中 "1" 对象的引用返回。

```java
String s2 = "1";

System.out.println(s1 == s2); // true
```

下面看一下这个：

```java
String s3 = new String("1") + new String("2");
```

一直一来我都有这么一个理解：在上面的表达式中最后会创建一个新的字符串字面量 "12"，然后执行`String s3 = new String("12")` 这么个操作，"12" 自然也自动加入进字符串常量池当中。

实际上我的理解是错误的。真正的情况是：<u>会直接在堆中的非常量池中创建一个新的字符串对象</u>，而不会现在字符串常量池中创建。

```java
String s3 = new String("1") + new String("2");
String s4 = "12";

System.out.println(s3 == s4); // false
```

如果把上述代码改为:

```java
String s3 = (new String("1") + new String("2")).intern();
String s4 = "12";

System.out.println(s3 == s4); // true
```

推荐看一下 c 语言的字符串，看过后会对字符串如何储存会有一个更加深刻的了解。

除此之外，常用的静态方法：`String.valueOf(E)` 也是如此:

```java
String s5 = String.valueOf(1);
String s6 = "1";

System.out.println(s5 == s6); // false

String s7 = String.valueOf(1).intern();
String s8 = "1";

System.out.println(s7 == s8); // true
```

最后提一下: `new String("123")` 这种方式创建字符串对象是没有实际意义的, 其返回的相当于 "123" 的副本，JDK 源码中也在注释中说了：

Initializes a newly created String object so that it represents the same sequence of characters as the argument; in other words, the newly created string is a copy of the argument string. Unless an explicit copy of original is needed, use of this constructor is unnecessary since Strings are immutable.