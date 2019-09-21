package package1;

public class SupClass {
    private String protectedString = "This is a protected field of SupClass";
    public String getProtectedString() {
        return this.protectedString;
    }

    public static void main(String... args) {
        SupClass aSupclass = new SupClass();
        System.out.println(aSupclass.protectedString);
    }
}
