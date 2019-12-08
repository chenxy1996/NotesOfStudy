package javaConcurrencyInPractice.syncCacheTest;

public class Child extends Parent {
    static void say() {
        System.out.println("This is Child Class!");
    }
    public static void main(String[] args) {
        Parent.say();
        say();
    }
}
