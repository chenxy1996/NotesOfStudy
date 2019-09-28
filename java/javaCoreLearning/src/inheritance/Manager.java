package inheritance;

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
        Manager a = new Manager("chenxiangyu", 200000, 2021, 9, 1);
        Manager b = (Manager) a.clone();
        System.out.println(b);
        System.out.println(a.equals(b));
    }
}
