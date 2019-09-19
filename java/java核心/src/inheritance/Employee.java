package inheritance;

import java.time.LocalDate;

public class Employee {
	private static int nextId = 0;
	
	private String name;
	private LocalDate hireDay;
	private double salary;
	private int id = Employee.nextId++;
	
	public static int getNextId() {
		return Employee.nextId;
	}
	
	public Employee(String name, double salary, int year, int month, int day) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.salary = salary;
		this.hireDay = LocalDate.of(year, month, day);
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public LocalDate getHireDay() {
		return this.hireDay;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getSalary() {
		return this.salary;
	}
	
	
	public void raiseSalary(double byPercent) {
		this.salary *= 1.0 + byPercent / 100;
	}
	
	
	public static void main(String[] args) {
		Employee aEmployee = new Employee("chen", 30000, 2022, 9, 1);
		System.out.println(aEmployee.getSalary());
		aEmployee.raiseSalary(50);
		System.out.println(aEmployee.getSalary());
		System.out.println(Employee.getNextId());
		System.out.println(aEmployee.getHireDay());
		
		Employee bEmployee = new Employee("lele", 50000, 2023, 9, 1);
		System.out.println(bEmployee.getSalary());
		bEmployee.raiseSalary(50);
		System.out.println(bEmployee.getSalary());
		System.out.println(Employee.getNextId());
		System.out.println(bEmployee.getHireDay());
	}
}	
	
