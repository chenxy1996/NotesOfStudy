package schoolTest;

import jdk.swing.interop.SwingInterOpUtils;
import methodReference.Employee;

import java.util.*;

public class SchoolTest {
    public static <T> void  printAllNames(int... persons) {
        System.out.println(persons.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        String s1 = String.valueOf(1).intern();
        String s2 = String.valueOf(2);

        String s3 = "1";

        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(7 + 5);
    }
}
