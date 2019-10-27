package concurrency;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {
    public final String name;

    public int[] nums = new int[100];
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();

    public Test(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Test aTest = new Test("chen");
        System.out.println(Arrays.toString(aTest.nums));
        System.out.println(aTest.name);
    }
}
