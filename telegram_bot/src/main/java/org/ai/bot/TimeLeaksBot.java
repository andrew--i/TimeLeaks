package org.ai.bot;

import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.ai.repository.ITimeLeaksRepository;

public class TimeLeaksBot {

    private TelegramBot telegamBot;
    private ITimeLeaksRepository timeLeaksRepository;

    public TimeLeaksBot(ITimeLeaksRepository timeLeaksRepository) {
        this.timeLeaksRepository = timeLeaksRepository;
        telegamBot = getBot();
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
        getBot().execute(new SendMessage(message.chat().id(), new Gson().toJson(timeLeaksRepository.all())));
    }

    private boolean isInvalidUpdate(Update update) {
        String userName = System.getenv("TELEGRAM_USERNAME");
        return !update.message().from().username().equalsIgnoreCase(userName);
    }
}
