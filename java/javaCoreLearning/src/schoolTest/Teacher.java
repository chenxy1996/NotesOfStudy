package schoolTest;

public class Teacher extends SchoolStuff implements Teaching {
    private static final int AUTH = 1;
    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age, AUTH);
        this.subject = subject;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String newSubject) {
        subject = newSubject;
    }
}
