public class MyHashMap {
    /*static field-----------------------------------------------------*/
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // 1 * (2^30) int 中的最大值
    private static final int MAXIMUM_CAPACITY = 1 << 30;


    /*instance field---------------------------------------------------*/
    // 下一次扩大 (resize) 的时候，size 的大小
    private int threshold;
    private int size;
    private final float loadFactor;


    /*static method----------------------------------------------------*/
    /**
     * 返回不小于 i 的最小整数，该整数是2的幂次方(1, 2, 4, 8...).
     * @param i 大于等于0.
     */
    private static int newSizeFor(int i) {
        int n = -1 >>> Integer.numberOfLeadingZeros(i - 1);
        if (n < 0) {
            // 两种情况：
            // 当 i = 0 时候 i - 1 = -1
            // Integer.numberOfLeadingZeros(-1) 返回0. -1 >>> 0 = -1.
            // 当 i = 1 时候 i - 1 = 0
            // Integer.numberOfLeadingZeros(-1) 返回32. -1 >>> 32 = -1.
            return 1;
        } else {
            if (n >= MAXIMUM_CAPACITY) {
                // 当 n 不小于最大容量的时候.
                // 当 Integer.numberOfLeadingZeros(i - 1) 返回1. -1 >>> 1 = 1 << 30.
                return MAXIMUM_CAPACITY;
            }
            // 一般情况
            // 如果一开始 i = 11(10进制)
            // 此时 n = 1111(2进制)
            // 加上1后为 10000(2进制) = 16(10进制)
            return n + 1;
        }
    }


    /*constructor------------------------------------------------------*/
    public MyHashMap(int initialCapacity, int loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial Capacity: "
                                                + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("Illegal load factor: " +
                                                loadFactor);
        }
        this.loadFactor = loadFactor;
        // 取最靠近 initialCapacity 且不小于它的2的幂次方数
        this.threshold = newSizeFor(initialCapacity);
    }

    public MyHashMap(float loadFactor) {
        this.loadFactor = loadFactor;
    }

    public static void main(String[] args) {
        int i = 12;
        System.out.println(newSizeFor(i));
    }
}
