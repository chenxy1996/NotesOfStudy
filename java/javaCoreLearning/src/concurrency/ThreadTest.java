package concurrency;

public class ThreadTest {
    static int[] nums;

    static {
        for (int i = 0; i < 10000; i++) {
            nums[i] = i;
        }
    }

    public static void main(String[] args) {

    }
}
