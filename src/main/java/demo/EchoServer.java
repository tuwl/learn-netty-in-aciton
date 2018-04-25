package demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by TuWeiLiang on 2018/4/24.
 */
public class EchoServer {
    private final int port;

    public EchoServer() {
        this.port = 8081;
    }

    public static void main(String[] args) throws Exception {
        new EchoServer().start();
    }

    public void start() throws Exception {
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(echoServerHandler);
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    " started and listen on " + channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
