package org.ai;

import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SaveTimeLeaksHandler implements Route {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveTimeLeaksHandler.class);
    private TelegramBot telegamBot = getBot();
    private Map<String, BigDecimal> timeLeaks = new HashMap<>();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        CrossOptionsRoute.allowCrossOrigin(response);
        LOGGER.info("save leaks: " + request.body());
        final Map timeLeaks = new Gson().fromJson(request.body(), Map.class);
        saveTimeLeaks(timeLeaks);
        return "ok";
    }

    private void saveTimeLeaks(Map data) {
        for (Object k : data.keySet()) {
            String key = k.toString();
            if (key.equalsIgnoreCase("newTab"))
                continue;
            final BigDecimal dataValue = BigDecimal.valueOf(Double.parseDouble(data.get(key).toString()));
            if (timeLeaks.containsKey(key)) {
                timeLeaks.put(key, dataValue.add(timeLeaks.get(key)));
            } else
                timeLeaks.put(key, dataValue);
        }
    }

    private TelegramBot getBot() {
        if (telegamBot == null)
            telegamBot = createBot();
        return telegamBot;
    }

    private TelegramBot createBot() {
        final TelegramBot telegramBot = TelegramBotAdapter.build(System.getenv("BOT_TOKEN"));
        telegramBot.setUpdatesListener(list -> {
            for (Update update : list) {
                if (isInvalidUpdate(update))
                    continue;
                processUserMessage(update.message());
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
        return telegramBot;
    }

    private void processUserMessage(Message message) {
        getBot().execute(new SendMessage(message.chat().id(), new Gson().toJson(timeLeaks)));
    }

    private boolean isInvalidUpdate(Update update) {
        String userName = System.getenv("TELEGRAM_USERNAME");
        return !update.message().from().username().equalsIgnoreCase(userName);
    }


}
