package package1;

import java.util.ArrayList;

public class SupClass {
    public static Integer x = new Integer(66);
    public static final ArrayList<Integer> a = new ArrayList<>();
    public int y = 7;
    {
        System.out.println("Instance initialized!");
    }

    static {
        System.out.println("Class obj initialized!");
    }

    public SupClass(int y) {
        this.y = y;
    }

    public static void main(String[] args) {
        SupClass a = new SupClass(5);
        System.out.println(a.y);
        System.out.println(A.num);
        System.out.println(A.num1);
    }
}

class A {
    public static final int num = 10;
    public static final Integer num1 = new Integer(10);

    static {
        System.out.println("Class A initialized!");
    }
}