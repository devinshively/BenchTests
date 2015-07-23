package com.ipass.fileundertow;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.Headers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUndertow {
    public static final String PATH = "/sqm-list";
    public static final String DIR = "/Users/dshively/TestFile/";

    public static void main(final String[] args) {
    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(new PathHandler().addPrefixPath(PATH, exchange -> {
            exchange.startBlocking();
            InputStream postBodyStream = exchange.getInputStream();
            java.util.Scanner s = new java.util.Scanner(postBodyStream).useDelimiter("\\A");
            String postBody = s.hasNext() ? s.next() : "";
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + UUID.randomUUID();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIR + filename), "utf-8"))) {
                writer.write(postBody);
            }
            exchange.getResponseHeaders().put(Headers.STATUS, "200 OK");
        })).build();
    server.start();
    }
}

