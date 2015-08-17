package org.abhishek.fileanalytics.exception;

public class ConfigurationFailureException extends RuntimeException {
	private static final long serialVersionUID = -2125258754494219810L;

	public ConfigurationFailureException() {
		super();
	}

	public ConfigurationFailureException(String message) {
		super(message);
	}

	public ConfigurationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationFailureException(Throwable cause) {
		super(cause);
	}
}
