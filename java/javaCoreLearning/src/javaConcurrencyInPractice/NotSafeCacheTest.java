package javaConcurrencyInPractice;

import java.util.*;
import java.util.concurrent.*;

// 线程不安全
public class NotSafeCacheTest {
    private volatile Integer key = 0;
    private volatile Character value = 'a';
    public static int errorTimes = 0;

    public Character getCache(Integer key) {
        if (key == this.key) {
            return this.value;
        } else {
            return null;
        }
    }

    public void setCache(Integer key, Character value) {
        this.key = key;
        this.value = value;
    }

    public static Character calc(Integer key) {
        char c = 'a';
        char ret = (char) (key + 97);
        return ret;
    }

    public static void main(String[] args) {
        NotSafeCacheTest test = new NotSafeCacheTest();

        for (int i = 0; i < 3000; i++) {
            int threadNumber = i;
            Thread t = new Thread(() -> {
                while (true) {
                    int key = (int) Math.floor(Math.random() * 10);
                    Character value = test.getCache(key);
                    if (value == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        value = calc(key);
                        test.setCache(key, value);
                    }

                    boolean res = ((char) (key + 97)) == value;

                    if (!res) {
                        System.out.println("----------------------------------------------------------------");
                        errorTimes += 1;
                    }

                    System.out.println(threadNumber + " " + "key: " + key + " value: " + value + " " + res + " errorTimes: " + errorTimes);
                }
            });

            t.start();
        }
    }
}
