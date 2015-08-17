package org.abhishek.fileanalytics.constants;

public enum TimeZones {
	DEFAULT("GMT"),
	GMT("GMT");

	private final String timeZoneStr;

	private TimeZones(String timeZoneStr) {
		this.timeZoneStr = timeZoneStr;
	}

	public String zone() {
		return this.timeZoneStr;
	}
}
