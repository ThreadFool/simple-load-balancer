package threadfool.op.backendclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;

public class BackendHandler extends SimpleChannelInboundHandler<FullHttpResponse>
{
	private Channel frontendChannel;

	public BackendHandler(Channel frontendChannel)
	{
		this.frontendChannel = frontendChannel;
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext channelHandlerContext, final FullHttpResponse fullHttpResponse)
			throws Exception
	{
		ByteBuf contentCopy = channelHandlerContext.alloc().buffer();
		contentCopy.writeBytes(fullHttpResponse.content());

		FullHttpResponse frontResponse = new DefaultFullHttpResponse(fullHttpResponse.protocolVersion(),
				fullHttpResponse.status(), contentCopy);

		frontResponse.headers().setAll(fullHttpResponse.headers());
		frontResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, contentCopy.readableBytes());

		frontendChannel.writeAndFlush(frontResponse);

		channelHandlerContext.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	{
		// backend error -> frontend 502
	}
}
