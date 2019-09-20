package inheritance;

public abstract class Person {
	
	private String name;
	
	public Person(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	public abstract String getDescription();
	
	public String getName() {
		return this.name;
	}
	
}
