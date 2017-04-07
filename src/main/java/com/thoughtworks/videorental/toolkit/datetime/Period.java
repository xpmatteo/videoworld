package com.thoughtworks.videorental.toolkit.datetime;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Period {
	private final LocalDate startDate;
	private final Duration duration;

	protected Period(final LocalDate startDate, final Duration duration) {
		assert startDate != null;
		assert duration != null;
		this.startDate = startDate;
		this.duration = duration;
	}

	public static Period of(final LocalDate startDate, final Duration duration) {
		return new Period(startDate, duration);
	}

	public static Period of(final LocalDate startDate, final LocalDate endDate) {
		final Duration exclusiveDuration = startDate.durationUntil(endDate);
		final Duration inclusiveDuration = Duration.ofDays(exclusiveDuration.getDays() + 1);
		return new Period(startDate, inclusiveDuration);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Duration getDuration() {
		return duration;
	}

	public LocalDate getEndDate() {
		return startDate.plusDays(duration.getDays() - 1);
	}

	public boolean isOverlapping(final Period period) {
		return startDate.isOnOrBefore(period.getEndDate()) && getEndDate().isOnOrAfter(period.getStartDate());
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		final Period other = (Period) obj;
		return new EqualsBuilder().append(startDate, other.startDate).append(duration, other.duration).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(startDate).append(duration).toHashCode();
	}
}
