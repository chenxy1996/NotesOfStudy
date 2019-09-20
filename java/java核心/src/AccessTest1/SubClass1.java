package AccessTest1;

import AccessTest.SuperClass;

public class SubClass1 extends SuperClass{

	public SubClass1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SubClass1 aSubClass1 = new SubClass1();
		System.out.println(aSubClass1.protectedString);
		
		SuperClass aSuperClass = new SuperClass();
		System.out.println(aSuperClass.worldProString);
		System.out.println(aSuperClass.protectedString);
	}

}
