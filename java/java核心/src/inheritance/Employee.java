package inheritance;

import java.time.LocalDate;

public class Employee extends Person{
	private static int nextId = 0;
	
	private LocalDate hireDay;
	private double salary;
	private int id = Employee.nextId++;
	
	public static int getNextId() {
		return Employee.nextId;
	}
	
	public Employee(String name, double salary, int year, int month, int day) {
		// TODO Auto-generated constructor stub
		super(name);
		this.salary = salary;
		this.hireDay = LocalDate.of(year, month, day);
	}
	
	@Override
	public String getDescription() {
		return "name: " + super.getName() + '\n' + "salary: " + this.salary + '\n' 
				 																+ "hireDay: " + this.hireDay;
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
		Person[] persons = new Person[2];
		persons[0] = new Employee("chen", 60000, 2021, 9, 2);
		persons[1] = new Student("lele", "Computer Science");
		
		for(Person eachPerson : persons) {
			System.out.println(eachPerson.getDescription());
			System.out.println("--·Ö" + "---------------" + "¸ô" + "---------------" + "Ïß--");
		}
		
		Employee aEmployee = new Employee("aHuang", 50000, 2021, 9, 3);
		System.out.println(aEmployee.salary);
	}
}	
	
