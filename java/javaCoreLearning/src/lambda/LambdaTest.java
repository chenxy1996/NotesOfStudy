package lambda;


import java.util.Comparator;
import java.util.function.*;

public class LambdaTest {
    public static void main(String[] args) {
        Runnable action = () -> System.out.println("Hello World!");
    }

    public static double add(double a, double b, double c) {
        return a + b + c;
    }

    public static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++) {
            action.accept(i);
        }
    }
}
