package generic;

import java.util.ArrayList;

public class Test {
    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() { return this.name; };

        @Override
        public String toString() {
            return this.name;
        }
    }


    static class Student extends Person {
        private int id;

        public Student(String name, int id) {
            super(name);
            this.id = id;
        }

        public int getId() { return this.id; }

        @Override
        public String toString() { return this.id + " " + super.toString(); }

    }

    static class GradePair<T> {
        private T target;
        private int grade;

        public GradePair(T t, int grade) {
            this.target = t;
            this.grade = grade;
        }

        @Override
        public String toString() {
            return this.target.toString() + ": " + this.grade;
        }
    }

    static class StudentGradePair extends GradePair<Student> {

        public StudentGradePair(Student student, int grade) {
            super(student, grade);
        }

        @Override
        public String toString() {
            return super.toString() + " " + super.target.id;
        }
    }

    public static void main(String[] args) {
//        Student student = new Student("chen", 1);
//        GradePair<Person> pair = new GradePair<>(student, 99);
//        System.out.println(pair);
        ArrayList<String> al = new ArrayList<String>();
        ArrayList ol = al;
        ol.add("lele");
        ol.add(1);
        System.out.println(al.get(1));
    }
}
