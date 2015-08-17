package org.abhishek.fileanalytics.constants;

public enum ConfigTypes {
	FILE("file"),
	DATABASE("database");

	private final String type;

	private ConfigTypes(String type) {
		this.type = type;
	}

	public String type() {
		return this.type;
	}
}
