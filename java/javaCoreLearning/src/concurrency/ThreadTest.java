package concurrency;

import javaConcurrencyInPractice.TestFuctureTask;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    static int count = 1000000000;
    static int[] nums = new int[count];

    static {
        for (int i = 0; i < count; i++) {
            nums[i] = i;
        }
    }


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
        /*
        创建一个新的线程
         */
        Runnable r1 = () -> {
            for (int i = 0; i < 50 ; i++) {
                System.out.println("r1: " + i);
                try {
                    Thread.sleep(300l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable r2 = () -> {
            for (int i = 0; i < 50 ; i++) {
                System.out.println("r2: " + i);
                try {
                    Thread.sleep(300l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }
}
