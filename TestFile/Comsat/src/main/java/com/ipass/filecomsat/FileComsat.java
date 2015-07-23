package com.ipass.filecomsat;

import co.paralleluniverse.fibers.Suspendable;
import co.paralleluniverse.fibers.servlet.FiberHttpServlet;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by dshively on 7/7/15.
 */
public class FileComsat {
    public static final String DIR = "/Users/dshively/TestFile/";

    public static void main(String[] args) throws Exception {
        final Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        context.addServlet(new ServletHolder(new FiberHttpServlet() {
            @Override
            @Suspendable
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                InputStream postBodyStream = req.getInputStream();
                java.util.Scanner s = new java.util.Scanner(postBodyStream).useDelimiter("\\A");
                String postBody = s.hasNext() ? s.next() : "";
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + UUID.randomUUID();
                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(DIR + filename), "utf-8"))) {
                    writer.write(postBody);
                }
                resp.setStatus(200);
            }
        }), "/sqm-list");
        context.addServlet(new ServletHolder(new FiberHttpServlet() {
            @Override
            @Suspendable
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                getServletConfig().getServletContext().getRequestDispatcher("/sqm-list").forward(req, resp);
            }
        }), "/sqm-list/");
        server.start();
    }
}
