package com.ipass.helloundertow;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.Headers;

public class HelloUndertow {


    public static void main(final String[] args) {
    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(new PathHandler().addPrefixPath("/", exchange -> {
            exchange.getResponseHeaders().put(Headers.STATUS, "200 OK");
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("Hello World!");
        })).build();
    server.start();
    }
}

