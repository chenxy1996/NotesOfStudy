package lambda;


import interfaceTest.Interface;

import java.util.Arrays;
import java.util.Comparator;

public class LambdaTest {
    public static void main(String[] args) {
        Handler callback = (a, b, c) -> a - b - c;
        System.out.println(callback.handle1(3, 4, 5));
    }
}
