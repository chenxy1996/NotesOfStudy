package javaConcurrencyInPractice.syncCacheTest;

public interface ChildInterface extends ParentInterface{
    @Override
    default void say() {
        System.out.println("This is Child Interface!");
    }

    static void main(String... args) {
        ParentInterface test = new ChildInterface() {};
        ParentInterface parent = new ParentInterface() {};

        test.say();
        parent.say();
        Parent.say();
    }
}
