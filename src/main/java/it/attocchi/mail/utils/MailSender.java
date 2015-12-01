package it.attocchi.mail.utils;

import it.attocchi.mail.utils.items.MailHeader;
import it.attocchi.mail.utils.items.MailRecipient;
import it.attocchi.mail.utils.ssl.DummySSLSocketFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailSender {

	private static final String MAIL_SMTP_SSL_SOCKET_FACTORY_FALLBACK = "mail.smtp.ssl.socketFactory.fallback";
	private static final String MAIL_SMTP_SSL_SOCKET_FACTORY_CLASS = "mail.smtp.ssl.socketFactory.class";
	public static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
	private String hostName;
	private int port;

	private String fromAddress;
	private String fromName;

	private String authUser;
	private String authPassword;

	private boolean enableSSL;
	private boolean disableSSLCertCheck;

	private MailSender() {
		super();
	}

	public static MailSender createMailSender(String hostName, int port) {
		MailSender m = new MailSender();
		m.setHostName(hostName);
		m.setPort(port);

		return m;
	}

	public static MailSender createMailSenderAuth(String hostName, int port, String authUser, String authPassword) {
		MailSender m = new MailSender();
		m.setHostName(hostName);
		m.setPort(port);

		m.setAuthUser(authUser);
		m.setAuthPassword(authPassword);

		return m;
	}

	public static MailSender createMailSender(String hostName, int port, String fromAddress, String fromName) {
		MailSender m = createMailSender(hostName, port);

		if (StringUtils.isBlank(fromAddress))
			fromAddress = "";

		if (StringUtils.isBlank(fromName))
			fromName = "";

		m.setFromAddress(fromAddress);
		m.setFromName(fromName);

		return m;
	}

	public static MailSender createMailSender(String hostName, int port, String fromAddress, String fromName, String authUser, String authPassword) {
		MailSender m = createMailSender(hostName, port, fromAddress, fromName);

		m.setAuthUser(authUser);
		m.setAuthPassword(authPassword);

		return m;
	}

	public static MailSender createMailSender(String hostName, int port, String fromAddress, String fromName, boolean enableSSL, boolean disableSSLCertCheck, String authUser, String authPassword) {
		// MailSender m = createMailSender(hostName, port, fromAddress,
		// fromName);
		// m.setAuthUser(authUser);
		// m.setAuthPassword(authPassword);

		MailSender m = createMailSender(hostName, port, fromAddress, fromName, authUser, authPassword);

		m.setEnableSSL(enableSSL);
		m.setDisableSSLCertCheck(disableSSLCertCheck);

		return m;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getAuthUser() {
		return authUser;
	}

	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	public String getAuthPassword() {
		return authPassword;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public boolean isEnableSSL() {
		return enableSSL;
	}

	public void setEnableSSL(boolean enableSSL) {
		this.enableSSL = enableSSL;
	}

	public boolean isDisableSSLCertCheck() {
		return disableSSLCertCheck;
	}

	public void setDisableSSLCertCheck(boolean disableSSLCertCheck) {
		this.disableSSLCertCheck = disableSSLCertCheck;
	}

	private void prepareEmail(Email email, String to, String toCC, String toCCN, String subject, String message, List<MailHeader> customHeaders) throws Exception {

		// String host = "smtps.pec.aruba.it";
		// String host = "smtps.pec.aruba.it";
		// int port = 465;
		// int port = 487;

		email.setHostName(hostName);
		email.setSmtpPort(port);

		if (authUser != null && authPassword != null) {
			email.setAuthentication(authUser, authPassword);
		}

		Properties properties = System.getProperties();

		/* RESET */
		properties.setProperty(MAIL_SMTP_SSL_ENABLE, "false");
		// System.out.println(properties.get("mail.smtp.ssl.enable"));

		// properties.setProperty(Email.MAIL_PORT, smtpPort);
		// String sslSmtpPort = "465";
		// properties.setProperty(Email.MAIL_PORT, sslSmtpPort);
		// properties.setProperty(Email.MAIL_SMTP_SOCKET_FACTORY_PORT,
		// sslSmtpPort);
		// properties.setProperty(Email.MAIL_SMTP_SOCKET_FACTORY_CLASS,
		// "javax.net.ssl.SSLSocketFactory");
		// System.out.println(Email.MAIL_SMTP_SOCKET_FACTORY_CLASS + "=" +
		// properties.get(Email.MAIL_SMTP_SOCKET_FACTORY_CLASS));
		// properties.setProperty(Email.MAIL_SMTP_SOCKET_FACTORY_FALLBACK,
		// "false");
		// System.out.println(Email.MAIL_SMTP_SOCKET_FACTORY_FALLBACK + "=" +
		// properties.get(Email.MAIL_SMTP_SOCKET_FACTORY_FALLBACK));

		if (enableSSL) {
			email.setSSL(true);
			// email.setDebug(true);

			if (disableSSLCertCheck) {
				/* FIX: Verifica dei Certificati SSL */

				properties.setProperty(MAIL_SMTP_SSL_ENABLE, "true");
				properties.setProperty(MAIL_SMTP_SSL_SOCKET_FACTORY_CLASS, DummySSLSocketFactory.class.getName());
				// properties.setProperty(Email.MAIL_SMTP_SOCKET_FACTORY_CLASS,
				// DummySSLSocketFactory.class.getName());
				properties.setProperty(MAIL_SMTP_SSL_SOCKET_FACTORY_FALLBACK, "false");
				// properties.setProperty(Email.MAIL_SMTP_SOCKET_FACTORY_FALLBACK,
				// "false");
			}
		}

		email.setFrom(fromAddress, fromName);

		// email.setSmtpPort(port);

		// email.addTo("amirco@gmail.com", "Mirco Attocchi");

		List<MailRecipient> recipients = parseEmails(to);
		for (MailRecipient recipient : recipients) {
			// email.addTo(toEmail, toName);
			email.addTo(recipient.getToEmail(), recipient.getToName());
		}

		List<MailRecipient> recipientsCC = parseEmails(toCC);
		for (MailRecipient recipientCC : recipientsCC) {
			email.addCc(recipientCC.getToEmail(), recipientCC.getToName());
		}

		List<MailRecipient> recipientsCCN = parseEmails(toCCN);
		for (MailRecipient recipientCCN : recipientsCCN) {
			email.addBcc(recipientCCN.getToEmail(), recipientCCN.getToName());
		}

		// email.setSubject("Test message");
		// email.setMsg("This is a simple test of commons-email");
		email.setSubject(subject);
		email.setMsg(message);

		if (customHeaders != null) {
			for (MailHeader header : customHeaders) {
				if (StringUtils.isNotBlank(header.getValue()))
					email.addHeader(header.getName(), header.getValue());
			}
		}
	}

	public void sendMail(String to, String toCC, String toCCN, String subject, String message) throws Exception {
		SimpleEmail email = new SimpleEmail();

		prepareEmail(email, to, toCC, toCCN, subject, message, null);

		email.send();
	}

	public void sendMailHtml(String to, String toCC, String toCCN, String subject, String message, List<EmailAttachment> attachments) throws Exception {
		HtmlEmail email = new HtmlEmail();

		prepareEmail(email, to, toCC, toCCN, subject, message, null);
		email.setHtmlMsg(message);

		if (attachments != null) {
			for (EmailAttachment a : attachments) {
				email.attach(a);
			}
		}

		email.send();
	}

	public void sendMail(String to, String toCC, String toCCN, String subject, String message, List<EmailAttachment> attachments) throws Exception {
		sendMail(to, toCC, toCCN, subject, message, null, attachments, null);
	}

	public void sendMail(String to, String toCC, String toCCN, String subject, String message, List<MailHeader> customHeaders, File emlToStore) throws Exception {

		SimpleEmail email = new SimpleEmail();

		prepareEmail(email, to, toCC, toCCN, subject, message, customHeaders);

		email.send();

		storeOnEml(email, emlToStore);
	}

	public void sendMail(String to, String toCC, String toCCN, String subject, String message, List<MailHeader> customHeaders, List<EmailAttachment> attachments, File emlToStore) throws Exception {
		// Create the attachment
		// EmailAttachment attachment = new EmailAttachment();
		// attachment.setPath("mypictures/john.jpg");
		// attachment.setDisposition(EmailAttachment.ATTACHMENT);
		// attachment.setDescription("Picture of John");
		// attachment.setName("John");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();

		prepareEmail(email, to, toCC, toCCN, subject, message, customHeaders);

		// email.setHostName("mail.myserver.com");
		// email.addTo("jdoe@somewhere.org", "John Doe");
		// email.setFrom("me@apache.org", "Me");
		// email.setSubject("The picture");
		// email.setMsg("Here is the picture you wanted");
		//
		// // add the attachment
		// email.attach(attachment);

		if (attachments != null) {
			for (EmailAttachment a : attachments) {
				email.attach(a);
			}
		}

		// send the email
		email.send();

		storeOnEml(email, emlToStore);
	}

	private void storeOnEml(Email email, File emlToStore) throws Exception {
		if (emlToStore != null) {
			/* Tentativo di Salvataggio */
			MimeMessage mimeMessage = email.getMimeMessage();
			OutputStream os = new FileOutputStream(emlToStore);
			try {
				mimeMessage.writeTo(os);
			} catch (Exception ex) {
				throw ex;
			} finally {
				os.close();
			}
		}
	}

//	@Deprecated
//	private void sendHtmlEmail() throws Exception {
//		// Create the email message
//		HtmlEmail email = new HtmlEmail();
//		email.setHostName("mail.myserver.com");
//		email.addTo("jdoe@somewhere.org", "John Doe");
//		email.setFrom("me@apache.org", "Me");
//		email.setSubject("Test email with inline image");
//
//		// embed the image and get the content id
//		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
//		String cid = email.embed(url, "Apache logo");
//
//		// set the html message
//		email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid + "\"></html>");
//
//		// set the alternative message
//		email.setTextMsg("Your email client does not support HTML messages");
//
//		// send the email
//		email.send();
//	}

	/**
	 * Extract multi recipient from a string separed with ; or ,
	 * 
	 * @param toEmail
	 * @return
	 */
	public List<MailRecipient> parseEmails(String toEmail) {
		List<MailRecipient> res = new ArrayList<MailRecipient>();

		// "Mirco Attocchi" <amirco@gmail.com>,

		if (StringUtils.isNotBlank(toEmail)) {
			String splitter = ";";
			String[] addresses = null;

			if (toEmail.indexOf(splitter) >= 0) {
				addresses = toEmail.split(splitter);
			}

			splitter = ",";
			if (toEmail.indexOf(splitter) >= 0) {
				addresses = toEmail.split(splitter);
			}

			if (addresses != null) {
				for (String address : addresses) {
					res.add(new MailRecipient(address.trim(), null));
				}
			} else {
				res.add(new MailRecipient(toEmail, null));
			}
		}

		return res;
	}

	/**
	 * 
	 * @param files
	 * @return
	 */
	public static List<EmailAttachment> prepareAttachment(List<String> files) {
		List<EmailAttachment> res = null;

		if (files != null)
			res = prepareAttachment(files.toArray(new String[0]));

		return res;
	}

	/**
	 * Prepara la lista di attachments partendo dalla lista di nomi files
	 * 
	 * @param files
	 * @return
	 */
	public static List<EmailAttachment> prepareAttachment(String... files) {
		List<EmailAttachment> res = null;

		for (String fileName : files) {
			File file = new File(fileName);
			if (file.exists()) {
				if (res == null)
					res = new ArrayList<EmailAttachment>();

				EmailAttachment attachment = new EmailAttachment();
				attachment.setName(file.getName());
				attachment.setPath(file.getPath());
				attachment.setDisposition(EmailAttachment.ATTACHMENT);

				res.add(attachment);
			}
		}

		return res;
	}
}
