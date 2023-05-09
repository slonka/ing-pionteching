package net.slonka.greencode.atmservice.http;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import net.slonka.greencode.atmservice.domain.Order;
import net.slonka.greencode.atmservice.domain.Task;
import net.slonka.greencode.atmservice.solver.ConvoyOrderSystem;

import java.util.List;

public class ATMsServiceHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final DslJson<Object> dslJson = new DslJson<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if ("/atms/calculateOrder".equalsIgnoreCase(request.uri()) && request.method() == HttpMethod.POST) {
            byte[] requestBody = new byte[request.content().readableBytes()];
            request.content().readBytes(requestBody);

            // Deserialize the request body to a list of tasks
            List<Task> tasks = dslJson.deserializeList(Task.class, requestBody, requestBody.length);

            // Calculate the order
            List<Order> order = ConvoyOrderSystem.calculateOrder(tasks);

            // Serialize the order to JSON
            JsonWriter writer = dslJson.newWriter();
            dslJson.serialize(writer, order);

            // Create the response
            ByteBuf content = Unpooled.copiedBuffer(writer.toString(), CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // Write the response and close the connection
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            // Return a 404 Not Found response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

