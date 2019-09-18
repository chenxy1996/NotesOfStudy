package objectLearning;

import java.time.LocalDate;

public class Employee {
	private String name;
	private double salary;
	private LocalDate hireDay;
	
	public Employee(String n, double s, int year, int month, int day) {
		// TODO Auto-generated constructor stub
		name = n;
		salary = s;
		hireDay = LocalDate.of(year, month, day);
	}
	
	public 	String getName() {
		return this.name;
	}
	
	public void raiseSlary(double byPercent) {
		double raise = this.salary * byPercent / 100;
		this.salary += raise;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
