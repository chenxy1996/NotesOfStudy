package test;

import jdk.swing.interop.SwingInterOpUtils;

public class Test {
    public static int i = 3;
    public int j;
    public int k = 8;

    public Test() {
        System.out.println("Constructor Funcation starts!");
        System.out.println("k = " + k + " j = " + j);
        i++;
        j++;
        k++;
    }

//    {
//        System.out.println("instance initializer starts!");
//        j = 7;
//        k++;
//    }

    public static void main(String[] args) {
//        Test aTest = new Test();
//        System.out.println(i);
//        System.out.println(aTest.j);
//        System.out.println(aTest.k);
        int[] a = {0};

        for (int i = 0; i < 5; i++) {
            Runnable r = () -> {
                System.out.println(a[0]);
                a[0] += 1;
            };
            r.run();
        }
    }
}

class A {
    static String name = "lele";
    static {
        System.out.println("Class A is initialized!!!!!");
    }

}
