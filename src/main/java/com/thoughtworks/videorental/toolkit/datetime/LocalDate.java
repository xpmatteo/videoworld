package com.thoughtworks.videorental.toolkit.datetime;

import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;

public class LocalDate implements Comparable<LocalDate> {
	private org.joda.time.LocalDate jodaLocalDate;

	private LocalDate(final org.joda.time.LocalDate date) {
		assert date != null;
		jodaLocalDate = date;
	}

	public java.util.Date getDate() {
		return jodaLocalDate.toDateMidnight().toDate();
	}

	public LocalDate minusDays(final int days) {
		return new LocalDate(jodaLocalDate.minusDays(days));
	}

	public LocalDate plusDays(final int days) {
		return new LocalDate(jodaLocalDate.plusDays(days));
	}

	public boolean isAfter(final LocalDate date) {
		return jodaLocalDate.isAfter(new org.joda.time.LocalDate(date.getDate()));
	}

	public boolean isBefore(final LocalDate date) {
		return jodaLocalDate.isBefore(new org.joda.time.LocalDate(date.getDate()));
	}

	public boolean isOnOrAfter(final LocalDate date) {
		return !isBefore(date);
	}

	public int compareTo(final LocalDate date) {
		return jodaLocalDate.compareTo(new org.joda.time.LocalDate(date.getDate()));
	}

	public Duration durationUntil(final LocalDate date) {
		if (isAfter(date)) {
			throw new IllegalArgumentException("cannot get duration to earlier date");
		}
		return Duration.ofDays(daysUntil(date));
	}

	private int daysUntil(final LocalDate date) {
		return new org.joda.time.Period(getDate().getTime(), date.getDate().getTime(), PeriodType.days()).getDays();
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof LocalDate))
			return false;
		final LocalDate other = (LocalDate) object;
		return this.jodaLocalDate.equals(other.jodaLocalDate);
	}

	@Override
	public int hashCode() {
		return jodaLocalDate.hashCode();
	}

	public String format(final String pattern) {
		return DateTimeFormat.forPattern(pattern).print(jodaLocalDate);
	}

	@Override
	public String toString() {
		return format("EEE MMM dd yyyy");
	}

	public static LocalDate today() {
		return new LocalDate(new org.joda.time.LocalDate());
	}

	public static LocalDate on(final int year, final int month, final int day) {
		return new LocalDate(new org.joda.time.LocalDate(year, month, day));
	}
}
