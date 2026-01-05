package threadfool.op.backendclient;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

public class BackendInitializer extends ChannelInitializer<SocketChannel>
{

	private final Channel frontendChannel;

	public BackendInitializer(Channel frontendChannel) {
		this.frontendChannel = frontendChannel;
	}

	@Override
	protected void initChannel(final SocketChannel socketChannel) throws Exception
	{
		socketChannel.pipeline().addLast(
				new HttpClientCodec(),
				new HttpObjectAggregator(1_048_576),
				new BackendHandler(frontendChannel)
		);
	}
}
