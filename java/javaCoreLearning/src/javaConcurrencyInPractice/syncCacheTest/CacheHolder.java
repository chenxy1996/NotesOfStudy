package javaConcurrencyInPractice.syncCacheTest;

import java.util.Dictionary;
import java.util.concurrent.*;

public class CacheHolder<U, T> implements Processor<U, T>{
    private final Processor<U, T> processor;
    private final ConcurrentHashMap<U, FutureTask<T>> cache = new ConcurrentHashMap<>();

    public CacheHolder(Processor<U, T> p) {
        this.processor = p;
    }

    @Override
    public T process(final U arg) throws InterruptedException, ExecutionException {
        while (true) {
            FutureTask<T> f = cache.get(arg);
            if (f == null) {
                Callable<T> c = () -> processor.process(arg);
                FutureTask<T> newf = new FutureTask<>(c);
                // 利用了 ConcurrentHashMap 自带的同步方法
                f = cache.putIfAbsent(arg, newf);
                // 如果执行 putIfAbsent 后发现返回是 null, 说明此时
                // f 还没有执行 run 方法
                if (f == null) {
                    f = newf;
                    f.run();
                }
            }

            try {
                // 如果在 f.run() 中执行出现异常
                // 在 调用 f.get() 时候会报错
                // 假如另一个线程在执行 f.run() 方法
                // 那么 f.get() 会阻塞另外一个线程执行完
                // f.run().
                return f.get();
            } catch (ExecutionException e) {
                throw e;
            } catch (CancellationException e) {
                cache.remove(arg, f);
            }
        }
    }

    public static void main(String[] args){
        // 这是一个测试，
        // 测试线程中执行 Thread.currentTimeMillis() 或者
        // Thread.nanoTime() 方法
        // 的时候, 或不会算上线程被阻塞或者被等待的时间
        Semaphore semaphore = new Semaphore(0);

        Runnable r1 = () -> {
            try {
                Thread.sleep(2000);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable r2 = () -> {
            try {
                long start = System.currentTimeMillis();
                semaphore.acquire();
                long end = System.currentTimeMillis();
                System.out.println(end - start);

                start = System.currentTimeMillis();
                end = System.currentTimeMillis();
                System.out.println(end - start);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t2.start();
        t1.start();

        Dictionary
    }
}
