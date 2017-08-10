package org.ai;

import org.slf4j.LoggerFactory;

import java.io.IOException;

import static spark.Spark.*;

public class Main {

	public static void main(String[] args) throws IOException {

		LoggerFactory.getLogger(Main.class).debug("Hello World!!!!!");


		final String port = System.getenv("PORT");
		port(Integer.valueOf(port != null ? port : "8080"));
		get("/bot", new TelegramBotHandler());
		get("/hello", (req, res) -> "Hello World!");
		post("/timeleaks", new SaveTimeLeaksHandler());
		options("/timeleaks", new CrossOptionsRoute());
	}
}
