package inheritance;

import java.util.Objects;

public class Student extends Person {
    private String major;

    public Student(String name, String major) {
        super(name);
        this.major = major;
    }

    public String getMajor() {
        return this.major;
    }

    @Override
    public String getDescription() {
        return super.getName() + ": " + this.major;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (this.getClass() != otherObject.getClass()) {
            return false;
        }
        Student other = (Student) otherObject;
        return Objects.equals(this.getName(), other.getName())
                && Objects.equals(this.major, other.major);
    }

    public static void main(String[] args) {
        Student aStudent = new Student("chen", "computer science");
        Student bStudent = new Student("chen", "computer science");

        System.out.println(aStudent.equals(bStudent));
    }
}
