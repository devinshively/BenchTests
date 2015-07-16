package com.ipass.hellocomsat;

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
public class HelloComsat {

    public static void main(String[] args) throws Exception {
        final Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        server.setHandler(context);

        context.addServlet(new ServletHolder(new FiberHttpServlet() {
            @Override
            @Suspendable
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                resp.setStatus(200);
                resp.setHeader("Content-Type", "text/plain");
                resp.getWriter().print("Hello World!");
            }
        }), "/");
        server.start();
    }
}
