package it.attocchi.mail.utils.items;

// private boolean restoreOriginalSysProp;
// private Map<String, String> originalMailValues = new HashMap<String,
// String>();
public class MailRecipient {
	String toEmail;
	String toName;

	public MailRecipient(String toEmail, String toName) {
		super();
		this.toEmail = toEmail;
		this.toName = toName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

}