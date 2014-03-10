package it.attocchi.mail.parts;

import java.io.InputStream;

public class MailAttachmentUtil {
	/**
	 * 
	 */
	private String contentType;
	private InputStream stream;
	private String fileName;
	private int size;

	public MailAttachmentUtil() {
		super();
	}

	public MailAttachmentUtil(String mimeType, InputStream stream, String fileName, int size) {
		super();
		this.contentType = mimeType;
		this.stream = stream;
		this.fileName = fileName;
		this.size = size;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}