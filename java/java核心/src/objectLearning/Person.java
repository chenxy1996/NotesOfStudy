package objectLearning;

class Person {
	
	/**
	 * The name of a person instance.
	 */
	private String name;
	
	public Person() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	* Raises the salary of an employee.
	* @param byPercent the percentage by which to raise the salary (e.g. 10 means 10%)
	* @param byNumber
	* @return the amount of the raise
	*/
	public static void say() {
		System.out.println("This is Person");
	}
	
	public String sayName() {
		return this.name;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Person");
		Student.say();
	}
}


class Student {
	public static void say() {
		System.out.println("This is Student!");
	}
}


