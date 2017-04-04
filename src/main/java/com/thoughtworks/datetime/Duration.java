package com.thoughtworks.datetime;

public final class Duration implements Comparable<Duration> {
	private Integer days;

	private Duration(final Integer days) {
		this.days = days;
	}

	public static Duration ofDays(final int days) {
		if (days < 0) {
			throw new IllegalArgumentException("days must not be negative");
		}
		return new Duration(days);
	}

	public Integer getDays() {
		return days;
	}

	@Override
	public int hashCode() {
		return days.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Duration))
			return false;
		final Duration other = (Duration) object;
		return this.days.equals(other.days);
	}

	public int compareTo(final Duration other) {
		return this.days.compareTo(other.days);
	}

	@Override
	public String toString() {
		return Integer.toString(days);
	}

}
