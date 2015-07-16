package com.ipass.helloratpack;

import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

/**
 * Created by dshively on 7/13/15.
 */
public class HelloRatpack {
    public static void main(String[] args) throws Exception {
        RatpackServer server = RatpackServer.of(b -> b
                        .serverConfig(ServerConfig.embedded().port(8080))           //default config
//                        .registryOf(r -> r.add(String.class, "world"))              // registry of supporting objects - optional
                        .handlers(chain -> chain                                    // request handlers - required
                                        .get(ctx -> ctx.render("Hello world!"))
                        )
        );

        server.start();
    }
}
