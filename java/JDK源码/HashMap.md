[toc]

# Java 中 HashMap<K, V> 源码详解 By 陈翔宇

## 0 HashMap 大致的实现结构

首先，介绍两种实现（implementations），被运用于不同语言的哈希表数据结构中。

- 开放地址法
- “链条”法

了解 Python 的同学知道 Python 里面有一个 dict 数据类型，其底层 C 语言实现时就用了开放地址法来解决 hashCode 的碰撞问题。具体的算法这里不深入讨论。

Java 与之不同采用了链条法。所谓链条法，就是在不同的 key 碰撞的时候，通过链表这个数据结构来组织数据的存储方式。每个 table 数组所储存的是这个链表的头节点。**在 jdk1.8 后，如果一条链中的节点数量大于或等于8的时候，就会将单链表转换为红黑树，从而提高查找效率。如果红黑树的节点数量小于等于6之后，则又会转换为单链表。**

## 1 对 HashMap 中底层的 table 数组的大小要求是2的幂次方的算法实现

一般来说，table 数组的长度为素数时，造成的碰撞会少些。为什么这里规定是2的幂次方，可以继续往下看。

构造函数 `HashMap(int initialCapacity, float loadFactor)`

```java
public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity); // 看这一行
    }
```

`threshold` 的源码注释为: <u>The next size value at which to resize (capacity * load factor).</u>

调用 `tableSizeFor(int)` 静态方法得到大于 `initialCapacity` 最小的 2 的幂次方.（例如11，其最小2的幂就是16）

```java
// tableSizeFor

/**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1); // 1
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1; // 2
    }
```

这里有个非常巧妙的处理: `cap - 1`.

**如果一个数为2的幂次方，那么其二进制表示肯定为 00..0010...00的形式 (构造函数要求大于等于0)，总位置数为32（int）。如果不为且不等于0，那么肯定在1的右边肯定会有1出现，这时大于其最近的2的幂肯定是其最高位1向左移动一位，然后右边全部为0，以11为例，其二进制表示为1011,那么离他最近的是10000。`cap - 1`是为了把数原本就为2的幂次方这种情况按照和上述一样处理。对于不为2的幂次方的数，减去1的操作不会使其最高位1右移动，对结果无影响。对于2的幂次方数，例如16，二进制为10000，减去1为1111,按照上述处理也能得到结果为10000。这样在算法的是实现中不需要分类讨论。具体做法为原来的数字减去1，然后把最左边的1的右边各个位置都为设为1，最后再加上1.**

`Integer.numberOfLeadingZeros(int i)` 得到一个数其二进制表示时其最左边的0的个数。例如11的二进制表示是000...0001101, `Integer.numberOfLeadingZeros(11)` 就是28。

`-1 >>> 28` 中 `>>>` 无符号向右移动，在此得到 000...0001111。

下面一句 `return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;` 首先判断移位后是否小于0（-1向右移32位置还是-1）,如果不小于0小于最大容量,就加1，000...0001111加1等于10000(16)。

如果cap为0或1，最后结果是0.

**补充：这里还有个算法可以让  `cap - 1` 的最左边的右边所有未都变为1：**

```java
int n = cap - 1;
n |= n >>> 1;
n |= n >>> 2;
n |= n >>> 4;
n |= n >>> 8;
n |= n >>> 16;
return n < 0 ? 1 : n + 1; // n < 0 是
// 这里没有考虑容量最大值的情况
```



## 2 hash() 方法与 resize() 方法中的巧妙位运算

这节我们探讨出现在这个两个方法中的2进制位运算。位运算的速度是远远超过普通乘除的，HashMap 的设计者使用了多处位运算及其性质来提高对 HashMap 的操作速度。

### 2.1 hash()

一般来说对一个实例调用其 `hashCode()` 方法，返回的类型是 `int` 类型。

```java
/**
     * Computes key.hashCode() and spreads (XORs) higher bits of hash
     * to lower.  Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

**<u>注释说，由于 power-of-two masking, sets of hashes that vary only in bits above the current mask will always collide. 翻译过来就是由于采取了用2的幂次方数作为掩码的方式，如果不同的哈希码如果在掩码不掩盖的区域相同，在掩码掩盖的区域不同，那么它们会碰撞.</u>**

举个例子:

<u>假设8位整数 hashcode1 为5, 那么其二进制表示为00000101。  hashcode2 为 21 其二进制表示为00010101。 此时 table 数组的长度为16（2的4次幂），其二进制表示为00010000. 16 -1 = 15,</u> 

<u>要得到各自安放在 table 数组的哪个位置上，一般来说执行操作：`hashcode % table_size` 得到余数。但由于 table 的大小为2次幂，所以将 table 的大小减1正好得到一个掩码，再用该掩码来和 hashcode 执行 `&` 即可。即（**hashcode & (table_size - 1) 的操作就相当于 hashcode % table_size**）</u>

<u>掩码为 00001111。hashcode1 和 hashcode2 是不同的两个哈希码，在掩码00001111掩盖的区域（前4位）它们是不同的，一个是0000, 一个是0001。在掩码没有掩盖的区域（后四位）,它们相同都为0101。这就会使得两个哈希码的高4位没有用上，只用上了低4位从而造成碰撞。</u>

由于这种机制，注释上说：the highest bits that would otherwise never be used in index calculations because of table bounds. 高位上的数字将由于 table 数组的长度限制永远不会被使用。一般来说 hashcode 是分布较好的，上面的机制却无法利用到这点。

<u>`hash()` 静态方法为了改善这点，利用到其高位的数字，使用了最简单的方法（in the cheapest possible way），将哈希码的最高16位与最低16位做异或(^)运算，在一定程度上保留高位。</u>

<u>`(h = key.hashCode()) ^ (h >>> 16)`。（异或(^)是将较于或(|)和且(&)，让数字分布相对均匀的运算，这个到底如何证明，作者并不了解）</u>

### 2.2 resize() 方法

```java
/**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * @return the table
     */
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead; // 看这一步
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

这一方法的代码量特别长，在这里先不解释每一部分是什么意思。而是先看涉及到位运算的部分。

看最后的 `do` 后面的代码：

```java
do {
    next = e.next;
    if ((e.hash & oldCap) == 0) {
        if (loTail == null)
            loHead = e;
        else
            loTail.next = e;
        loTail = e;
    }
    else {
        if (hiTail == null)
            hiHead = e;
        else
            hiTail.next = e;
        hiTail = e;
    }
} while ((e = next) != null);
if (loTail != null) {
    loTail.next = null;
    newTab[j] = loHead;
}
if (hiTail != null) {
    hiTail.next = null;
    newTab[j + oldCap] = hiHead; // 看这一步
}
```

这一部分是为了将单链表中的各个节点重新放置在新 table 数组的合适位置中(bin)。为了解释这部分代码的意思，我们以一个具体的例子来说明。

令 table_size = 16 二进制为 00010000; hashcode1 = 5 二进制为00000101; hashcode2 = 21 二进制为00010101; hashcode3 = 37 二进制为 00100101。

根据本文之前介绍的算法 hashcode & (table_size - 1), 这三个哈希码经过计算后都为0101,十进制为5。所代表的键值对都会被储存在 table[5] 中按照插入的先后顺序形成一个单链表.

```java
HashMap-----table--------linkedSingleList--------------------------------------

​			0: [------]

​			1: [------]

​			2: [------]

​			...

​			5: [---ref---] => node1(hashcode:5) => node2(hashcode:21) => node3(hashcode:37) => null

​			...

​			15:[------]
```

**如果此时执行了 `resize()` 方法，使得 new_table_size 变为原来的两倍，32, 其二进制表示相当于 table_size 的二进制表示向左边移动一位，减去1后相应的新掩码11111相当于在table_size 减去1后得到的掩码1111的最左边的1的左边再加上1。问题就转换为：已经知道了在旧掩码1111时进行&的结果，如何求得11111进行&运算的结果. 显然，我们只需知道 hashcode 从右边数第5位（掩码新增的一位）上的数字是1还是0即可，因为其左边的位会被掩盖，右边的位运算的结果已经通过和旧掩码运算得到。**

**那么如何得到新增的位所对应哈希码位上的数字是0还是1呢。可以通过 hashcode & table_size 来的得到，这里的 table_size 是未扩大前的 table 的长度，这里是16。**

**如果该位是0：其在新 table 中所储存的索引和在旧 table 中所储存的索引是相同的。**

**如果该位是1：那么就相当于在旧 table 中的索引加上旧 table 的长度从而得到新的索引。**

**`newTab[j + oldCap] = hiHead;`**

**`do` 代码块中就是用来拆分旧 table  中所储存的单链表，将其拆封为两条单链表。一条链表中所有的节点的哈希值新增位上为0，另外一条则为1.（hiHead 就是这条单链表的头节点）**

