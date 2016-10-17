package it.attocchi.test;

import it.attocchi.mail.utils.PecParser2;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.util.MimeMessageUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Mirco on 17/05/16.
 */
public class TestPecParser2 {

	@BeforeClass
	public static void setUpClass() {

	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void estraiAllegati() throws Exception {
		File f = new File("/home/mirco/Downloads/message.eml");
		MimeMessage mail = MimeMessageUtils.createMimeMessage(null, f);
		PecParser2 parser = new PecParser2();
		parser.dumpPart(mail);
		Assert.assertNotNull(parser.getSegnaturaXml());
		Assert.assertNotNull(parser.getPostacertEmlSubject());
	}
}
