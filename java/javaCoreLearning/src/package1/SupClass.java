package package1;

/**
 * This is a SupClass
 * @author 陈翔宇
 */
public class SupClass extends Object implements GeneralUtils{
    private String protectedString = "This is a protected field of SupClass";
    private final String finalString = "This is a final string";

    /**
     * @return String
     */
    public String getProtectedString() {
        this.protectedString = "changed!";
        return this.finalString;
    }

    public static void main(String... args) {
        SupClass aSupclass = new SupClass();
        System.out.println(aSupclass.getProtectedString());
        System.out.println(aSupclass.protectedString);
    }

    @Override
    public String sayName() {
        return null;
    }
}
