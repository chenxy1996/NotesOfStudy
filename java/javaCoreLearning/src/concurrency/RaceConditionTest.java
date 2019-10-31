package concurrency;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RaceConditionTest {
    static AtomicLong count = new AtomicLong(0);

//    static long count = 0l;
    public static final ThreadLocal<Long> threadLocalCount =
                ThreadLocal.withInitial(() -> 0l);

    public static void main(String[] args) throws InterruptedException {
//        ArrayList<Thread> s = new ArrayList<>();

        /*
        记住这里必须要用线程安全的 HashMap, 因为如果不是线程安全的，会莫名其妙出现
        一些不好的 bug .
         */
        ConcurrentHashMap<Thread, Integer> s = new ConcurrentHashMap<>();
        for (int i = 0; i < 10; i++) {
            int threadNum = i;

            Runnable r = () -> {
                Long localCount = threadLocalCount.get();
                for (long j = 0; j < 10000; j++) {
                    count.getAndIncrement();
                    localCount += 1;
//                    count++;
                }
                s.remove(Thread.currentThread());
                System.out.println("Thread: " + threadNum + "; count: " + count + "; local count: " + localCount + "; " + s.size() + "; " + s.toString());
            };

            Thread t = new Thread(r);
            s.put(t, 0);
            t.start();
        }

//        for (Thread t : s) {
//            t.join();
//        }
        while (s.size() != 0) {
            System.out.println(s.size() + "; " + s.toString());
            Thread.sleep(1000);
        }
//        System.out.println(s.size() + "; " + s.toString());
        System.out.println(count);
    }
}
