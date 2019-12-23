package methodReference;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Employee implements Comparable<Employee> {
    private String name;
    private int id;

    @Override
    public int compareTo(@NotNull Employee o) {
        Objects.requireNonNull(o);
        return Integer.compare(id, o.getId());
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static void main(String[] args) {

    }
}
