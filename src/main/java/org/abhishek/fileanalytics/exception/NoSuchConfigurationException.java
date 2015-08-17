package org.abhishek.fileanalytics.exception;

public class NoSuchConfigurationException extends RuntimeException {
	private static final long serialVersionUID = -3699418387940721508L;

	public NoSuchConfigurationException() {
		super();
	}

	public NoSuchConfigurationException(String message) {
		super(message);
	}

	public NoSuchConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchConfigurationException(Throwable cause) {
		super(cause);
	}
}
