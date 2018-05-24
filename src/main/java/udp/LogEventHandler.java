package udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by TuWeiLiang on 2018/5/24.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(logEvent.getReceived());
        stringBuilder.append(" [");
        stringBuilder.append(logEvent.getSource().toString());
        stringBuilder.append("] [");
        stringBuilder.append(logEvent.getLogfile());
        stringBuilder.append("] : ");
        stringBuilder.append(logEvent.getMsg());
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
