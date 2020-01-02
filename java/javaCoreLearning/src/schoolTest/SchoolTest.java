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
        Comparator<SchoolStuff> comp = ((Comparator<SchoolStuff>) Comparator.naturalOrder()).thenComparing(Comparator.comparingInt(Person::getAge).reversed());
        teacherList.sort(comp);
        System.out.println(teacherList);
    }
}
