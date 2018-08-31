package cn.missbe.redis.server.net;

import cn.missbe.redis.server.util.CommandProcessUtil;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:32
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class RedisServerNIO {
    private static final int PORT   = 3000;         ///服务器端口
    static final String IP          = "127.0.0.1";  ///端口
    private Selector selector       = null; ///用于检测所有Channel状态的Selector
    private Charset charset         = Charset.forName("UTF-8"); ///定义编码解码的字符集

    public void listen() throws IOException {
        selector = Selector.open();  ///初始化轮询器
        ServerSocketChannel server = ServerSocketChannel.open(); ///通过open方法打开一个未被绑定的实例
        InetSocketAddress inetAddress = new InetSocketAddress(IP, PORT);
        server.bind(inetAddress); ////将ServerSocketChannel绑定到指定IP地址
        server.configureBlocking(false); ///配置非阻塞方式工作
        server.register(selector, SelectionKey.OP_ACCEPT);///将server注册到指定的轮询器
        while (selector.select() > 0){
            //依次处理轮询器上的每个已选择的SelectionKey
            for (SelectionKey key : selector.selectedKeys()){
                selector.selectedKeys().remove(key); ////因为轮询器只负责添加，所以删除正在处理的key
                ////如果是客户端连接请求
                if(key.isAcceptable()){
                    SocketChannel socketChannel = server.accept(); ////调用accept方法接受连接，产生服务端的SocketChannel
                    socketChannel.configureBlocking(false); ///配置成非阻塞式
                    socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE); ///注册到轮询器
                    key.interestOps(SelectionKey.OP_ACCEPT); ////将key设置成准备成接收其他请求
                    PrintUtil.print(socketChannel + "连接到服务器.", SystemLog.Level.info);
                }
                if(key.isReadable()){
                    try {
                        /**
                         * 如果读取过程出现异常，表明对应的客户端出现问题
                         * 所以从轮询器取消key的注册
                         */
                        read(key);
                    } catch (IOException e) {
                        key.cancel(); ///从轮询器中删除指定的key
                        if(key.channel() != null){
                            key.channel().close();
                        }
                        PrintUtil.print(key+"读取失败，关闭当前key.", SystemLog.Level.error);
                    }
                    key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE); ///改变自身关注事件
                }
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String contents = "";
        while (channel.read(buffer) > 0){
            buffer.flip();
            contents += charset.decode(buffer);
        }
        System.out.println("服务器端读取到的数据:" + contents);
        byte[] res = CommandProcessUtil.processCommand(contents);
        channel.write(ByteBuffer.wrap(res));
    }

    public static void main(String[] args) throws IOException {
        new RedisServerNIO().listen();
    }
}
