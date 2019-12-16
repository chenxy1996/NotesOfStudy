package generic;

import java.util.function.Consumer;

/**
 * CLOSURE TEST 闭包测试
 */
// 下面是用来演示闭包 Closure;
public class ClosureTest {
    static Consumer<Integer> print;

    static void initiatePrint(int arg) {
        // 闭包中引用的外部变量要求必须是
        // final 或者是 effectively final.
        // 这里初始化了一个数组 nums 其引用
        // 是不变的(final) 但是可以更改其内部
        // 所储存的元素的值。
        final int[] nums = {0};

        print = new Consumer<Integer>() {
            @Override
            public void accept(Integer x) {
                System.out.println(arg + nums[0] + x);
                nums[0] += 1;
            }
        };
    }

    public static void main(String... args) {
        initiatePrint(3); // 初始化 Consumer<Integer>

        if (print == null) {
            System.out.println("Please initiate the print!");
        } else {
            for (int i = 0; i < 10; i++) {
                print.accept(10);
            }
        }
    }
}
