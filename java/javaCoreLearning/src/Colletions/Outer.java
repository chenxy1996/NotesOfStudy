package Colletions;

public class Outer {
    class Inner {
        static final int num = 10;
        String name = "chenxiangyu";
    }

    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        String str3 = "lele";
        String str4 = "lele";
        System.out.println(str3 == str4);
    }
}

class B {
    static class C {
        public static final int num = 35000;
        public static int num1 = 50000;
        public String name = "lele";

        static {
            System.out.println("Inner class C get initiated!!!!!");
        }
    }
}
