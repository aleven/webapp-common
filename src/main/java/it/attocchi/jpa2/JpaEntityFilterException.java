package it.attocchi.jpa2;

/**
 * <p>JpaEntityFilterException class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class JpaEntityFilterException extends Exception {

	/**
	 * <p>Constructor for JpaEntityFilterException.</p>
	 */
	public JpaEntityFilterException() {
		super();
	}

	/**
	 * <p>Constructor for JpaEntityFilterException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public JpaEntityFilterException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for JpaEntityFilterException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public JpaEntityFilterException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for JpaEntityFilterException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public JpaEntityFilterException(Throwable cause) {
		super(cause);
	}

}
