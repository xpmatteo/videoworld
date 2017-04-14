package com.thoughtworks.videorental.toolkit.datetime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;

public class LocalDateTime implements Comparable<LocalDateTime> {

	private DateTime jodaDateTime;

	protected LocalDateTime(final DateTime time) {
		assert time != null;
		jodaDateTime = time;
	}

	public static LocalDateTime now() {
		return new LocalDateTime(new DateTime().withMillisOfSecond(0));
	}

	public static LocalDateTime at(final int year, final int month, final int day, final int hour, final int minute,
			final int second) {
		return new LocalDateTime(new DateTime(year, month, day, hour, minute, second, 0));
	}

	public int compareTo(final LocalDateTime dateTime) {
		return jodaDateTime.compareTo(dateTime.jodaDateTime);
	}

	public static void setSystemDateTime(final LocalDateTime dateTime) {
		DateTimeUtils.setCurrentMillisFixed(dateTime.jodaDateTime.getMillis());
	}

	public static void resetSystemDateTime() {
		DateTimeUtils.setCurrentMillisSystem();
	}

	public String format(final String pattern) {
		return DateTimeFormat.forPattern(pattern).print(jodaDateTime);
	}

	public String toString() {
		return format("EEE MMM dd yyyy HH:mm:ss");
	}

	public boolean equals(final Object other) {
		if (!(other instanceof LocalDateTime)) {
			return false;
		}
		return jodaDateTime.equals(((LocalDateTime) other).jodaDateTime);
	}

	public int hashCode() {
		return jodaDateTime.hashCode();
	}
}
