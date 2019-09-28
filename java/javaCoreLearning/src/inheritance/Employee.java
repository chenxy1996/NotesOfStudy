package inheritance;

import java.time.LocalDate;
import java.util.Objects;
import interfaceTest.Comparable;

public class Employee extends Person implements Comparable<Employee>, Cloneable{
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

    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.salary, other.salary);
    }

    @Override
    public Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {

    }
}
