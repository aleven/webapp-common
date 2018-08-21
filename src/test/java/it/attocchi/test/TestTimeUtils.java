package it.attocchi.test;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import it.webappcommon.lib.TimeUtils;

/**
 * Created by Mirco on 17/05/16.
 */
public class TestTimeUtils {

	@Test
	public void test() throws Exception {
		long val = 0;
		int toMs = 1; // 1000;
		
		val = TimeUtils.decode("1m");
		Assert.assertEquals(val, 1 * 60 * toMs);
		val = TimeUtils.decode("1min");
		Assert.assertEquals(val, 1 * 60 * toMs);

		val = TimeUtils.decode("1h");
		Assert.assertEquals(val, 1 * 60 * 60 * toMs);
		val = TimeUtils.decode("1ora");
		Assert.assertEquals(val, 1 * 60 * 60 * toMs);

		val = TimeUtils.decode("1s");
		Assert.assertEquals(val, 1 * toMs);
		val = TimeUtils.decode("1sec");
		Assert.assertEquals(val, 1 * toMs);

		val = TimeUtils.decode("1g");
		Assert.assertEquals(val, 1 * 24 * 60 * 60 * toMs);
		val = TimeUtils.decode("1gg");
		Assert.assertEquals(val, 1 * 24 * 60 * 60 * toMs);

		val = TimeUtils.decode("1");
		Assert.assertEquals(val, 1);

		val = TimeUtils.decode("1000");
		Assert.assertEquals(val, 1000);
	}

	@Test
	public void testAdd() throws Exception {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.YEAR, 2018);
		start.set(Calendar.MONTH, 1);
		start.set(Calendar.DATE, 5);
		start.set(Calendar.HOUR_OF_DAY, 15);
		start.set(Calendar.MINUTE, 20);
		System.out.println(start.getTime());
		Date date = TimeUtils.aggiungi(start.getTime(), "2m");
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.MINUTE, 2);
		System.out.println(end.getTime());
		Assert.assertEquals(end.getTime(), date);
		
		start = Calendar.getInstance();
		start.set(Calendar.YEAR, 2018);
		start.set(Calendar.MONTH, 1);
		start.set(Calendar.DATE, 5);
		start.set(Calendar.HOUR_OF_DAY, 15);
		start.set(Calendar.MINUTE, 20);
		System.out.println(start.getTime());
		date = TimeUtils.aggiungi(start.getTime(), "80m");
		end = (Calendar) start.clone();
		end.add(Calendar.MINUTE, 80);
		System.out.println(end.getTime());
		Assert.assertEquals(end.getTime(), date);
		
		start = Calendar.getInstance();
		start.set(Calendar.YEAR, 2018);
		start.set(Calendar.MONTH, 1);
		start.set(Calendar.DATE, 5);
		start.set(Calendar.HOUR_OF_DAY, 15);
		start.set(Calendar.MINUTE, 20);
		System.out.println(start.getTime());
		date = TimeUtils.aggiungi(start.getTime(), "2g");
		end = (Calendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, 2);
		System.out.println(end.getTime());
		Assert.assertEquals(end.getTime(), date);		
	}
}
