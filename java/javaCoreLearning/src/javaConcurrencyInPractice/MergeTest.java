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
        int[] numbers = {100, 200, 10000, 50000, 100000, 300000};
        int[] nums;
        int[] nums1;
        MergeSort normalSort = new MergeSort();
        MergeSort parallelSort = new ParallelMergeSort();

        for (int eachNumber : numbers) {
            nums = generateIntArrays(eachNumber, 256);
            nums1 = Arrays.copyOf(nums, nums.length);

            System.out.print(eachNumber + "------");
            System.out.print("normalSort: " + sort(nums, normalSort) + " paralleclSort: " + sort(nums1, parallelSort) + "\n");
        }
    }
}
