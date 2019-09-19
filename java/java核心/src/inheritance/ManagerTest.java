package inheritance;

public class ManagerTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Manager boss = new Manager("chen", 50000, 2022, 9, 1);
		boss.setBonus(30000);
		
		Employee[] staff = new Employee[3];
		
		staff[0] = boss;
		staff[1] = new Employee("lele", 50000, 2022, 9, 2);
		staff[2] = new Employee("aHuang", 50000, 2022, 9, 3);
		
		for (Employee eachStaff : staff) {
			System.out.println(eachStaff.getId() + " " + eachStaff.getName() 
																+ " " + eachStaff.getSalary());
		}
	}

}
