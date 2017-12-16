package it.attocchi.jpa2;

public class JpaControllerException extends Exception {

	public JpaControllerException() {
		super();
	}

	public JpaControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public JpaControllerException(String message) {
		super(message);
	}

	public JpaControllerException(Throwable cause) {
		super(cause);
	}

}
