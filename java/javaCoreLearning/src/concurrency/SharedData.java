package concurrency;

import java.util.Arrays;

public class SharedData {
    private int a, b, c, d;
    private final int[] nums = new int[] {1, 2, 3};
    public SharedData() {
        a = b = c = d = 10;

    }

    public synchronized void compute() {
        a = b * 20;
        b = a + 10;
    }

    public void sayNums() {
        System.out.println(Arrays.toString(nums));
    }

    public int[] getNums() {
        return nums;
    }

    public static void main(String[] args) {
        SharedData s = new SharedData();
        s.sayNums();
        int[] n = s.getNums();
        n[2] = 7;
        s.sayNums();
    }
}
