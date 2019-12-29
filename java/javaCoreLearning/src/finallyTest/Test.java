package finallyTest;

import java.util.LinkedList;
import java.util.function.Consumer;

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
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(2);
        list.add(7);
        System.out.println(list);
        list.forEach();
    }
}
