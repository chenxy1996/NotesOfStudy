package javaConcurrencyInPractice.syncCacheTest;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class MainTest {
    public static final int NTHREAD = 100;

    // 随机数的上限, 最后的随机整型生成产生的区间是 [0, UPPER_LIMIT)
    public static final int UPPER_LIMIT = 50;

    // 每个线程执行的循环的次数
    public static final int LOOP_TIME = 100;

    public static CountDownLatch latch = new CountDownLatch(NTHREAD);

    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();

        Processor<Integer, String> p = new DataHandler();

        CacheHolder<Integer, String> cache = new CacheHolder<>(p);

        for (int i = 0; i < NTHREAD; i++) {
            int currentThread = i;

            Runnable r = () -> {
                for (int j = 0; j < LOOP_TIME; j++) {
                    Integer num = Integer.valueOf(rand.nextInt(UPPER_LIMIT));
                    String s = "";

                    long start = System.currentTimeMillis();
                    try {
                        s = cache.process(num);
                    } catch (RuntimeException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    // 这里面有一个 bug 如果该线程被阻塞或者
                    // 等待中那么也会被算到这部分时间中
                    long end = System.currentTimeMillis();

                    // 打印执行所花的时间和结果
                    System.out.println(currentThread + "---  " + "LoopTime: " + j + " " + (end - start) + " num: " + num + " res: " + " " + s);
                }

                latch.countDown();
            };

            // 创建线程
            Thread t = new Thread(r);
            t.start();
        }

        latch.await();
        System.out.println("---------THE-------------------------END---------");
    }
}
