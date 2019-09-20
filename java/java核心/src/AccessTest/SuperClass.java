package AccessTest;


public class SuperClass {
	
	private String classOnlyProString = "class Only";
	public String worldProString = "all wold";
	protected String protectedString = "to the package and subclass";
	String defaultString = "default to the package";
	
	public static void main(String... args) {
		SuperClass aSuperClass = new SuperClass();
		
		System.out.println(aSuperClass.classOnlyProString);
		System.out.println(aSuperClass.worldProString);
		System.out.println(aSuperClass.protectedString);
		System.out.println(aSuperClass.defaultString);
	}

}
