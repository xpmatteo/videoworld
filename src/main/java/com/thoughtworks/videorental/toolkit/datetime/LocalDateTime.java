package com.thoughtworks.videorental.toolkit.datetime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;

public class LocalDateTime implements Comparable<LocalDateTime> {
	private static boolean isSystemDateTimeFixed = false;

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

	public static LocalDateTime at(final int year, final LocalDate.Month month, final int day, final int hour, final int minute,
			final int second) {
		return new LocalDateTime(new DateTime(year, month.ordinal() + 1, day, hour, minute, second, 0));
	}

	public static LocalDateTime onDate(final java.util.Date time) {
		return new LocalDateTime(new DateTime(time).withMillisOfSecond(0));
	}

	public static LocalDateTime onDateAt(final LocalDate date, final int hour, final int minute, final int second) {
		final MutableDateTime dateTime = new MutableDateTime(date.getDate());
		dateTime.setHourOfDay(hour);
		dateTime.setMinuteOfHour(minute);
		dateTime.setSecondOfMinute(second);
		return new LocalDateTime(dateTime.toDateTime());
	}

	public java.util.Date getDate() {
		return jodaDateTime.toDate();
	}

	public boolean isAfterNow() {
		return jodaDateTime.isAfterNow();
	}

	public boolean isBeforeNow() {
		return jodaDateTime.isBeforeNow();
	}

	public int compareTo(final LocalDateTime dateTime) {
		return jodaDateTime.compareTo(dateTime.jodaDateTime);
	}

	public static void setSystemDateTime(final LocalDateTime dateTime) {
		DateTimeUtils.setCurrentMillisFixed(dateTime.jodaDateTime.getMillis());
		isSystemDateTimeFixed = true;
	}

	public static void resetSystemDateTime() {
		DateTimeUtils.setCurrentMillisSystem();
		isSystemDateTimeFixed = false;
	}

	public static boolean isSystemDateTimeFixed() {
		return isSystemDateTimeFixed;
	}

	public int daysUntil(final LocalDate date) {
		return new Period(getDate().getTime(), date.getDate().getTime(), PeriodType.days()).getDays();
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
