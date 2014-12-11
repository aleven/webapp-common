package it.attocchi.jsf2.exceptions;

/**
 * 
 * @author Mirco
 *
 */
public class WebAppException extends Exception {

	public WebAppException(String message) {
		super(message);
	}

	public WebAppException(String message, Object... args) {
		super(String.format(message, args));
	}

	public WebAppException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}
	
}
