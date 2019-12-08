package javaConcurrencyInPractice.syncCacheTest;

public interface ParentInterface {
    default void say() {
        System.out.println("This is Parent Interface!");
    }
}
