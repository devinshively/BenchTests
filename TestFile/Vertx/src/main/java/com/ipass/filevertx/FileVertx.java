package com.ipass.filevertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dshively on 7/6/15.
 */
public class FileVertx extends AbstractVerticle {
    public static final String DIR = "/Users/dshively/TestFile/";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("com.ipass.filevertx.FileVertx");
    }

    @Override
    public void start() throws Exception {
        final Router router = Router.router(vertx);

        // Enable the body parser to we can get the form data and json documents in out context.
        router.route().handler(BodyHandler.create());

        router.post("/sqm-list").handler(ctx -> {
            String postBody = ctx.getBodyAsString();
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + UUID.randomUUID();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIR + filename), "utf-8"))) {
                writer.write(postBody);
            } catch (Exception e) {
                System.out.println(e);
            }
            ctx.response().setStatusCode(200);
            ctx.response().end();
        });

        // start a HTTP web server on port 8080
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
