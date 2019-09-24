package inheritance;

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
}
