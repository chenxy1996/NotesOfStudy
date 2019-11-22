[toc]

# HashMap<K, V>

## 1 对HashMap  中底层的 table 数组的大小要求是2的幂次方的算法实现

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

得到大于 `initialCapacity` 最小的 2 的幂次方.（例如11，其最小2的幂就是16）

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

**如果一个数为2的幂次方，那么其二进制表示肯定为 00..0010...00的形式 (构造函数要求大于等于0)，总位置数为32（int）。如果不为，那么肯定在1的右边肯定会有1出现，这时大于其最近的2的幂肯定是其最高位1向左移动一位，然后右边全部为0，以11为例，其二进制表示为1011,那么离他最近的是10000。`cap - 1`是为了把数原本就为2的幂次方这种情况按照和上述一样处理。对于不为2的幂次方的数，减去1的操作不会使其最高位1右移动，对结果无影响。对于2的幂次方数，例如16，二进制为10000，减去1为1111,按照上述处理也能得到结果为10000。这样在算法的是实现中不需要分类讨论。具体做法为原来的数字减去1，然后把最左边的1的右边各个位置都为设为1，最后再加上1.**

`Integer.numberOfLeadingZeros(int i)` 得到一个数其二进制表示时其最左边的0的个数。例如11的二进制表示是000...0001101, `Integer.numberOfLeadingZeros(11)` 就是28。

`-1 >>> 28` 中 `>>>` 无符号向右移动，在此得到 000...0001111。

下面一句 `return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;` 首先判断移位后是否小于0（-1向右移32位置还是-1）,如果不小于0小于最大容量,就加1，000...0001111加1等于10000(16)。

如果cap为0或1，最后结果是0.

