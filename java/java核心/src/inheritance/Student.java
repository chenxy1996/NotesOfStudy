package inheritance;

public class Student extends Person {
	private String major;
	
	public Student(String name, String major) {
		super(name);
		// TODO Auto-generated constructor stub
		this.major = major;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Name: " + super.getName() + '\n' + "major: " + this.major;
	}
	
	public String getMajor() {
		return this.major;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student aStudent = new Student("chen", "computer science");
		System.out.println(aStudent.getDescription());
	}

}
