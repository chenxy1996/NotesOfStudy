package concurrency;

import org.w3c.dom.ls.LSOutput;

public class SuperClass {
    protected String motto = "This is a protected string!";

    public static void main(String[] args) {
        SuperClass a = new SuperClass();
        System.out.println(a.motto);
    }
}
