package threadfool.op.frontserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Main
{
	public static void main(String[] args)
	{
		EventLoopGroup master = new NioEventLoopGroup(1);
		EventLoopGroup workers = new NioEventLoopGroup();

		try
		{
			ServerBootstrap bootstrap = new ServerBootstrap().group(master, workers).channel(NioServerSocketChannel.class).childHandler(new FrontendInitializer());

			bootstrap.bind(8080).sync().channel().closeFuture().sync();
		}
		catch (Exception e)
		{
			System.out.println("Kaboom");
		}
	}
}
