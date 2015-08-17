package org.abhishek.fileanalytics.constants;

public enum LoggerReservedChars {
	c('c', "Category of the logging event; usually defined as a class level attribute named log/logger"),
	d('d', "Date of the logging event"),
	m('m', "Application supplied message associated with the logging event"),
	n('n', "Platform dependent line separator character or characters"),
	p('p', "Priority of the logging event"),
	t('t', "Name of the thread that generated the logging event"),
	x('x', "NDC (nested diagnostic context) associated with the thread that generated the logging event"),
	percent('%', "%% outputs a single percent sign");

	private final char character;
	private final String description;

	private LoggerReservedChars(char character, String description) {
		this.character = character;
		this.description = description;
	}

	public char valueOf() {
		return this.character;
	}

	public String descriptionOf() {
        return this.description;
    }
}
