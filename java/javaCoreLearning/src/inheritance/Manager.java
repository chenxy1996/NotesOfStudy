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

    public static double add(double... args) {
        double sum = 0;
        for (double eachNum : args) {
            sum += eachNum;
        }
        return sum;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Employee aManager = new Manager("chenxiangyu", 90000, 2022, 9, 1);
        String className = aManager.getClass().getName();
        Class cl = Class.forName(className);
    }
}
