package it.attocchi.jsf2.exceptions;

/**
 * <p>ValidationException class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class ValidationException extends Exception {

	/**
	 * <p>Constructor for ValidationException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for ValidationException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param args a {@link java.lang.Object} object.
	 */
	public ValidationException(String message, Object... args) {
		super(String.format(message, args));
	}

	/**
	 * <p>Constructor for ValidationException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 * @param args a {@link java.lang.Object} object.
	 */
	public ValidationException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}
	
}
