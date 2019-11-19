package javaConcurrencyInPractice.syncCacheTest;

public interface Processor<U, T> {
    T process(U u);
}
