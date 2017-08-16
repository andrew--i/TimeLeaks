package org.ai;

import org.ai.bot.TimeLeaksBot;
import org.ai.handler.CrossOptionsRoute;
import org.ai.handler.SaveTimeLeaksHandler;
import org.ai.repository.ITimeLeaksRepository;
import org.ai.repository.TimeLeaksRepository;

import java.io.IOException;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) throws IOException {

        final ITimeLeaksRepository timeLeaksRepository = initDatabase();
        initServer(timeLeaksRepository);

        ((Runnable) () -> initBot(timeLeaksRepository)).run();

    }

    private static ITimeLeaksRepository initDatabase() {
        return new TimeLeaksRepository();
    }

    private static TimeLeaksBot initBot(ITimeLeaksRepository timeLeaksRepository) {
        return new TimeLeaksBot(timeLeaksRepository);
    }

    private static void initServer(ITimeLeaksRepository timeLeaksRepository) {
        final String port = System.getenv("PORT");
        port(Integer.valueOf(port != null ? port : "8080"));
        post("/timeleaks", new SaveTimeLeaksHandler(timeLeaksRepository));
        options("/timeleaks", new CrossOptionsRoute());
    }


}
