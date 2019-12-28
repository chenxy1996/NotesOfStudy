package schoolTest;

public class GradeDirector extends SchoolStuff implements Teaching {
    private static final int AUTH = 2;
    private String subject;

    public GradeDirector(String name, int age, String subject) {
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
