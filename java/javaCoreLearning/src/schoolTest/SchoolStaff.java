package schoolTest;
import java.lang.Comparable;

public abstract class SchoolStaff extends Person implements Comparable<SchoolStaff>{
    private final int auth;

    public SchoolStaff(String name, int age, int auth) {
        super(name, age);
        this.auth = auth;
    }

    public int getAuth() {
        return auth;
    }

    @Override
    public int compareTo(SchoolStaff staff) {
        return Integer.compare(auth, staff.getAuth());
    }
}
