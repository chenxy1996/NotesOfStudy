package schoolTest;
import java.lang.Comparable;

public abstract class SchoolStuff extends Person implements Comparable<SchoolStuff>{
    private final int auth;
    private final int id;
    private static int NEXT_ID = 0;

    public SchoolStuff(String name, int age, int auth) {
        super(name, age);
        this.auth = auth;
        this.id = NEXT_ID++;
    }

    public int getId() { return id; }

    public int getAuth() {
        return auth;
    }

    @Override
    public int compareTo(SchoolStuff staff) {
        return Integer.compare(auth, staff.getAuth());
    }

    @Override
    public String toString() {
        return auth + " " + super.toString();
    }
}
