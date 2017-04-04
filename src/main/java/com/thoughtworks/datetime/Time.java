package com.thoughtworks.datetime;

public interface Time {
    int hourOfDay();

    int minuteOfHour();

    int secondOfMinute();

    boolean isAfterNow();

    boolean isBeforeNow();
}
