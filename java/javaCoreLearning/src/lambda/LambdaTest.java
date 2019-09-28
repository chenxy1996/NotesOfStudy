package lambda;


import interfaceTest.Interface;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class LambdaTest {
    public static void main(String[] args) {
        Handler callback = LambdaTest::add;
        System.out.println(callback.handle1(1, 2, 3));
        System.out.println(Handler.class);
    }

    public static double add(double a, double b, double c) {
        return a + b + c;
    }
}
