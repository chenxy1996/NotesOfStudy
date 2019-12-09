package javaConcurrencyInPractice;

import javaConcurrencyInPractice.GrandFather;

public class Father extends GrandFather {
    String name = "father";
    @Override
    void thinking() {
        System.out.println("I'm father");
    }

    public static void main(String[] args) {
        Integer num1 = new Integer(1);
        Integer num2 = new Integer(1);

        Integer num3 = 1;
        Integer num4 = 1;
        System.out.println(num1 == num2);
        System.out.println(num3 == num4);
    }
}
