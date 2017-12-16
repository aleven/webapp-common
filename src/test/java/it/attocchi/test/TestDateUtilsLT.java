package it.attocchi.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.attocchi.utils.DateUtilsLT;
import it.webappcommon.lib.DateUtils;

public class TestDateUtilsLT {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testExistsFalse() {
		logger.info("testExists");
		int anno = 2017;
		int mese = 1;
		int giorno = 30;
		boolean res = DateUtils.exists(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertFalse(res);
	}

	@Test
	public void testExistsTrue() {
		logger.info("testExists");
		int anno = 2017;
		int mese = 1;
		int giorno = 28;
		boolean res = DateUtils.exists(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertTrue(res);
	}
	
	@Test
	public void testExistsFalseBisestile() {
		logger.info("testExists");
		int anno = 2017;
		int mese = 1;
		int giorno = 29;
		boolean res = DateUtils.exists(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertFalse(res);
	}
	
	@Test
	public void testExistsTrueBisestile() {
		logger.info("testExists");
		int anno = 2020;
		int mese = 1;
		int giorno = 29;
		boolean res = DateUtils.exists(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertTrue(res);
	}
	
	@Test
	public void testSearchValidBeforeDataNonValida() {
		logger.info("testExists");
		int anno = 2017;
		int mese = 1;
		int giorno = 29;
		Date res = DateUtils.searchValidBefore(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertTrue(!DateUtils.isSameDay(res, DateUtils.getDate(anno, mese, giorno)));
		Assert.assertTrue(DateUtils.isSameDay(res, DateUtils.getDate(anno, mese, 28)));
	}
	
	@Test
	public void testSearchValidBeforeDataNonValida2() {
		logger.info("testExists");
		int anno = 2017;
		int mese = 10;
		int giorno = 31;
		Date res = DateUtils.searchValidBefore(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertTrue(!DateUtils.isSameDay(res, DateUtils.getDate(anno, mese, giorno)));
		Assert.assertTrue(DateUtils.isSameDay(res, DateUtils.getDate(anno, mese, 30)));
	}
	
	@Test
	public void testSearchValidBeforeDataValida() {
		logger.info("testExists");
		int anno = 2020;
		int mese = 1;
		int giorno = 29;
		Date res = DateUtils.searchValidBefore(anno, mese, giorno);		
		logger.info("{} {} {} = {}", anno, mese, giorno, res);
		Assert.assertTrue(DateUtils.isSameDay(res, DateUtils.getDate(anno, mese, giorno)));
		
	}	
}
