package com.lsh.ip.tcp;

import java.net.InetSocketAddress;

import com.lsh.constant.ModbusConstants;
import com.lsh.handle.ModbusDecoder;
import com.lsh.handle.ModbusEncoder;
import com.lsh.handle.ProxyHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class TcpGateway {

    public void connect(Integer localPort, String targetHost, Integer targetPort) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(group);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.localAddress(new InetSocketAddress("0.0.0.0", localPort));

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                //数据包的最大长度、长度域的偏移量、长度域的长度
                pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(ModbusConstants.ADU_MAX_LENGTH, 4, 2));
                //编码
                pipeline.addLast("encoder", new ModbusEncoder());
                pipeline.addLast("decoder", new ModbusDecoder(true));
                pipeline.addLast("proxyHandler", new ProxyHandler(targetHost, targetPort));
            }
        });

        try {
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}