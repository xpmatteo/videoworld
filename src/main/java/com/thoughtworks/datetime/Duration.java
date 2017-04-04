package com.thoughtworks.datetime;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Duration implements Comparable<Duration> {
	private static final Duration INFINITE = new Duration(null);

	private Integer days;

	private Duration(final Integer days) {
		this.days = days;
	}

	public static Duration infinite() {
		return INFINITE;
	}

	public static Duration ofDays(final int days) {
		if (days < 0) {
			throw new IllegalArgumentException("days must not be negative");
		}
		return new Duration(days);
	}

	public Integer getDays() {
		if (days == null) {
			throw new IllegalStateException("Duration is infinite");
		}
		return days;
	}

	public boolean isInfinite() {
		return (days == null);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(days).toHashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		final Duration other = (Duration) object;
		return new EqualsBuilder().append(days, other.days).isEquals();
	}

	public int compareTo(final Duration other) {
		if (this == other) {
			return 0;
		}
		if (days == null) {
			if (other.days == null) {
				return 0;
			}
			return 1;
		} else if (other.days == null) {
			return -1;
		} else {
			return days.compareTo(other.days);
		}
	}

	@Override
	public String toString() {
		if (days == null) {
			return "infinite";
		} else {
			return Integer.toString(days);
		}
	}

}
