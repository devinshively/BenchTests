package com.ipass.vertxfile;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by dshively on 7/2/15.
 */
public class MongoEmbedded extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("com.ipass.vertxfile.MongoEmbedded");
    }

    @Override
    public void start() throws Exception {
        final MongoClient mongo = MongoClient.createShared(vertx, new JsonObject().put("db_name", "demo"));
        final Router router = Router.router(vertx);

        // Enable the body parser to we can get the form data and json documents in out context.
        router.route().handler(BodyHandler.create());

        router.get("/:ssid").handler(ctx -> {
            String ssid = ctx.request().getParam("ssid");

            mongo.find("networks", new JsonObject().put("ssid", ssid), lookup -> {

                if (lookup.failed()) {
                    ctx.fail(lookup.cause());
                    return;
                }

                ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                ctx.response().end(lookup.result().get(0).encode());
            });
        });

        // start a HTTP web server on port 8080
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

}
