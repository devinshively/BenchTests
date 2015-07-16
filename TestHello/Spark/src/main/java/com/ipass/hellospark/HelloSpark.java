package com.ipass.hellospark;


import static spark.Spark.*;

/**
 * Created by dshively on 7/6/15.
 */
public class HelloSpark {
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        ipAddress(IP_ADDRESS);
        port(PORT);
        get("/", (request, response) -> {
            response.status(200);
            return "Hello World!";
        });
    }
}
