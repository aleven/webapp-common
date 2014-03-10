package it.attocchi.mail.utils.items;

public class MailHeader {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MailHeader(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

}