package innerClasses;

import interfaceTest.Comparable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.IntPredicate;

public class Test {
    private String name;
    static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    public Test(String name) {
        this.name = name;
    }

    public String getName() {
        class A implements I{
            @Override
            public String get() {
                return name;
            }
        }
        return new A().get();
    }

    public static void main(String[] args) {

        /* 初始化 int 数组*/
//        Integer[] nums = new Integer[100];
//        for (int i = 0; i < nums.length; i++) {
//            nums[i] = (int) Math.ceil(Math.random() * 100);
//        }
//
//        class Sortor implements Comparator<Integer> {
//            private String name;
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                counter += 1;
//                return o2 - o1;
//            }
//
//            public Sortor(String name) {
//                this.name = name;
//            }
//        }
//
//        Arrays.sort(nums, new Sortor("chen"));
//
//        System.out.println(Arrays.toString(nums));
//        System.out.println(counter);
        Test a = new Test("lele");
        System.out.println(a.counter);
        I x = () -> "CHEN";
        System.out.println(a.getCounter());
        System.out.println(I.sayHello());
        System.out.println(Comparable.class);
    }

}

@FunctionalInterface
interface I {
    String get();

    default I andThen(I then) {
        Objects.requireNonNull(then);
        return () -> {
            String a = this.get();
            System.out.println(a);
            return then.get();
        };
    }

    static String sayHello() {
        return "Hello World!!!";
    }
}

