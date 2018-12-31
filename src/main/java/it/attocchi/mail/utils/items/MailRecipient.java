package it.attocchi.mail.utils.items;

// private boolean restoreOriginalSysProp;
// private Map<String, String> originalMailValues = new HashMap<String,
// String>();
/**
 * <p>MailRecipient class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class MailRecipient {
	String toEmail;
	String toName;

	/**
	 * <p>Constructor for MailRecipient.</p>
	 *
	 * @param toEmail a {@link java.lang.String} object.
	 * @param toName a {@link java.lang.String} object.
	 */
	public MailRecipient(String toEmail, String toName) {
		super();
		this.toEmail = toEmail;
		this.toName = toName;
	}

	/**
	 * <p>Getter for the field <code>toEmail</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getToEmail() {
		return toEmail;
	}

	/**
	 * <p>Setter for the field <code>toEmail</code>.</p>
	 *
	 * @param toEmail a {@link java.lang.String} object.
	 */
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * <p>Getter for the field <code>toName</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getToName() {
		return toName;
	}

	/**
	 * <p>Setter for the field <code>toName</code>.</p>
	 *
	 * @param toName a {@link java.lang.String} object.
	 */
	public void setToName(String toName) {
		this.toName = toName;
	}

}
