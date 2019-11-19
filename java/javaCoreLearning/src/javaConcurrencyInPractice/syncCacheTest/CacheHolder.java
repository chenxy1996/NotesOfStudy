package javaConcurrencyInPractice.syncCacheTest;

import java.util.concurrent.*;

public class CacheHolder<U, T> {
    private final Processor<U, T> processor;
    private final ConcurrentHashMap<U, FutureTask<T>> cache = new ConcurrentHashMap<>();

    public CacheHolder(Processor<U, T> p) {
        this.processor = p;
    }

    public T getCache(final U arg) throws ExecutionException, InterruptedException {
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
}
