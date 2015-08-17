package org.abhishek.fileanalytics.constants;

public enum MatchTypes {
	START('S'),
	END('E'),
	BOTH('B');

	private final char type;

	private MatchTypes(char type) {
		this.type = type;
	}

	public char type() {
		return this.type;
	}

	public boolean isStartMatcher() {
        return ('S' == this.type) || ('B' == this.type);
    }

	public boolean isEndMatcher() {
        return ('E' == this.type) || ('B' == this.type);
    }
}
