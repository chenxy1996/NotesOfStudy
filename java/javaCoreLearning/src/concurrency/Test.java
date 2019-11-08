package concurrency;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {
    private int value = 0;
    static int a = 3;
    static int b;
    static int c;

    public void setValue(int value) { this.value = value; };

    public int getValue() { return value; };

    static {
        System.out.println(a);
        System.out.println(b);
        b = 5;
        System.out.println(c);
    }

    public static int getNumber() {
        try {
            int a = 9;
            int b = 7;
            throw new IllegalArgumentException("test");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            return b;
        } finally {
            System.out.println();
        }
    }

    public static void main(String[] args) {
    }
}
