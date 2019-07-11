package net.meteorr.dev.meteorrcomett.server.messaging;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.Objects;

public class MessagingEncryptionSetup {
    public static void main(String[] args) throws SSLException, InterruptedException {
        File cert = new File(Objects.requireNonNull(MessagingEncryptionSetup.class.getClassLoader().getResource("MeteorrComettCertificate.crt")).getFile());
        File key = new File(Objects.requireNonNull(MessagingEncryptionSetup.class.getClassLoader().getResource("MeteorrComettPrivateKey-PBE-SHA1-RC4-128.pem")).getFile());
        String pass = "B]</uhzcam_N?t`kcd4AqNjM&7\\-z_<+'5#my?XHCQz7(E:zSN_v$aX,>KFh~-=Z\"arN\"kg4rL$PN!A7D]$FD=^ZU,%)MT9]^;Wev#k'Tv;n+*`UA]M=\"!C`S:6?Cu$!:s&9FX^]'DdJQt>8`SduQD]tAcma)KCkULssp*VZ}Rs#kWt_tDGLN\"jH?u/j{F:r8t.ykfzXCt<NG+a_`X#~]=\"F8ZB?w,p*6*Hj#yS=w%\\\"H\\ttMmQaMdt6Fq4!D'\\eVjDhNDWG':^SQNvd.P,7Z$JRX36&:a[ZHr`zEakm^Q8P}\"%?w@mB{z3m6?k\\hwM-.RYLw^g,@'W{-+&S}w]sVEsnn)2Efb<&'qrX\\7F+-an5}tbYmn$My$PxC<3PYTm4hxw}b+n'>2u);hhA]sP{7,2cw^A3j[@mb.f6>Cu3V?p:%P}_v6&d%yU/Ntc<pbhhQh62F^#+Yb-f[kpFf4F\\BT*5DfBtb3&q'~K7z;LgCnyX)}jueAC:.a_L[3724L-3";
        System.out.println("Private key path: " + key.getAbsolutePath());
        System.out.println("Certificate path: " + cert.getAbsolutePath());
        System.out.println("Password bytes:" + pass.getBytes());
        SslContext context = SslContextBuilder.forServer(cert, key, pass).build();
        System.err.println("SSLContext Built successfully (après 1 semaine et demie) :");
        System.out.println(context.toString());
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new FactorialServerInitializer(context));
            b.bind(1192).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static class FactorialServerInitializer extends ChannelInitializer<SocketChannel> {

        private final SslContext sslCtx;

        public FactorialServerInitializer(SslContext sslCtx) {
            this.sslCtx = sslCtx;
        }

        @Override
        public void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();

            if (sslCtx != null) {
                pipeline.addLast(sslCtx.newHandler(ch.alloc()));
            }

            // Enable stream compression (you can remove these two if unnecessary)
            pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
            pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

            // Add the number codec first,
            //pipeline.addLast(new BigIntegerDecoder());
            //pipeline.addLast(new NumberEncoder());

            // and then business logic.
            // Please note we create a handler for every new channel
            // because it has stateful properties.
            //pipeline.addLast(new FactorialServerHandler());
        }
    }

}
