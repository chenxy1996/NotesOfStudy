package concurrency;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreadTest {
    static int count = 1000000000;
    static int[] nums = new int[count];

    public static void incrementByMultithreading() throws InterruptedException {
        int threadCounts = 10;
        int step = count / threadCounts;

        /*
        创建线程
         */
        for (int i = 0; i < threadCounts; i++) {
            int finalI = i;

            Runnable r = () -> {
                for (int j = finalI * step; j < (finalI + 1) * step; j++) {
                    nums[j] += 1;
                }
                System.out.println((finalI + 1) + " thread starts.");
            };

            Thread t = new Thread(r);
            t.start();
            t.join();
        }
    }
    
    public static void increment() {
        for (int i = 0; i < count; i++) {
            nums[i] += 1;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            nums[i] = i;
        }

        long t1 = System.currentTimeMillis();
        incrementByMultithreading();
        System.out.println("MultiThreads: " + (System.currentTimeMillis() - t1));

        for (int i = 0; i < count; i++) {
            nums[i] = i;
        }

        long t2 = System.currentTimeMillis();
        increment();
        System.out.println("Normal: " + (System.currentTimeMillis() - t2));
    }
}
