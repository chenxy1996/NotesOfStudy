package schoolTest;

public class Teacher extends SchoolStaff implements Teaching {
    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age, 1);
    }

    @Override
    public String getSubject() {
        return null;
    }

    @Override
    public String setSubject(String newSubject) {
        return null;
    }
}
