package AccessTest.com;

import java.util.Scanner;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public int add(int... args) {
		int result = 0;
		
		for (int eachInt : args) {
			result += eachInt;
		}
		
		return result;
	}
	
	public static void main(String... args) {
		Scanner in = new Scanner(System.in);
	}
}
