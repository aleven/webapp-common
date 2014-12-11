package it.attocchi.jsf2.exceptions;

/**
 * 
 * @author Mirco
 *
 */
public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Object... args) {
		super(String.format(message, args));
	}

	public ValidationException(String message, Throwable cause, Object... args) {
		super(String.format(message, args), cause);
	}
	
}
