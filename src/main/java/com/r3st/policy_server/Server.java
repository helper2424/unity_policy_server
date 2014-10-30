package com.r3st.policy_server;

/**
 * Created with IntelliJ IDEA.
 * User: helper
 * Date: 5/5/13
 * Time: 4:07 AM
 * To change this template use File | Settings | File Templates.
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: helper
 * Date: 5/3/13
 * Time: 8:12 PM
 * To change this template use File | Settings | File Templates.
 */

public class Server {
	static public Logger logger = LogManager.getLogger(Server.class.getName());

    public static int request_length = 0;
    public static String request;
    public static String response = "<file-not-found/>";
    public static String S_REQUEST_PATH = "/unity_security_request.xml";
    public static String S_RESPONSE_PATH = "/unity_security_respond.xml";

    public static void Init() throws IOException {
        // Open an input stream
        InputStream fin = Server.class.getResourceAsStream(S_REQUEST_PATH);

        byte[] buf = new byte[200];
        request_length = fin.read(buf);

        fin.close();

        Server.logger.info("Request file length: " + request_length);

        request = new String(buf, 0, request_length);

        fin = Server.class.getResourceAsStream(S_RESPONSE_PATH);
        int response_len = fin.read(buf);

        Server.logger.info("Response file length: " + response_len);

        if (response_len != -1) {
            response = new String(buf, 0, response_len);
        } else throw new IOException("Response file not found: " + S_RESPONSE_PATH);

        fin.close();
    }

    static public void main(String[] args) {
		Properties props = System.getProperties();
		props.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        try {
            Init();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            Server.logger.info("Accept: "+channel.remoteAddress() + " to "+channel.localAddress());

                            Server.logger.info("Sending response to: "+channel.remoteAddress());

                            // Отправляем соответствующий ответ
                            ByteBuf out = channel.alloc().buffer(response.length());
                            out.discardReadBytes();
                            out.writeBytes(response.getBytes());
                            //Закрываем канал как только данные записаны
                            channel.writeAndFlush(out).addListener(ChannelFutureListener.CLOSE);
                        }
                    });


            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);

            bootstrap.bind(new InetSocketAddress(843)).sync();
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(8843)).sync();

            //Ожадаем закрытие серверного сокета
            future.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            logger.error(ex);
            System.exit(1);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}