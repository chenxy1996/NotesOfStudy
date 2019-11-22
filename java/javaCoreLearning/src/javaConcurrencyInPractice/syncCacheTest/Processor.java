package javaConcurrencyInPractice.syncCacheTest;

import java.util.concurrent.ExecutionException;

public interface Processor<U, T> {
    T process(U u) throws InterruptedException, ExecutionException;
}
