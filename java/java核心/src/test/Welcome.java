package test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class Welcome {
	enum Size {
		SMALL, MEDIUM, LARGE, EXTRA_LARGE
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDate nowDate = LocalDate.now();
		LocalDate aThousandDaysLater = nowDate.plusDays(1000);
		System.out.println(aThousandDaysLater);
	}

}
