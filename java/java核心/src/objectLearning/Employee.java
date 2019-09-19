package objectLearning;

import java.time.LocalDate;

public class Employee {
	
	private static int nextId = 1;
	private String name;
	private int id = nextId;
	
	{
		System.out.println("Not static!");
		
	}
	
	static {
		System.out.println("Static!");
	}
	
	public Employee(String name) {
		this.name = name;
	}
	
	public String  getDescription() {
		return "id: " + this.id + "\n" + "name: " + this.name;
	}
	
	public static void main(String[] args) {
		Employee a = new Employee("chen");
		Employee b = new Employee("lele");
		Employee c = new Employee("xiaobai");
		
		System.out.println(a.getDescription());
		System.out.println(b.getDescription());
		System.out.println(c.getDescription());
	}
}
