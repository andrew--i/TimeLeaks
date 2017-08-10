package org.ai;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class TelegramBotHandler implements Route {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotHandler.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Update update = BotUtils.parseUpdate(request.body());
        Message message = update.message();
        LOGGER.debug("message Id: {}", message.messageId());
        LOGGER.debug("chat Id: {}", message.chat().id());
        return "ok";
    }
}
