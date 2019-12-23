package methodReference;

import inheritance.Employee;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Main {
    static class Employee implements Comparable<Employee> {
        private final String name;
        private final int id;
        private int rank = 1;

        public Employee(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() { return name; }
        public int getId() { return id; }
        public int getRank() { return rank; }

        @Override
        public final int compareTo(@NotNull Main.Employee o) {
            return Integer.compare(rank, o.getRank());
        }
    }

    static class Manager extends Main.Employee {
        private int rank = 2;

        public Manager(String name, int id) {
            super(name, id);
        }
    }

    public static void main(String[] args) {

    }
}
