package AccessTest;

class SubClass extends SuperClass{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SuperClass aSuperClass = new SuperClass();
		System.out.println(aSuperClass.worldProString);
		System.out.println(aSuperClass.protectedString);
		System.out.println(aSuperClass.defaultString);
	}

}
