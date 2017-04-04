package com.thoughtworks.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;

public class LocalDate implements Comparable<LocalDate> {
    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    };

    public enum Month {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    };

	protected org.joda.time.LocalDate jodaLocalDate;

	protected LocalDate(final org.joda.time.LocalDate date) {
		assert date != null;
		jodaLocalDate = date;
	}

	public java.util.Date getDate() {
		return jodaLocalDate.toDateMidnight().toDate();
	}

	public LocalDate toFutureDate() {
		return isAfterNow() ? this : LocalDate.daysAfterToday(1);
	}

	public Calendar toCalendar() {
		return jodaLocalDate.toDateMidnight().toCalendar(null);
	}

	public int getDayOfWeek() {
		return jodaLocalDate.getDayOfWeek();
	}

	public int getDayOfMonth() {
		return jodaLocalDate.getDayOfMonth();
	}

	public int getMonthOfYear() {
		return jodaLocalDate.getMonthOfYear();
	}

	public int getWeekOfYear() {
		return jodaLocalDate.getWeekOfWeekyear();
	}

	public Day day() {
		final int dayOfWeek = jodaLocalDate.getDayOfWeek();
		return Day.values()[dayOfWeek - 1];
	}

	public Month month() {
		final int monthOfYear = jodaLocalDate.getMonthOfYear();
		return Month.values()[monthOfYear - 1];
	}

	public int getYear() {
		return jodaLocalDate.getYear();
	}

	public LocalDate minusDays(final int days) {
		return new LocalDate(jodaLocalDate.minusDays(days));
	}

	public LocalDate plusMonths(final int months) {
		return new LocalDate(jodaLocalDate.plusMonths(months));
	}

	public LocalDate minusMonths(final int months) {
		return new LocalDate(jodaLocalDate.minusMonths(months));
	}

	public LocalDate plusDays(final int days) {
		return new LocalDate(jodaLocalDate.plusDays(days));
	}

	public boolean isLastDayOfMonth() {
		LocalDate date = plusDays(1);
		return date.isFirstDayOfMonth();
	}

	public boolean isFirstDayOfMonth() {
		return getDayOfMonth() == 1;
	}

	public boolean isWeekend() {
		if (day() == Day.SUNDAY || day() == Day.SATURDAY) {
			return true;
		}
		return false;
	}

	public LocalDate plusDuration(final Duration duration) {
		assert duration != null;
		return plusDays(duration.getDays());
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

	public boolean isOnOrBefore(final LocalDate date) {
		return !isAfter(date);
	}

	public boolean isAfterNow() {
		return jodaLocalDate.isAfter(new org.joda.time.LocalDate());
	}

	public boolean isBeforeNow() {
		return jodaLocalDate.isBefore(new org.joda.time.LocalDate());
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

	public static LocalDate parseDate(final String dateString, final String dateFormat) {
		if (StringUtils.isEmpty(dateString)) {
			return null;
		}
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		format.setLenient(false);
		try {
			return LocalDate.onDate(format.parse(dateString));
		} catch (final ParseException e) {
			return null;
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof LocalDate)) {
			return false;
		}
		final LocalDate other = (LocalDate) obj;
		return new EqualsBuilder().append(jodaLocalDate, other.jodaLocalDate).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(jodaLocalDate).toHashCode();
	}

	public String format(final String pattern) {
		return DateTimeFormat.forPattern(pattern).print(jodaLocalDate);
	}

	@Override
	public String toString() {
		return format("EEE MMM dd yyyy");
	}

	public boolean isBetween(final LocalDate start, final LocalDate end) {
		return isOnOrAfter(start) && isOnOrBefore(end);
	}

	public static LocalDate today() {
		return new LocalDate(new org.joda.time.LocalDate());
	}

	public static LocalDate on(final int year, final int month, final int day) {
		return new LocalDate(new org.joda.time.LocalDate(year, month, day));
	}

	public static LocalDate on(final int year, final Month month, final int day) {
		return new LocalDate(new org.joda.time.LocalDate(year, month.ordinal() + 1, day));
	}

	public static LocalDate onDate(final java.util.Date time) {
		return new LocalDate(new org.joda.time.LocalDate(time));
	}

	public static LocalDate daysAfterToday(final int days) {
		return LocalDate.today().plusDays(days);
	}

	public static LocalDate daysBeforeToday(final int days) {
		return LocalDate.today().minusDays(days);
	}

	public static LocalDate monthsAfterToday(final int months) {
		return LocalDate.today().plusMonths(months);
	}
}
