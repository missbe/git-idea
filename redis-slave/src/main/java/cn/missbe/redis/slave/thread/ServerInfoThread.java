package cn.missbe.redis.slave.thread;

import cn.missbe.redis.slave.util.JsonConfigUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 下午2:38
 *   @author lyg
 *   @version 1.0
 *   @Description 处理客户端数据请求
 **/
public class ServerInfoThread implements Runnable {
    private Socket socket; ///保持数据传送的socket

    public ServerInfoThread(Socket socket) {
        PrintUtil.print(Thread.currentThread().getName() + "连接到备份服务器."  , SystemLog.Level.info);
        this.socket = socket;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try(
                PrintStream printStream = new PrintStream(socket.getOutputStream())
        ) {
            printStream.println(processInfo(JsonConfigUtils.getIpCluster()));
            printStream.flush();
            PrintUtil.print(Thread.currentThread().getName() + "备份服务器数据发送完毕，关闭流.."  , SystemLog.Level.info);
        } catch (IOException e) {
            PrintUtil.print(Thread.currentThread().getName() + "获取输出流异常，关闭该线程." + e.getMessage() , SystemLog.Level.error);
            Thread.currentThread().interrupt();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                PrintUtil.print(Thread.currentThread().getName() + "socket关闭异常，关闭该线程." + e.getMessage() , SystemLog.Level.error);
            }
        }
    }

    private String processInfo(List<String[]> serverinfo) {
        StringBuilder builder = new StringBuilder();
        for(String[] info : serverinfo) {
            String tmp = info[0] + ":" + info[1] + "-";
            builder.append(tmp);
        }
        return builder.substring(0,builder.length()-1);
    }

}



