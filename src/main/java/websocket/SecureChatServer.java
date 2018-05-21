package websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * Created by TuWeiLiang on 2018/5/21.
 */
public class SecureChatServer extends ChatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group, context);
    }

    public static void main(String[] args) throws Exception {
        int port = 9999;
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        SslContext context = SslContext.newServerContext(selfSignedCertificate.certificate(), selfSignedCertificate.privateKey());

        final SecureChatServer endPoint = new SecureChatServer(context);
        ChannelFuture future = endPoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endPoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
