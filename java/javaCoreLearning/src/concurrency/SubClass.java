package concurrency;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SubClass extends SuperClass {
    public static String say() {
        return "hello!";
    }

    int num;

    public SubClass(int num) {
        System.out.println(this.num);
        this.num = num;
    }


    public static void main(String[] args) {
        SubClass s = new SubClass(5);
        System.out.println(s.motto);
    }
}

interface A {
    int a = 3;
}
