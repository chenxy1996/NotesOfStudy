package methodReference;

import inheritance.Employee;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

        @Override
        public String toString() {
            return id + " " + name;
        }
    }

    static class Manager extends Main.Employee {
        private int rank = 2;

        public Manager(String name, int id) {
            super(name, id);
        }
    }

    static class Executor extends Manager {
        private int rank = 3;
        public Executor(String name, int id) {
            super(name, id);
        }

        public void say() {
            System.out.println("Executor!");
        }
    }

    public static void main(String[] args) {
//        List<Executor> list = new ArrayList<>();
//        list.add(new Executor("chen", 1));
//        List<? extends Object> helper = list;
//        List<Employee> newList = (List<Employee>) helper;
//        Employee aEmployee = newList.get(0);
//        System.out.println(aEmployee.getName());
//        List<Manager> raw = new ArrayList<>();
//        List<? extends Manager> list = raw;
//        List<Employee> newList = (List<Employee>) list;
//        newList.add(new Employee("chen", 1));
//        System.out.println(newList.get(0).getRank());
        Executor a = new Executor("chen", 1);
        System.out.println(a.getRank());
    }
}
