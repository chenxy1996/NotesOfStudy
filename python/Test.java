import java.util.function.Function;

public class Test {
    public int getInt() {
        return 7;
    }

    public static void main(String[] args) {
        // int i = 3;
        Function<Integer, String> fun = a -> String.valueOf(a);
        System.out.println(fun.apply(3));

        System.out.println(999);

        Test a = new Test();
        System.out.println(a.getInt());
    }
}