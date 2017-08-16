package org.ai.handler;

import com.google.gson.Gson;
import org.ai.repository.ITimeLeaksRepository;
import org.ai.model.DayTimeLeak;
import org.ai.util.TimeLeaksUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

public class SaveTimeLeaksHandler implements Route {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveTimeLeaksHandler.class);


    private ITimeLeaksRepository timeLeaksRepository;

    public SaveTimeLeaksHandler(ITimeLeaksRepository timeLeaksRepository) {
        this.timeLeaksRepository = timeLeaksRepository;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        CrossOptionsRoute.allowCrossOrigin(response);
        LOGGER.info("save leaks: " + request.body());
        final Map timeLeaks = new Gson().fromJson(request.body(), Map.class);
        saveTimeLeaks(timeLeaks);
        return "ok";
    }

    private void saveTimeLeaks(Map data) {

        final DayTimeLeak dayTimeLeak = TimeLeaksUtil.createDayTimeLeak(data);
        timeLeaksRepository.save(dayTimeLeak);
    }
}
