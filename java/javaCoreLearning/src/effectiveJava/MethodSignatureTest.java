package effectiveJava;

public class MethodSignatureTest {
    static int get(int n, String s) {
        return n + Integer.valueOf(s);
    }

    static String get(String s, int n) {
        return s + String.valueOf(n);
    }

    public static void main(String[] args) {
        System.out.println(get(1, "2"));

        System.out.println(get("2", 1));
    }
}
