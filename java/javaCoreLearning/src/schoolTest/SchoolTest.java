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

        String s1 = "1";
        String s2 = String.valueOf(2).intern();
        String s3 = "2";
        System.out.println(s2 == s3);

        String s4 = (new String("1") + new String("2"));
        s4.intern();
        String s5 = "12";
        System.out.println(s5 == s4);

        String s6 = String.valueOf(1).intern();
        String s7 = "1";

        System.out.println(s6 == s7);
    }
}
