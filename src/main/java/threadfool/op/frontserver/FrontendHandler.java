package threadfool.op.frontserver;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class FrontendHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
	@Override
	protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final FullHttpRequest fullHttpRequest)
	{
		System.out.println("Got request:" + fullHttpRequest.method() + " "+ fullHttpRequest.uri());

		byte[]body = "OK\n".getBytes();

		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, channelHandlerContext.alloc().buffer().writeBytes(body));
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, body.length);
		channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
