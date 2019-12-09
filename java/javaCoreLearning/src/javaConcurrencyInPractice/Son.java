package javaConcurrencyInPractice;

public class Son extends Father{
    String name = "son";

    @Override
    void thinking() {
        GrandFather a = new GrandFather();
        a.thinking();
    }

    public static void main(String[] args) {
//        GrandFather test = new Son();
//        System.out.println(test.name);
//        System.out.println(((Father) test).name);
//        System.out.println(((Son) test).name);
//
//        System.out.println(test.getClass().getSuperclass());
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        int e = 3;
        Integer num1 = 321;
        Integer num2 = 321;
        Long g = 3L;

        System.out.println(c == d);
        System.out.println(c == e);
        System.out.println(num1 == num2);

        System.out.println(Integer.valueOf("123"));

        Integer num3 = -128;
        Integer num4 = -128;

        System.out.println(num3 == num4);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
        System.out.println();

//        System.out.println(Class.getPrimitiveClass("int"))
    }
}
