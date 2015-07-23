package com.ipass.filespark;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static spark.Spark.*;

/**
 * Created by dshively on 7/6/15.
 */
public class FileSpark {
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;
    public static final String DIR = "/Users/dshively/TestFile/";

    public static void main(String[] args) throws Exception {
        ipAddress(IP_ADDRESS);
        port(PORT);
        post("/sqm-list", (request, response) -> {
            String postBody = request.body();
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + UUID.randomUUID();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIR + filename), "utf-8"))) {
                writer.write(postBody);
            }
            response.status(200);
            return "";
        });
    }
}
