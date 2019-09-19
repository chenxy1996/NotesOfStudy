package inheritance;

public class Manager extends Employee{
	private double bonus = 0;
	
	public Manager(String name, double salary, int year, int month, int day) {
		// TODO Auto-generated constructor stub
		super(name, salary, year, month, day);
	}
	
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	public double getBonus() {
		return this.bonus;
	}
	
	public double getSalary() {
		return super.getSalary() + this.bonus;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Manager aManager = new Manager("chen", 50000, 2022, 9, 1);
		System.out.println(aManager.getSalary());
		aManager.setBonus(20000);
		System.out.println(aManager.getSalary());
	}

}
