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

    public static void main(String[] args) {
        Manager aManager = new Manager("chenxiangyu", 90000, 2021, 9, 1);
        Manager bManager = new Manager("chenxiangyu", 90000, 2021, 9, 1);
        aManager.setBonus(50000);
        aManager.raiseSalary(10);
        System.out.println(aManager.getDescription());
        System.out.println(bManager.getDescription());
        System.out.println(aManager.equals(bManager));
    }
}
