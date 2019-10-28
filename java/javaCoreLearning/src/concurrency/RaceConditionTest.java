package concurrency;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class RaceConditionTest {
    static AtomicLong count = new AtomicLong(0);
    public static final ThreadLocal<Long> threadLocalCount =
                ThreadLocal.withInitial(() -> 0l);

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Thread> s = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int threadNum = i;

            Runnable r = () -> {
                Long localCount = threadLocalCount.get();
                for (long j = 0; j < 10000000; j++) {
                    count.getAndIncrement();
                    localCount += 1;
                }
                System.out.println("Thread: " + threadNum + "; count: " + count + "; local count: " + localCount);
            };

            Thread t = new Thread(r);
            s.add(t);
            t.start();
        }

        for (Thread t : s) {
            t.join();
        }

        System.out.println(count);
    }
}
