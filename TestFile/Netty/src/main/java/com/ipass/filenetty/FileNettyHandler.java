package com.ipass.filenetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Created by dshively on 7/8/15.
 */
public class FileNettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public static final String DIR = "/Users/dshively/TestFile/";

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
//        ByteBuf in = (ByteBuf) msg;
//        String postBody = in.toString(CharsetUtil.UTF_8);
//        ReferenceCountUtil.release(msg);

            String postBodyString = msg.content().toString(CharsetUtil.UTF_8);
//            if (HttpHeaders.is100ContinueExpected(req)) {
//                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
//            }
            boolean keepAlive = false;//HttpHeaders.isKeepAlive(msg);
//            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("Hello World!".getBytes()));
//            response.headers().set(CONTENT_TYPE, "text/plain");
//            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
//

            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + UUID.randomUUID();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(DIR + filename), "utf-8"))) {
                writer.write(postBodyString);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
                ctx.write(response);
            }
//            msg.release();
        }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
