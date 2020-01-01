package finallyTest;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.*;

public class Test {
    static int[] get(int num) {
        int[] nums = new int[1];
        try {
            nums[0] = num;
            return nums;
        } finally {
            nums[0] = num + 7;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[5];
        for (int i = 0; i < 5; i++) {
            nums[i] = i + 1;
        }
    }
}
