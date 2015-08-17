package org.abhishek.fileanalytics.constants;

public enum DateFormats {
	DEFAULT_PATTERN("dd-MM-yyyy hh:mm:ss"),
	SIMPLE_CHAR_PATTERN("ddMMyyyyhhmmss");

	private final String dateFormat;

	private DateFormats(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String format() {
		return this.dateFormat;
	}
}
