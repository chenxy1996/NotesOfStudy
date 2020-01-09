package effectiveJava.builder;

public class Person {
    private final int age;
    private final String gender;
    private final String name;

    public static class PersonBuilder {
        private int age = 0;
        private String gender   = null;
        private String name     = null;

        PersonBuilder withName(String name) {
            this.name = name;
            return this;
        }

        PersonBuilder withAge(int age) {
            this.age = age;
            return this;
        }

        PersonBuilder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Person build() {
            return new Person(this);
        };
    }

    protected Person(PersonBuilder pb) {
        this.age    = pb.age;
        this.gender = pb.gender;
        this.name   = pb.name;
    }

    @Override
    public String toString() {
        return name + " " + gender + " " + age;
    }

    public static void main(String[] args) {

    }
}
