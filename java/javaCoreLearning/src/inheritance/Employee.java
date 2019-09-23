package inheritance;

import java.time.LocalDate;
import java.util.Objects;

public class Employee extends Person {
    private static int nextId = 0;

    private LocalDate hireDay;
    private int id = nextId++;
    private double salary;

    public Employee(String name, double salary, int year, int month, int day) {
        super(name);
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public Double getSalary() {
        return this.salary;
    }

    public void raiseSalary(double byPercent) {
        this.salary *= (1 + byPercent/100);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return super.getName() + ": " + this.id + " " + this.hireDay + " " + this.getSalary();
    }

    @Override
    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (!(otherObject instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) otherObject;
        return this.getId() == other.getId();
    }

    public static void main(String[] args) {
        Employee aEmployee = new Employee("chen", 50000, 2021, 9, 1);
        Employee bEmployee = new Employee("chen", 50000, 2021, 9, 2);
        Employee cEmployee = aEmployee;
        System.out.println(aEmployee.getDescription());
        System.out.println(bEmployee.getDescription());
        System.out.println(aEmployee.equals(cEmployee));
    }
}
