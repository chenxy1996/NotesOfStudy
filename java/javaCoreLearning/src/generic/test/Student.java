package generic.test;

public class Student extends Person {
    private final int className;

    public Student(String name, int className) {
        super(name);
        this.className = className;
    }

    @Override
    public String toString() {
        return "class " + this.className + ", " + getName();
    }

    public int getClassName() {
        return this.className;
    }

    public static void main(String[] args) {
        Student aStudent = new Student("chen", 1);
        System.out.println(aStudent);
    }
}
