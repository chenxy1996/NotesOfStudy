package AccessTest.package2;
import AccessTest.package1.SupClass;

public class SubClass extends SupClass{
	public static void main(String... args) {
		SupClass a = new SupClass();
		System.out.println(a.mottoStatic);
		
		SubClass b = new SubClass();
		System.out.println(b.motto);
	}
}
