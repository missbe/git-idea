package cn.missbe.redis.slave.net;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.thread.RedisServerThread;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    /**
     * 服务器开始运行需要的相关操作
     */
    private  void initalEnvironmental(){
        ///加载Redis配置文件
        App.loadRedisConfig();

     //   TaskScheduleExecutor.startTaskScheduleExecutor();////开启缓存淘汰任务和持久化任务
    }

    public static void main(String[] args) throws IOException {
        RedisServer redisServer = new RedisServer(65532);
        redisServer.bind();
        PrintUtil.print(redisServer.serverSocket.toString() + "服务器初始化成功.", SystemLog.Level.info);

        ////初始化运行环境
        redisServer.initalEnvironmental();

        //noinspection InfiniteLoopStatement
        while (true){
            Socket socket = redisServer.getServerSocket().accept();
            new Thread(new RedisServerThread(socket)).start();
        }
    }
}
