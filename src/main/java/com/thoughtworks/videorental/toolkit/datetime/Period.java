package com.thoughtworks.videorental.toolkit.datetime;

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

    public Duration getDuration() {
		return duration;
	}

	public LocalDate getEndDate() {
		return startDate.plusDays(duration.getDays() - 1);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!startDate.equals(period.startDate)) return false;
        return duration.equals(period.duration);

    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + duration.hashCode();
        return result;
    }
}
