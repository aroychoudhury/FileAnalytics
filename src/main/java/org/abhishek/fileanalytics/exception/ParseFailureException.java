package org.abhishek.fileanalytics.exception;

public class ParseFailureException extends RuntimeException {
	private static final long serialVersionUID = -4177776848569931023L;

	public ParseFailureException() {
		super();
	}

	public ParseFailureException(String message) {
		super(message);
	}

	public ParseFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParseFailureException(Throwable cause) {
		super(cause);
	}
}
