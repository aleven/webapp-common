package it.attocchi.test;

import it.attocchi.mail.parts.EmailBody;
import it.attocchi.mail.utils.MailUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Mirco on 17/05/16.
 */
public class TestMailUtils {

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
	public void testCleanDuplicates() throws Exception {
		String addresses = "test@email.com";

		System.out.println(MailUtils.cleanDuplicatesAndRemoveAddress2(addresses, ""));
		System.out.println(MailUtils.cleanDuplicatesAndRemoveAddress2(addresses, "test@email.com"));

		System.out.println("test@email.com".equalsIgnoreCase("test@email.com"));
	}
	
	@Test
	public void testBodyParser() throws Exception {
		File f = new File("/home/mirco/Downloads/message.eml");
		// MimeMessage mail = MimeMessageUtils.createMimeMessage(null, f);
		MimeMessage mail = new MimeMessage(null, new FileInputStream(f));
		EmailBody body = MailUtils.getBody(mail);
		
		Assert.assertNotNull(body);
	}
	
	@Test
	public void testGetAllRecipents() throws Exception {
		File f = new File("/home/mirco/Downloads/message(1).eml");
		// MimeMessage mail = MimeMessageUtils.createMimeMessage(null, f);
		MimeMessage mail = new MimeMessage(null, new FileInputStream(f));
		System.out.println(mail.getSubject());
		List<String> destinatari = MailUtils.getAllRecipents(mail);
		System.out.println(destinatari);
		Assert.assertNotNull(destinatari);
	}	
	
	
}
