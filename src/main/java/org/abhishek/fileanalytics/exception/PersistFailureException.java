package org.abhishek.fileanalytics.exception;

public class PersistFailureException extends RuntimeException {
	private static final long serialVersionUID = -2125258754494219810L;

	public PersistFailureException() {
		super();
	}

	public PersistFailureException(String message) {
		super(message);
	}

	public PersistFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistFailureException(Throwable cause) {
		super(cause);
	}
}
