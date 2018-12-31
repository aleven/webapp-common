package it.attocchi.mail.utils;

import javax.mail.Message;

/**
 * <p>MailMessage class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class MailMessage {

	private String subject;
	private int messageNumber;
	
	private Message originalMessage = null;

	private MailMessage(Message message) {
		super();

		this.originalMessage = message;
	}

	/**
	 * <p>create.</p>
	 *
	 * @param message a {@link javax.mail.Message} object.
	 * @return a {@link it.attocchi.mail.utils.MailMessage} object.
	 */
	public static MailMessage create(Message message) {
		return new MailMessage(message);
	}

	/**
	 * <p>Getter for the field <code>subject</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * <p>Setter for the field <code>subject</code>.</p>
	 *
	 * @param subject a {@link java.lang.String} object.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * <p>Getter for the field <code>messageNumber</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMessageNumber() {
		return messageNumber;
	}

	/**
	 * <p>Setter for the field <code>messageNumber</code>.</p>
	 *
	 * @param messageNumber a int.
	 */
	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}

//	public String getSubject() throws MessagingException {
//		return originalMessage.getSubject();
//	}
//
//	public int getMessageNumber() {
//		return originalMessage.getMessageNumber();
//	}

}
