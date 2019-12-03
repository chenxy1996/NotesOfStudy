package javaConcurrencyInPractice;

import java.util.Arrays;

public class ExceptionHandler {
    static int[] understandFinally() {
        int[] x = {1, 2};
        try {
            x[0] = 3;
            return x;
        } catch (RuntimeException e) {
            x[0] = 2;
            return x;
        } finally {
            x[0] = 5;
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(understandFinally()));
    }
}
