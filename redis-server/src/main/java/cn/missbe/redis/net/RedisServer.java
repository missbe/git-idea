package cn.missbe.redis.net;

import cn.missbe.redis.thread.RedisServerThread;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {
    private int backlog;
    private int port;
    private ServerSocket serverSocket;

    private RedisServer(int port) {
       this(port,680);
    }

    private RedisServer(int port, int backlog) {
        this.backlog = backlog;
        this.port = port;
    }

    /**
     * 将服务器绑定到本机IP和端口上
     */
    private void bind(){
        try {
            this.serverSocket = new ServerSocket(port,backlog);
        } catch (IOException e) {
            PrintUtil.print("服务器绑定IP和商品失败" + e.getLocalizedMessage(), SystemLog.Level.error);
        }
    }

    private ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void main(String[] args) throws IOException {
        RedisServer redisServer = new RedisServer(65532);
        redisServer.bind();
        PrintUtil.print(redisServer.serverSocket.toString() + "服务器初始化成功.", SystemLog.Level.info);
        while (true){
            Socket socket = redisServer.getServerSocket().accept();
            new Thread(new RedisServerThread(socket)).start();
        }
    }
}
