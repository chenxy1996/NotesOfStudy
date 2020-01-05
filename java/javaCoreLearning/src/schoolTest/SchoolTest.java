package schoolTest;

import jdk.swing.interop.SwingInterOpUtils;
import methodReference.Employee;

import java.util.*;

public class SchoolTest {
    public static <T> void  printAllNames(int... persons) {
        System.out.println(persons.getClass().getSimpleName());
    }

    public static void main(String[] args) {
//        SchoolStuff[] teachers = new SchoolStuff[3];
//        teachers[0] = new Teacher("LELE", 10, "CHINESE");
//        teachers[1] = new Principal("CHENXIANGYU", 24);
//        teachers[2] = new Principal("CHEN", 23);
//        List<SchoolStuff> teacherList = Arrays.asList(teachers);
//
//        BitSet bucket = new BitSet(4);
//        bucket.set(0);
//        bucket.set(1);
//        bucket.set(2);

        String s1 = String.valueOf(1).intern();
        String s2 = String.valueOf(2);

        String s3 = "1";

        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s3));
    }
}
