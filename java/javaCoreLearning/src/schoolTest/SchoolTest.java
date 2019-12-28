package schoolTest;

import java.util.Arrays;
import java.util.Comparator;

public class SchoolTest {
    public static void main(String[] args) {
        SchoolStuff[] staff = new SchoolStuff[3];
        Principal prin = new Principal("chen", 23);
        staff[0] = prin;
        staff[1] = new Teacher("xiaobai", 3, "math");
        staff[2] = new GradeDirector("lele", 12, "chinese");

        System.out.println(prin.getAuth());

        System.out.println(Arrays.toString(staff));
        Arrays.sort(staff);
        System.out.println(Arrays.toString(staff));
        Arrays.sort(staff, Comparator.comparingInt(Person::getAge).reversed());
        System.out.println(Arrays.toString(staff));

        for (SchoolStuff eachStaff : staff) {
            System.out.println(eachStaff.getId() + " "
                    + eachStaff.getName() + " " +
                            eachStaff.getClass().getSimpleName());
        }

        for (SchoolStuff eachStaff : staff) {
            if (eachStaff instanceof Teaching) {
                System.out.println(((Teaching) eachStaff).getSubject());
            }
        }
    }
}
