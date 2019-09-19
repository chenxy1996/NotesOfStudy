package objectLearning;

import java.time.LocalDate;

/**
 * break up classes that have too many responsibilities
 * @author ≥¬œË”Ó
 *
 */
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
	

	
	public String  getDescription() {
		return "id: " + this.id + "\n" + "name: " + this.name;
	}
	
	public static void main(String[] args) {
		Employee aeEmployee = new Employee();
		System.out.println(aeEmployee.name);
	}
}
