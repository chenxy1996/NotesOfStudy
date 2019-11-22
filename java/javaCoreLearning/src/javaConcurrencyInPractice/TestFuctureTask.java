package javaConcurrencyInPractice;

import java.util.HashMap;
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
//        FutureTask<String> f = new FutureTask<>(new MyCallable<String>());
//        Thread t = new Thread(f);
//        t.start();
//        f.run();
//        System.out.println(f.get());
//        System.out.println("chenxiangyu");
        int a = 11;
        System.out.println(-1 >>> 32);

    }
}
