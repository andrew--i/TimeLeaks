package org.ai.repository;

import org.ai.model.DayTimeLeak;
import org.ai.util.TimeLeaksUtil;

import java.util.ArrayList;
import java.util.List;

public class TimeLeaksRepository implements ITimeLeaksRepository {

    private List<DayTimeLeak> timeLeaks = new ArrayList<>();

    @Override
    public List<DayTimeLeak> all() {
        return timeLeaks;
    }

    @Override
    public void save(DayTimeLeak dayTimeLeak) {
        timeLeaks = TimeLeaksUtil.mergeDayTimeLeaks(timeLeaks, dayTimeLeak);
    }
}
