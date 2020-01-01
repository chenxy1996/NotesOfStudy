package schoolTest;

import methodReference.Employee;

import java.util.*;

public class SchoolTest {
    public static <T> void  printAllNames(int... persons) {
        System.out.println(persons.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        Teacher[] teachers = new Teacher[2];
        teachers[0] = new Teacher("CHEN", 23, "MATH");
        teachers[1] = new Teacher("LELE", 10, "CHINESE");
        List<Teacher> teacherList = Arrays.asList(teachers);
        Collections.sort(teacherList, Comparator.comparingInt(Person::getAge).reversed());
        System.out.println(teacherList);
        System.out.println(Arrays.toString(teachers));
        teacherList.sort(Comparator.comparingInt(Person::getAge));
        System.out.println(teacherList);
    }
}
