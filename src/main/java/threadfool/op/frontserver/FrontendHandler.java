package threadfool.op.frontserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class FrontendHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
	@Override
	protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final FullHttpRequest fullHttpRequest)
			throws Exception
	{
		System.out.println("Got request:" + fullHttpRequest.method() + " "+ fullHttpRequest.uri());

		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK);

		channelHandlerContext.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
