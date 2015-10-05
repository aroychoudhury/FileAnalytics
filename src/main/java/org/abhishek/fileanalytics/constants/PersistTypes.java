/* Copyright 2015 Roychoudhury, Abhishek */

package org.abhishek.fileanalytics.constants;

public enum PersistTypes {
	FILE("file"),
	DATABASE("database");

	private final String type;

	private PersistTypes(String type) {
		this.type = type;
	}

	public String type() {
		return this.type;
	}
}
