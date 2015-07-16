package com.ipass.hellovertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by dshively on 7/6/15.
 */
public class HelloVertx extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("com.ipass.hellovertx.HelloVertx");
    }

    @Override
    public void start() throws Exception {
        final Router router = Router.router(vertx);

        // Enable the body parser to we can get the form data and json documents in out context.
        router.route().handler(BodyHandler.create());

        router.get("/").handler(ctx -> {
            ctx.response().setStatusCode(200);
            ctx.response().end("Hello World!");
        });

        // start a HTTP web server on port 8080
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
