package cn.missbe.redis.server.net;

import cn.missbe.redis.server.App;
import cn.missbe.redis.server.dao.FileDaoImpl;
import cn.missbe.redis.server.map.KeyValueNode;
import cn.missbe.redis.server.map.RedisMapImpl;
import cn.missbe.redis.server.task.TaskScheduleExecutor;
import cn.missbe.redis.server.thread.RedisServerThread;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:32
 *   @author lyg
 *   @version 1.0
 *   @Description 主服务器，负责接受来自客户端的命令和初始化服务器需要的数据
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

    @Contract(pure = true)
    private ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * 服务器开始运行需要的相关操作
     */
    private  void initalEnvironmental(){
        ///加载Redis配置文件
        App.loadRedisConfig();
        TaskScheduleExecutor.startTaskScheduleExecutor();////开启缓存淘汰任务和持久化任务

        ////从持久化文件中加载配置
        String fileName =  App.PORT + "_" + App.SAVE_FILENAME;
        Map<String, KeyValueNode> maps = FileDaoImpl.getInstance().read(fileName);
        Map<String, HashSet<KeyValueNode>> setMaps = FileDaoImpl.getInstance().read2Set(fileName);
        Map<String, ArrayList<KeyValueNode>> listMaps = FileDaoImpl.getInstance().read2list(fileName);
        if(maps != null && !maps.isEmpty()){
            PrintUtil.print("从持久化文件:" + fileName + "加载set命令的缓存数据.", SystemLog.Level.info);
            RedisMapImpl.RedisMapHolder.getInstance().addMaps(maps);
        }
        if(listMaps != null && !listMaps.isEmpty()){
            PrintUtil.print("从持久化文件:" + fileName + "加载lset命令的缓存数据.", SystemLog.Level.info);
            RedisMapImpl.RedisMapHolder.getInstance().addListMaps(listMaps);
        }
        if(setMaps != null && !setMaps.isEmpty()){
            PrintUtil.print("从持久化文件:" + fileName + "加载het命令的缓存数据.", SystemLog.Level.info);
            RedisMapImpl.RedisMapHolder.getInstance().addSetMaps(setMaps);
        }

    }

    public static void main(String[] args) throws IOException {
        RedisServer redisServer = new RedisServer(App.PORT);
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
