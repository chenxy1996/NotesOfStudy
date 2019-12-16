package generic.test;

import generic.test.Pair;
import javaConcurrencyInPractice.GrandFather;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;

public class StudentGradePair extends Pair<Student, Grade> {
    public StudentGradePair(Student t, Grade g) {
        super(t, g);
    }

    @Override
    public String toString() {
        return getFirst().toString() + ": " + getSecond().toString();
    }

    public static void main(String[] args) {
        Student s = new Student("chen", 1);
        Grade math = new Grade("Math", 150);
        Pair<?, ?> pair = new Pair<>(s, math);
        System.out.println(pair.getFirst());
        System.out.println(pair.getSecond());
        Student s1 = new Student("lele", 2);
        System.out.println(((Student)pair.getFirst()).getName());

//        Function<Person, String> func = (student) -> student.toString() + "# This is func!";
//        Function<Person, Student> beforeFunc = (person) -> new Student(person.toString(), 2);
//        Function<Student, String> function = func.compose(beforeFunc);
//
//        System.out.println(function.apply(new Student("chen", 1)));
    }
}
