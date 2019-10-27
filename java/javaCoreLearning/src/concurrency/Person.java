package concurrency;

import java.util.function.Function;

public class Person {
    public static int count = 0;
    public static final String description = "Person class";
    private String name;
    public int flag = 0;

    public Person(String name) {
        count += 1;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return "Current class is " + description;
    }

    public static void main(String[] args) {
        Student aStudent = new Student("chenxiangyu", 9);
        System.out.println(aStudent.getDescription());
        System.out.println(aStudent.count);
        System.out.println(aStudent.flag);
        Person bStudent = new Student("chenxiangyu", 9);
        System.out.println(bStudent.flag);

        Function<Integer, String> fun = a -> String.valueOf(a);

    }
}

class Student extends Person {
    public static final String description = "Student class";
    public static int count = 10;
    private int grade;
    public int flag = 1;

    public Student(String name, int grade) {
        super(name);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public String getDescription() {
        return "Current class is " + description + "; count: " + count;
    }
}
