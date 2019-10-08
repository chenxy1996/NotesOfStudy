package inheritance;

import java.util.Arrays;

public class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
    }

    public void setBonus(double newBonus) {
        this.bonus = newBonus;
    }

    @Override
    public Double getSalary() {
        return super.getSalary() + this.bonus;
    }



    public static void main(String[] args) throws CloneNotSupportedException {
        Employee[] staff = new Manager[2];
        staff[0] = new Manager("chenxiangyu", 200000, 2021, 9, 1);
        staff[1] = new Manager("lele", 90000, 2021, 9, 1);

        System.out.println(staff[0].getClass());

//        Object[] a = staff;

//        a[0] = staff[0];
//        a[1] = staff[1];
//        System.out.println(a == staff);
//
//        System.out.println(Arrays.toString(staff));
//        System.out.println(Arrays.toString(a));
//
//        a[0] = new Manager("xiaobai", 20000, 2022, 9, 1);
//        System.out.println(Arrays.toString(staff));
//        System.out.println(Arrays.toString(a));
    }
}
