package package1;

/**
 * This is a SupClass
 * @author 陈翔宇
 */
public class SupClass extends Object{
    private String protectedString = "This is a protected field of SupClass";

    /**
     * @return String
     */
    public String getProtectedString() {
        int a = 3;
        return this.protectedString;
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
    SupClass aSupclass = new SupClass();
    System.out.println(aSupclass.getProtectedString());
    }
}