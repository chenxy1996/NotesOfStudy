package package2;
import package1.SupClass;

public class SubClass extends SupClass{
    void setX(SubClass obj, int num) {
        obj.x = num;
    }

    public static void main(String... args) {
        SubClass a = new SubClass();
        SubClass b = new SubClass();
        b.setX(a, 7);
        System.out.println(a.x);
    }
}
