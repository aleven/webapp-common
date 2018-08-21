package it.attocchi.test;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

public class TestDateUtilsJoda {

	@Test
	public void test() {
		LocalDate today = new DateTime().toLocalDate();
		String num = today.dayOfWeek().getAsShortText(Locale.ITALIAN);
		System.out.println(num);
	}

}
