package org.abhishek.fileanalytics.constants;

public enum FileTypes {
	FILE("file"),
	DIRECTORY("directory");

	private final String type;

	private FileTypes(String type) {
		this.type = type;
	}

	public String type() {
		return this.type;
	}
}
