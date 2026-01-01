package threadfool.op.frontserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class FrontendInitializer extends ChannelInitializer<SocketChannel>
{
	@Override
	protected void initChannel(final SocketChannel socketChannel) throws Exception
	{
		ChannelPipeline pipeline = socketChannel.pipeline();

		pipeline.addLast(new HttpServerCodec());

		pipeline.addLast(new HttpObjectAggregator(1024 * 1024));

		pipeline.addLast(new FrontendHandler());
	}
}
