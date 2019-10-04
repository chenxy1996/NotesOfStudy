package inheritance;

import org.w3c.dom.ls.LSOutput;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public abstract class Person {
    private  String name;

    public Person(String name) {
        this.name = name;
    }

    public final String getName() {
        return this.name;
    }

    public abstract String getDescription();

    @Override
    public abstract boolean equals(Object otherObject);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    public static String sayName() {
        return "chenxiangyu is a good man!";
    }

    public static void main(String[] args) throws CloneNotSupportedException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Person a = new Person("chen") {
            private String motto;
            {
                this.motto = "Lele is an adorable dog!!!!!";
                System.out.println(this.motto);
            }
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public boolean equals(Object otherObject) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String toString() {
                return null;
            }

            public void sayMyName() {
                System.out.println(super.getName());
            }
        };
        Person b = new Person("lele") {
            private String motto;
            {
                this.motto = "Lele is an adorable dog!!!!!";
                System.out.println(this.motto);
            }
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public boolean equals(Object otherObject) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String toString() {
                return null;
            }

            public void sayMyName() {
                System.out.println(super.getName());
            }
        };
        Class cls = Class.forName("inheritance.Person$1");
        System.out.println(a.getClass() == cls);
        System.out.println(Person.class);
        System.out.println(new Object(){}.getClass().getEnclosingClass());
    }
}
