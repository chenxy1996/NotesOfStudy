package schoolTest;

import methodReference.Employee;

import java.util.*;

public class SchoolTest {
    public static <T> void  printAllNames(int... persons) {
        System.out.println(persons.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        SchoolStuff[] teachers = new SchoolStuff[3];
        teachers[0] = new Teacher("LELE", 10, "CHINESE");
        teachers[1] = new Principal("CHENXIANGYU", 24);
        teachers[2] = new Principal("CHEN", 23);
        List<SchoolStuff> teacherList = Arrays.asList(teachers);

        BitSet bucket = new BitSet(4);
        bucket.set(0);
        bucket.set(1);
        bucket.set(2);

        String s1 = new String("1") + new String("1");
        s1.intern();
        String s2 = "11";
        System.out.println(s1 == s2);

        String s3 = String.valueOf(1);
        String s4 = String.valueOf(2);

        String.valueOf(3).intern();

        System.out.println(s3.equals(s4));
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        sb.append(2);
        System.out.println(sb);

        StringBuffer sf = new StringBuffer();
        sf.append(1);
        sf.append(2);
        sf.append(5);

        System.out.println(sf.toString());
    }
}
