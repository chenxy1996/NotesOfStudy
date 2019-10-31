package concurrency;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {
    private int value = 0;

    public void setValue(int value) { this.value = value; };

    public int getValue() { return value; };

    public static void main(String[] args) {
        Test t = new Test();
        new Thread(() -> {
            System.out.println(t.getValue());
            t.setValue(10);
        }).start();

        new Thread(() -> {
            System.out.println(t.getValue());
            t.setValue(9);
        }).start();
    }
}
