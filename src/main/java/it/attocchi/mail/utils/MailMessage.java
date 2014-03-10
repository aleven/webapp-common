package it.attocchi.mail.utils;

import javax.mail.Message;

/**
 * 
 * @author Mirco
 * 
 */
public class MailMessage {

	private String subject;
	private int messageNumber;
	
	private Message originalMessage = null;

	private MailMessage(Message message) {
		super();

		this.originalMessage = message;
	}

	public static MailMessage create(Message message) {
		return new MailMessage(message);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getMessageNumber() {
		return messageNumber;
	}

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
