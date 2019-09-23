package package2;
import package1.SupClass;

public class SubClass extends SupClass{
    protected int x = 2;

    void setX(SubClass obj, int num) {
        obj.x = num;
    }

    public static void main(String[] args) {
        SubClass aSupClass = new SubClass();

        System.out.println(aSupClass.x);
    }
}
