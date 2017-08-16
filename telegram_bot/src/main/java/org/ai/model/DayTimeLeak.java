package org.ai.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class DayTimeLeak {
    private LocalDate date;
    private List<TimeLeak> timeLeaks = new ArrayList<>();

    public void addTimeLeak(TimeLeak timeLeak) {
        final LocalDate timeLeakDate = timeLeak.getTimestamp().toLocalDate();
        if (!timeLeakDate.isEqual(date))
            throw new IllegalArgumentException("Different dates: " + date.format(DateTimeFormatter.BASIC_ISO_DATE) + " vs " + timeLeakDate.format(DateTimeFormatter.BASIC_ISO_DATE));

        timeLeaks.add(timeLeak);
    }

    public boolean isSameDay(DayTimeLeak dayTimeLeak) {
        return dayTimeLeak.date.isEqual(date);
    }

    public void mergeWith(DayTimeLeak dayTimeLeak) {
        timeLeaks.addAll(dayTimeLeak.timeLeaks);
    }
}
