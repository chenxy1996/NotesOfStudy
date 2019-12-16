package generic.test;

public class Grade {
    private final String subject;
    private final int point;

    public Grade(String subject, int point) {
        this.subject = subject;
        this.point = point;
    }

    public String getSubject() {
        return this.subject;
    }

    public int getPoint() {
        return this.point;
    }

    @Override
    public String toString() {
        return subject + ": " + point;
    }

    public static void main(String[] args) {
        Grade math = new Grade("Math", 150);
        System.out.println(math);
    }
}
