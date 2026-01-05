package threadfool.op.backendclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;

public class BackendHandler extends SimpleChannelInboundHandler<FullHttpResponse>
{

	@Override
	protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final FullHttpResponse fullHttpResponse)
			throws Exception
	{
		// 1. forward response
		// 2. flush
		// 3. close backend ctx
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// backend error -> frontend 502
	}
}
