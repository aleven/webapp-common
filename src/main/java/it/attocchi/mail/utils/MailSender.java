package it.attocchi.mail.utils;

import it.attocchi.mail.utils.items.MailHeader;
import it.attocchi.mail.utils.items.MailRecipient;
import it.attocchi.mail.utils.ssl.DummySSLSocketFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * <p>MailSender class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class MailSender {

	private static final String MAIL_SMTP_SSL_SOCKET_FACTORY_FALLBACK = "mail.smtp.ssl.socketFactory.fallback";
	private static final String MAIL_SMTP_SSL_SOCKET_FACTORY_CLASS = "mail.smtp.ssl.socketFactory.class";
	/** Constant <code>MAIL_SMTP_SSL_ENABLE="mail.smtp.ssl.enable"</code> */
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

	/**
	 * <p>createMailSender.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 * @param port a int.
	 * @return a {@link it.attocchi.mail.utils.MailSender} object.
	 */
	public synchronized static MailSender createMailSender(String hostName, int port) {
		MailSender m = new MailSender();
		m.setHostName(hostName);
		m.setPort(port);

		return m;
	}

	/**
	 * <p>createMailSenderAuth.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 * @param port a int.
	 * @param authUser a {@link java.lang.String} object.
	 * @param authPassword a {@link java.lang.String} object.
	 * @return a {@link it.attocchi.mail.utils.MailSender} object.
	 */
	public synchronized static MailSender createMailSenderAuth(String hostName, int port, String authUser, String authPassword) {
		MailSender m = new MailSender();
		m.setHostName(hostName);
		m.setPort(port);

		m.setAuthUser(authUser);
		m.setAuthPassword(authPassword);

		return m;
	}

	/**
	 * <p>createMailSender.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 * @param port a int.
	 * @param fromAddress a {@link java.lang.String} object.
	 * @param fromName a {@link java.lang.String} object.
	 * @return a {@link it.attocchi.mail.utils.MailSender} object.
	 */
	public synchronized static MailSender createMailSender(String hostName, int port, String fromAddress, String fromName) {
		MailSender m = createMailSender(hostName, port);

		if (StringUtils.isBlank(fromAddress))
			fromAddress = "";

		if (StringUtils.isBlank(fromName))
			fromName = "";

		m.setFromAddress(fromAddress);
		m.setFromName(fromName);

		return m;
	}

	/**
	 * <p>createMailSender.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 * @param port a int.
	 * @param fromAddress a {@link java.lang.String} object.
	 * @param fromName a {@link java.lang.String} object.
	 * @param authUser a {@link java.lang.String} object.
	 * @param authPassword a {@link java.lang.String} object.
	 * @return a {@link it.attocchi.mail.utils.MailSender} object.
	 */
	public synchronized static MailSender createMailSender(String hostName, int port, String fromAddress, String fromName, String authUser, String authPassword) {
		MailSender m = createMailSender(hostName, port, fromAddress, fromName);

		m.setAuthUser(authUser);
		m.setAuthPassword(authPassword);

		return m;
	}

	/**
	 * <p>createMailSender.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 * @param port a int.
	 * @param fromAddress a {@link java.lang.String} object.
	 * @param fromName a {@link java.lang.String} object.
	 * @param enableSSL a boolean.
	 * @param disableSSLCertCheck a boolean.
	 * @param authUser a {@link java.lang.String} object.
	 * @param authPassword a {@link java.lang.String} object.
	 * @return a {@link it.attocchi.mail.utils.MailSender} object.
	 */
	public synchronized static MailSender createMailSender(String hostName, int port, String fromAddress, String fromName, boolean enableSSL, boolean disableSSLCertCheck, String authUser, String authPassword) {
		// MailSender m = createMailSender(hostName, port, fromAddress,
		// fromName);
		// m.setAuthUser(authUser);
		// m.setAuthPassword(authPassword);

		MailSender m = createMailSender(hostName, port, fromAddress, fromName, authUser, authPassword);

		m.setEnableSSL(enableSSL);
		m.setDisableSSLCertCheck(disableSSLCertCheck);

		return m;
	}

	/**
	 * <p>Getter for the field <code>hostName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * <p>Setter for the field <code>hostName</code>.</p>
	 *
	 * @param hostName a {@link java.lang.String} object.
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * <p>Getter for the field <code>port</code>.</p>
	 *
	 * @return a int.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * <p>Setter for the field <code>port</code>.</p>
	 *
	 * @param port a int.
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * <p>Getter for the field <code>fromAddress</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * <p>Setter for the field <code>fromAddress</code>.</p>
	 *
	 * @param fromAddress a {@link java.lang.String} object.
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * <p>Getter for the field <code>fromName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * <p>Setter for the field <code>fromName</code>.</p>
	 *
	 * @param fromName a {@link java.lang.String} object.
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * <p>Getter for the field <code>authUser</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAuthUser() {
		return authUser;
	}

	/**
	 * <p>Setter for the field <code>authUser</code>.</p>
	 *
	 * @param authUser a {@link java.lang.String} object.
	 */
	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	/**
	 * <p>Getter for the field <code>authPassword</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAuthPassword() {
		return authPassword;
	}

	/**
	 * <p>Setter for the field <code>authPassword</code>.</p>
	 *
	 * @param authPassword a {@link java.lang.String} object.
	 */
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	/**
	 * <p>isEnableSSL.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEnableSSL() {
		return enableSSL;
	}

	/**
	 * <p>Setter for the field <code>enableSSL</code>.</p>
	 *
	 * @param enableSSL a boolean.
	 */
	public void setEnableSSL(boolean enableSSL) {
		this.enableSSL = enableSSL;
	}

	/**
	 * <p>isDisableSSLCertCheck.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isDisableSSLCertCheck() {
		return disableSSLCertCheck;
	}

	/**
	 * <p>Setter for the field <code>disableSSLCertCheck</code>.</p>
	 *
	 * @param disableSSLCertCheck a boolean.
	 */
	public void setDisableSSLCertCheck(boolean disableSSLCertCheck) {
		this.disableSSLCertCheck = disableSSLCertCheck;
	}

	private synchronized void prepareEmail(Email email, String to, String toCC, String toCCN, String subject, String message, List<MailHeader> customHeaders) throws EmailException {

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

		List<MailRecipient> recipients = parseEmails(to);
		for (MailRecipient recipient : recipients) {
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
		if (StringUtils.isNotBlank(subject)) {
			email.setSubject(subject);
		}
		if (StringUtils.isNotBlank(message)) {
			email.setMsg(message);
		}
		if (customHeaders != null) {
			for (MailHeader header : customHeaders) {
				if (StringUtils.isNotBlank(header.getValue()))
					email.addHeader(header.getName(), header.getValue());
			}
		}
	}

	/**
	 * <p>sendMail.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @param toCC a {@link java.lang.String} object.
	 * @param toCCN a {@link java.lang.String} object.
	 * @param subject a {@link java.lang.String} object.
	 * @param message a {@link java.lang.String} object.
	 * @throws org.apache.commons.mail.EmailException if any.
	 */
	public synchronized void sendMail(String to, String toCC, String toCCN, String subject, String message) throws EmailException {
		SimpleEmail email = new SimpleEmail();

		prepareEmail(email, to, toCC, toCCN, subject, message, null);

		email.send();
	}

	/**
	 * <p>sendMailHtml.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @param toCC a {@link java.lang.String} object.
	 * @param toCCN a {@link java.lang.String} object.
	 * @param subject a {@link java.lang.String} object.
	 * @param message a {@link java.lang.String} object.
	 * @param attachments a {@link java.util.List} object.
	 * @throws org.apache.commons.mail.EmailException if any.
	 */
	public synchronized void sendMailHtml(String to, String toCC, String toCCN, String subject, String message, List<EmailAttachment> attachments) throws EmailException {
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

	/**
	 * <p>sendMail.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @param toCC a {@link java.lang.String} object.
	 * @param toCCN a {@link java.lang.String} object.
	 * @param subject a {@link java.lang.String} object.
	 * @param message a {@link java.lang.String} object.
	 * @param attachments a {@link java.util.List} object.
	 * @throws org.apache.commons.mail.EmailException if any.
	 * @throws java.io.IOException if any.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized void sendMail(String to, String toCC, String toCCN, String subject, String message, List<EmailAttachment> attachments) throws EmailException, IOException, MessagingException {
		sendMail(to, toCC, toCCN, subject, message, null, attachments, null);
	}

	/**
	 * <p>sendMail.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @param toCC a {@link java.lang.String} object.
	 * @param toCCN a {@link java.lang.String} object.
	 * @param subject a {@link java.lang.String} object.
	 * @param message a {@link java.lang.String} object.
	 * @param customHeaders a {@link java.util.List} object.
	 * @param emlToStore a {@link java.io.File} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.apache.commons.mail.EmailException if any.
	 * @throws java.io.IOException if any.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized String sendMail(String to, String toCC, String toCCN, String subject, String message, List<MailHeader> customHeaders, File emlToStore) throws EmailException, IOException, MessagingException {

		SimpleEmail email = new SimpleEmail();

		prepareEmail(email, to, toCC, toCCN, subject, message, customHeaders);

		email.send();

		storeOnEml(email, emlToStore);

		return email.getMimeMessage().getMessageID();
	}

	/**
	 * <p>sendMail.</p>
	 *
	 * @param to a {@link java.lang.String} object.
	 * @param toCC a {@link java.lang.String} object.
	 * @param toCCN a {@link java.lang.String} object.
	 * @param subject a {@link java.lang.String} object.
	 * @param message a {@link java.lang.String} object.
	 * @param customHeaders a {@link java.util.List} object.
	 * @param attachments a {@link java.util.List} object.
	 * @param emlToStore a {@link java.io.File} object.
	 * @return a {@link java.lang.String} object.
	 * @throws org.apache.commons.mail.EmailException if any.
	 * @throws java.io.IOException if any.
	 * @throws javax.mail.MessagingException if any.
	 */
	public synchronized String sendMail(String to, String toCC, String toCCN, String subject, String message, List<MailHeader> customHeaders, List<EmailAttachment> attachments, File emlToStore) throws EmailException, IOException, MessagingException {
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

		return email.getMimeMessage().getMessageID();
	}

	private void storeOnEml(Email email, File emlToStore) throws IOException, MessagingException {
		if (emlToStore != null) {
			/* Tentativo di Salvataggio */
			MimeMessage mimeMessage = email.getMimeMessage();
			OutputStream os = new FileOutputStream(emlToStore);
			try {
				mimeMessage.writeTo(os);
			} catch (IOException ioEx) {
				throw ioEx;
			} catch (MessagingException mesEx) {
				throw mesEx;
			} finally {
				os.close();
			}
		}
	}

	// @Deprecated
	// private void sendHtmlEmail() throws EmailException {
	// // Create the email message
	// HtmlEmail email = new HtmlEmail();
	// email.setHostName("mail.myserver.com");
	// email.addTo("jdoe@somewhere.org", "John Doe");
	// email.setFrom("me@apache.org", "Me");
	// email.setSubject("Test email with inline image");
	//
	// // embed the image and get the content id
	// URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
	// String cid = email.embed(url, "Apache logo");
	//
	// // set the html message
	// email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid +
	// "\"></html>");
	//
	// // set the alternative message
	// email.setTextMsg("Your email client does not support HTML messages");
	//
	// // send the email
	// email.send();
	// }

	/**
	 * Extract multi recipient from a string separed with ; or ,
	 *
	 * @param toEmail a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
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
	 * <p>prepareAttachment.</p>
	 *
	 * @param files a {@link java.util.List} object.
	 * @return a {@link java.util.List} object.
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
	 * @param files a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
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
