package it.attocchi.mail.parts;

import it.attocchi.mail.utils.MailUtils;

public class EmailBody {
	private String body;
	private boolean isHtml;
	private String contentType;

	public EmailBody(String body, String contentType) {
		this.body = body;
		this.contentType = contentType;

		this.isHtml = false;
		if (contentType != null && contentType.indexOf(MailUtils.CONTENT_TYPE_TEXT_HTML) > -1) {
			this.isHtml = true;
		}
	}

	public EmailBody(String body, boolean isHtml) {
		this.body = body;
		this.isHtml = isHtml;
	}

	// public EmailBody(String body, boolean isHtml, String charset) {
	// this(body, isHtml);
	// this.charset = charset;
	// }

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isHtml() {
		return isHtml;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return body;
	}
}
