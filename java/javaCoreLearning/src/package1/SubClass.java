package package1;

public class SubClass extends SupClass{
    public static void main(String... args) {
        SupClass aSupClass = new SupClass();
        System.out.println(aSupClass.getProtectedString());
    }
}
