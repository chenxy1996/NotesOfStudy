package generic.test;

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public static void main(String[] args) {
        Person aPerson = new Person("chen");
    }
}
