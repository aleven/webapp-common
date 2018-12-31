package it.attocchi.jpa2;

/**
 * <p>JpaControllerException class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class JpaControllerException extends Exception {

	/**
	 * <p>Constructor for JpaControllerException.</p>
	 */
	public JpaControllerException() {
		super();
	}

	/**
	 * <p>Constructor for JpaControllerException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public JpaControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for JpaControllerException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public JpaControllerException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for JpaControllerException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public JpaControllerException(Throwable cause) {
		super(cause);
	}

}
