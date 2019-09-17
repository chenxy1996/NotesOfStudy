package test;

import java.math.BigDecimal;
import java.util.Arrays;

public class Welcome {
	enum Size {
		SMALL, MEDIUM, LARGE, EXTRA_LARGE
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student aStudent = new Student("chen");
		System.out.println(aStudent.sayName());
	}

}
