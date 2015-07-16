package com.ipass.hellorxnetty;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;

import java.util.Map;

/**
 * Created by dshively on 7/13/15.
 */
public class HelloRxNetty {
    static final int DEFAULT_PORT = 8080;

    private final int port;

    public HelloRxNetty(int port) {
        this.port = port;
    }

    public HttpServer<ByteBuf, ByteBuf> createServer() {
        return RxNetty.createHttpServer(port, (request, response) -> {
//            printRequestHeader(request);
            return response.writeStringAndFlush("Hello World!");
        });
    }

    public void printRequestHeader(HttpServerRequest<ByteBuf> request) {
        System.out.println("New request received");
        System.out.println(request.getHttpMethod() + " " + request.getUri() + ' ' + request.getHttpVersion());
        for (Map.Entry<String, String> header : request.getHeaders().entries()) {
            System.out.println(header.getKey() + ": " + header.getValue());
        }
    }

    public static void main(final String[] args) {
        System.out.println("HTTP hello world server starting ...");
        new HelloRxNetty(DEFAULT_PORT).createServer().startAndWait();
    }
}
