package org.abhishek.fileanalytics.exception;

public class ValidationFailureException extends RuntimeException {
	private static final long serialVersionUID = 6545820950405159436L;

	public ValidationFailureException() {
		super();
	}

	public ValidationFailureException(String message) {
		super(message);
	}

	public ValidationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationFailureException(Throwable cause) {
		super(cause);
	}
}
