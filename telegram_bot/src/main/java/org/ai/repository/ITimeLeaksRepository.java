package org.ai.repository;

import org.ai.model.DayTimeLeak;

import java.util.List;

public interface ITimeLeaksRepository {

    List<DayTimeLeak> all();

    void save(DayTimeLeak dayTimeLeak);
}
