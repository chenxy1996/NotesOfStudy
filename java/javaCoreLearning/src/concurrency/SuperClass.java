package concurrency;

import org.w3c.dom.ls.LSOutput;

public class SuperClass {
    protected String motto = "This is a protected string!";
    public int a;
    public SuperClass() {
        System.out.println("Super");
        a = 7;
    }

    public static void main(String[] args) {
        SuperClass a = new SuperClass();
        System.out.println(a.motto);
    }
}
