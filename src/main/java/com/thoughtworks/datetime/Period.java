package com.thoughtworks.datetime;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Period {
	private final LocalDate startDate;
	private final Duration duration;
	private final LocalDate endDate;

	protected Period(final LocalDate startDate, final Duration duration, final LocalDate endDate) {
		assert !startDate.isInfinite() || endDate.isInfinite();
		assert !duration.isInfinite() || endDate.isInfinite();
		assert endDate.isInfinite() || startDate.plusDays(duration.getDays() - 1).equals(endDate);
		this.startDate = startDate;
		this.duration = duration;
		this.endDate = endDate;
	}

	public static Period of(final LocalDate startDate, final Duration duration) {
		assert startDate != null;
		assert duration != null;
		final LocalDate endDate = startDate.plusDays(duration.getDays() - 1);
		return new Period(startDate, duration, endDate);
	}

	public static Period of(final LocalDate startDate, final LocalDate endDate) {
		assert startDate != null;
		assert endDate != null;
		final Duration exclusiveDuration = startDate.durationUntil(endDate);
		final Duration inclusiveDuration = exclusiveDuration.isInfinite() ? exclusiveDuration : Duration
				.ofDays(exclusiveDuration.getDays() + 1);
		return new Period(startDate, inclusiveDuration, endDate);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Duration getDuration() {
		return duration;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public boolean isOverlapping(final Period period) {
		return startDate.isOnOrBefore(period.getEndDate()) && endDate.isOnOrAfter(period.getStartDate());
	}

	public <T extends LocalDate> boolean containsAll(Set<T> dates) {
		for (LocalDate date : dates) {
			if (!(date.isOnOrAfter(startDate) && date.isOnOrBefore(endDate))) {
				return false;
			}
		}
		return true;
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
		return new EqualsBuilder().append(startDate, other.startDate).append(endDate, other.endDate).append(
				duration, other.duration).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(startDate).append(endDate).append(duration).toHashCode();
	}

}
