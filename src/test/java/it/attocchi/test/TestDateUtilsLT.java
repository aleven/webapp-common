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
		int anno = 2017;
		int mese = 1;
		int giorno = 30;
		boolean res = DateUtilsLT.exists(anno, mese, giorno);
		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		
		Assert.assertFalse(res);
	}

}
