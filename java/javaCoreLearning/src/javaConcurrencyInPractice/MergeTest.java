package javaConcurrencyInPractice;

import java.util.Arrays;
import java.util.Random;

public class MergeTest {
    public static int[] generateIntArrays(int number, int bound) {
        int[] nums = new int[number];
        Random r = new Random();

        for (int i = 0; i < number; i++) {
            nums[i] = r.nextInt(bound);
        }

        return nums;
    }

    public static double sort(int[] nums, MergeSort s) throws InterruptedException {
        long start = System.currentTimeMillis();
        s.sort(nums);
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(15);
    }
}
