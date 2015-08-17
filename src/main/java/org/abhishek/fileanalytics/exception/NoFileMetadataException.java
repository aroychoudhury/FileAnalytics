package org.abhishek.fileanalytics.exception;

public class NoFileMetadataException extends RuntimeException {
	private static final long serialVersionUID = -3699418387940721508L;

	public NoFileMetadataException() {
		super();
	}

	public NoFileMetadataException(String message) {
		super(message);
	}

	public NoFileMetadataException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoFileMetadataException(Throwable cause) {
		super(cause);
	}
}
