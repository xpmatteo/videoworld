package com.thoughtworks.datetime;

import java.util.Calendar;

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

	public static LocalDateTime atMidnightDaysBeforeToday(final int days) {
		return onDateAt(LocalDate.daysBeforeToday(days), 0, 0, 0);
	}

	public static LocalDateTime atMidnightDaysAfterToday(final int days) {
		return onDateAt(LocalDate.daysAfterToday(days), 0, 0, 0);
	}

	public static LocalDateTime atDaysBeforeToday(final int days) {
		return onDateAt(LocalDate.daysBeforeToday(days), now().hourOfDay(), now().minuteOfHour(), now()
				.secondOfMinute());
	}

	public static LocalDateTime hoursBeforeNow(final int hours) {
		return now().minusHours(hours);
	}

	public static LocalDateTime hoursAfterNow(final int hours) {
		return now().plusHours(hours);
	}

	public LocalDate toLocalDate() {
		return new LocalDate(jodaDateTime.toDateMidnight().toLocalDate());
	}

	public java.util.Date getDate() {
		return jodaDateTime.toDate();
	}

	public Calendar toCalendar() {
		return jodaDateTime.toCalendar(null);
	}

	public int getDayOfWeek() {
		return jodaDateTime.getDayOfWeek();
	}

	public int getDayOfMonth() {
		return jodaDateTime.getDayOfMonth();
	}

	public int getMonthOfYear() {
		return jodaDateTime.getMonthOfYear();
	}

	public int getWeekOfYear() {
		return jodaDateTime.getWeekOfWeekyear();
	}

	public LocalDate.Day day() {
		final int dayOfWeek = jodaDateTime.getDayOfWeek();
		return LocalDate.Day.values()[dayOfWeek - 1];
	}

	public LocalDate.Month month() {
		final int monthOfYear = jodaDateTime.getMonthOfYear();
		return LocalDate.Month.values()[monthOfYear - 1];
	}

	public int getYear() {
		return jodaDateTime.getYear();
	}

	public int hourOfDay() {
		return jodaDateTime.getHourOfDay();
	}

	public int minuteOfHour() {
		return jodaDateTime.getMinuteOfHour();
	}

	public int secondOfMinute() {
		return jodaDateTime.getSecondOfMinute();
	}

	public LocalDateTime plusDays(final int days) {
		return new LocalDateTime(jodaDateTime.plusDays(days));
	}

	public LocalDateTime minusDays(final int days) {
		return new LocalDateTime(jodaDateTime.minusDays(days));
	}

	public LocalDateTime plusHours(final int hours) {
		return new LocalDateTime(jodaDateTime.plusHours(hours));
	}

	public LocalDateTime minusHours(final int hours) {
		return new LocalDateTime(jodaDateTime.minusHours(hours));
	}

	public LocalDateTime plusMinutes(final int minutes) {
		return new LocalDateTime(jodaDateTime.plusMinutes(minutes));
	}

	public LocalDateTime minusMinutes(final int minutes) {
		return new LocalDateTime(jodaDateTime.minusMinutes(minutes));
	}

	public LocalDateTime plusSeconds(final int seconds) {
		return new LocalDateTime(jodaDateTime.plusSeconds(seconds));
	}

	public LocalDateTime minusSeconds(final int seconds) {
		return new LocalDateTime(jodaDateTime.minusSeconds(seconds));
	}

	public boolean isAfter(final LocalDate date) {
		return jodaDateTime.isAfter(new DateTime(date.getDate()));
	}

	public boolean isBefore(final LocalDate date) {
		return jodaDateTime.isBefore(new DateTime(date.getDate()));
	}

	public boolean isOnOrAfter(final LocalDate date) {
		return !isBefore(date);
	}

	public boolean isOnOrBefore(final LocalDate date) {
		return !isAfter(date);
	}

	public boolean isAfterNow() {
		return jodaDateTime.isAfterNow();
	}

	public boolean isBeforeNow() {
		return jodaDateTime.isBeforeNow();
	}

	public boolean isBetween(final LocalDate start, final LocalDate end) {
		return isOnOrAfter(start) && isOnOrBefore(end);
	}

	public int compareTo(final LocalDateTime dateTime) {
		return jodaDateTime.compareTo(dateTime.jodaDateTime);
	}

	public boolean isInfinite() {
		return false;
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
