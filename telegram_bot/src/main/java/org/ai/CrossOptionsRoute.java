package org.ai;


import spark.Request;
import spark.Response;
import spark.Route;

public class CrossOptionsRoute implements Route {
    @Override
    public Object handle(Request request, Response res) throws Exception {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "*");
        res.header("Access-Control-Allow-Methods", "POST");
        res.header("Access-Control-Allow-Headers", "Content-Type");
        return "ok";
    }

    public static Response allowCrossOrigin(Response response) {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Allow-Headers", "*");
        response.header("Access-Control-Allow-Methods", "POST");
        response.header("Content-Type", "application/json;charset=UTF-8");
        return response;
    }
}
