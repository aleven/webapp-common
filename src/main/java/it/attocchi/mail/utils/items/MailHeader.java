package it.attocchi.mail.utils.items;

/**
 * <p>MailHeader class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class MailHeader {

	private String name;
	private String value;

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * <p>Constructor for MailHeader.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public MailHeader(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

}
