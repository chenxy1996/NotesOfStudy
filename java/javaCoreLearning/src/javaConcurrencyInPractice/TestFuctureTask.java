package javaConcurrencyInPractice;

import java.util.concurrent.*;

public class TestFuctureTask {
    static class MyCallable<T> implements Callable {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            return "This is a Test";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> f = new FutureTask<>(new MyCallable<String>());
        Thread t = new Thread(f);
        t.start();
        System.out.println(f.get(5, TimeUnit.SECONDS));
        System.out.println("chenxiangyu");
    }
}
