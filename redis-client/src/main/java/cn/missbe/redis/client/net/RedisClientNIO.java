package cn.missbe.redis.client.net;

import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class RedisClientNIO {
    private Selector selector = null;          //轮询器
    static final int PORT = 3000;        ///端口
    static final String IP = "127.0.0.1";  ///端口
    private SocketChannel sc = null;        ///套接字通道
    private Charset charset = Charset.forName("UTF-8"); ///定义编码解码的字符集

    public void start() throws IOException {
        selector = Selector.open();
        sc = SocketChannel.open(new InetSocketAddress(IP, PORT));////连接到指定主机
        sc.configureBlocking(false); ///配置以非阻塞的方式读写
        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        new ClientThread().start(); ///启动读写服务器端数据线程

        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            sc.write(charset.encode(line));
        }
    }

    private class ClientThread extends Thread {
        @Override
        public void run() {
            try {
                while (selector.select() > 0) {
                    for (SelectionKey sk : selector.selectedKeys()) {
                        selector.selectedKeys().remove(sk); ///删除正在处理的key
                        if (sk.isReadable()) {
                            try {
                                read(sk);
                            } catch (IOException e) {
                                PrintUtil.print("客户端读取内容出错." + e.getLocalizedMessage(), SystemLog.Level.error);
                                Thread.currentThread().interrupt();
                            }
                            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        }
                    }
                }
            } catch (IOException e) {
                PrintUtil.print("客户端线程轮询出错." + e.getLocalizedMessage(), SystemLog.Level.error);
            }
        }

        private void read(SelectionKey sk) throws IOException {
            SocketChannel socketChannel = (SocketChannel) sk.channel();
            ByteBuffer buff = ByteBuffer.allocate(1024);
            String content = "";

            while (socketChannel.read(buff) > 0) {
                buff.flip();
                content += charset.decode(buff);
            }

            System.out.println("客户端读取的内容：" + content);
        }
    }

    public static void main(String[] args) throws IOException {
        new RedisClientNIO().start();
    }
}
