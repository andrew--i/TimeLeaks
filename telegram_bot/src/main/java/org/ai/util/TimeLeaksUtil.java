package org.ai.util;

import org.ai.model.DayTimeLeak;
import org.ai.model.TimeLeak;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeLeaksUtil {

    public static DayTimeLeak createDayTimeLeak(Map data) {
        final DayTimeLeak dayTimeLeak = new DayTimeLeak();
        final String sTimestamp = data.get("timestamp").toString();
        final LocalDateTime timestamp = LocalDateTime.parse(sTimestamp.substring(0, sTimestamp.length() - 1));
        final Map leaks = (Map) data.get("duration");

        dayTimeLeak.setDate(timestamp.toLocalDate());
        for (Object k : leaks.keySet()) {
            String host = k.toString();
            BigDecimal duration = BigDecimal.valueOf(Double.parseDouble(leaks.get(k).toString()));
            final TimeLeak timeLeak = new TimeLeak(host, duration, timestamp);
            dayTimeLeak.addTimeLeak(timeLeak);
        }
        return dayTimeLeak;
    }

    public synchronized static List<DayTimeLeak> mergeDayTimeLeaks(List<DayTimeLeak> timeLeaks, DayTimeLeak dayTimeLeak) {
        if (dayTimeLeak == null)
            return timeLeaks;

        if (timeLeaks.isEmpty()) {
            timeLeaks.add(dayTimeLeak);
            return timeLeaks;
        }


        List<DayTimeLeak> result = new ArrayList<>();
        result.addAll(timeLeaks);

        for (DayTimeLeak timeLeak : result) {
            if (timeLeak.isSameDay(dayTimeLeak)) {
                timeLeak.mergeWith(dayTimeLeak);
            }
        }
        timeLeaks.clear();
        timeLeaks.addAll(result);
        return timeLeaks;
    }
}
