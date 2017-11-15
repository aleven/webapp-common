package it.attocchi.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.attocchi.utils.DateUtilsLT;

public class TestDateUtilsLT {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testExists() {
		logger.info("testExists");
		boolean res = DateUtilsLT.exists(2017, 02, 30);
		logger.info("{} {} {} = {}", 2017, 02, 30, res);
		Assert.assertFalse(res);
	}

}
