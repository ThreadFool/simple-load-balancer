package threadfool.op.frontserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import threadfool.op.backendclient.BackendInitializer;

public class FrontendHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
	@Override
	protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final FullHttpRequest fullHttpRequest)
	{
		System.out.println("Got request:" + fullHttpRequest.method() + " " + fullHttpRequest.uri());

		Channel fronendChannel = channelHandlerContext.channel();

		Bootstrap bootstrap = new Bootstrap();

		bootstrap.group(fronendChannel.eventLoop()).channel(NioSocketChannel.class).handler(
				new BackendInitializer(fronendChannel));

		bootstrap.connect("localhost", 9001).addListener((ChannelFuture f) -> {
			if (f.isSuccess()) {
				f.channel().writeAndFlush(fullHttpRequest);
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
