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
        // 对于一个类的实例可以用其 getClass 的方法来获取这个实例的类。
        // 对于一个类，要想获取这个类就只能通过 类名.class 的方式来获得。
        // 单独一个类名无法获得这个类，会被编译器接释为一个类型。
        if (this.getClass() != otherObject.getClass()) {
            return false;
        }
        Student other = (Student) otherObject;
        return Objects.equals(this.getName(), other.getName())
                && Objects.equals(this.major, other.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName(), this.major);
    }

    @Override
    public final String toString() {
        return this.getClass().getName() + "[name=" + super.getName() + ", " + "major=" + this.major
                + "]";
    }

    public static void main(String[] args) {

    }
}
