package it.attocchi.jsf2.exceptions;

/**
 * <p>WebAppException class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class WebAppException extends Exception {

	/**
	 * <p>Constructor for WebAppException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public WebAppException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for WebAppException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param args a {@link java.lang.Object} object.
	 */
	public WebAppException(String message, Object... args) {
		super(String.format(message, args));
	}

	/**
	 * <p>Constructor for WebAppException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 * @param args a {@link java.lang.Object} object.
	 */
	public WebAppException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}
	
}
