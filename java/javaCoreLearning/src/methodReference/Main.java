package methodReference;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Function<Integer, int[]> func = int[]::new;
        int[] nums = func.apply(5);
        for (int i = 0; i < 5; i++) {
            nums[i] = i;
        }
        System.out.println(Arrays.toString(nums));
    }
}
