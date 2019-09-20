package inheritance;

public class ManagerTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Manager[] managers = new Manager[1];
		managers[0] = new Manager("chen", 50000.0, 2021, 9, 1);
		managers[0].setBonus(40000);
		Employee aEmployee = new Employee("lele", 50000, 2021, 9, 2);
		Manager aManager = (Manager)aEmployee;
		
		Employee castedEmployee = (Employee)managers[0];
		Manager newManager = (Manager)castedEmployee;
		System.out.println(newManager.getSalary());
		newManager.setBonus(50000);
		System.out.println(castedEmployee.getSalary());
		System.out.println(managers[0].getSalary());
	}

}
