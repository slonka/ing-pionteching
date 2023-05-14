package net.slonka.greencode;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import net.slonka.greencode.atmservice.domain.ATM;
import net.slonka.greencode.atmservice.domain.Task;
import net.slonka.greencode.atmservice.solver.ConvoyOrderSystem;
import net.slonka.greencode.onlinegame.domain.Group;
import net.slonka.greencode.onlinegame.domain.Players;
import net.slonka.greencode.onlinegame.solver.OnlineGameSolver;
import net.slonka.greencode.transactions.domain.Account;
import net.slonka.greencode.transactions.domain.Transaction;
import net.slonka.greencode.transactions.solver.TransactionSolver;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ServiceHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // fast path
        if (msg == LastHttpContent.EMPTY_LAST_CONTENT) {
            return;
        }
        channelReadSlowPath(ctx, msg);
    }

    private void channelReadSlowPath(ChannelHandlerContext ctx, Object msg) throws Exception {
        // slow path
        if (msg instanceof FullHttpRequest) {
            try {
                FullHttpRequest request = (FullHttpRequest) msg;
                process(ctx, request);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        }
    }

    protected void process(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();
        FullHttpResponse response;

        if ("/atms/calculateOrder".equals(uri) && method == HttpMethod.POST) {
            var tasks = JSON.parseObject(request.content().toString(StandardCharsets.UTF_8), Task[].class);
            List<ATM> orders = ConvoyOrderSystem.calculateOrder(tasks);
            var responseJson = JSON.toJSONString(orders);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(responseJson.getBytes(StandardCharsets.UTF_8)));
        } else if ("/transactions/report".equals(uri) && method == HttpMethod.POST) {
            var transactions = JSON.parseObject(request.content().toString(StandardCharsets.UTF_8), Transaction[].class);
            List<Account> report = TransactionSolver.processTransactions(transactions);
            String responseJson = JSON.toJSONString(report);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(responseJson.getBytes(StandardCharsets.UTF_8)));
        } else if ("/onlinegame/calculate".equals(uri) && method == HttpMethod.POST) {
            Players players = JSON.parseObject(new ByteBufInputStream(request.content()), Players.class);
            List<Group> order = OnlineGameSolver.calculateOrder(players.getGroupCount(), players.getClans());
            String responseJson = JSON.toJSONString(order);
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(responseJson.getBytes(StandardCharsets.UTF_8)));
        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        }

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        FullHttpResponse errorResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        cause.printStackTrace();
        ctx.writeAndFlush(errorResponse).addListener(ChannelFutureListener.CLOSE);
        ctx.close();
    }
}
