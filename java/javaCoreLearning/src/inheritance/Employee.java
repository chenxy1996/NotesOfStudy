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
        return this.id == other.id;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public final String toString() {
        return this.getClass().getName() + "[name=" + super.getName() + ", salary=" + this.getSalary()
                + "]";
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Employee aEmployee = new Employee("chen", 50000, 2021, 9, 1);
        Employee bEmployee = new Employee("chen", 50000, 2021, 9, 2);
        Employee cEmployee = aEmployee;
        System.out.println(aEmployee);
    }
}
