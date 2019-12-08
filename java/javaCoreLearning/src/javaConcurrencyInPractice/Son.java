package javaConcurrencyInPractice;

public class Son extends Father{
    String name = "son";

    @Override
    void thinking() {
        GrandFather a = new GrandFather();
        a.thinking();
    }

    public static void main(String[] args) {
        GrandFather test = new Son();
        System.out.println(test.name);
        System.out.println(((Father) test).name);
        System.out.println(((Son) test).name);
    }
}
