package cn.missbe.redis.slave.thread;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.util.CommandProcessUtil;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

public class RedisServerThread implements Runnable {
    private Socket          socket;               ///定义当前线程所处理的Socket
    private BufferedReader  bufferedReader;      ///该线程对应的输入流
    private PrintStream     printStream;         ///该线程对应的输出流
    private boolean         isClose    = false; ///标记该线程是否应该关闭

    public RedisServerThread(@NotNull Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printStream = new PrintStream(socket.getOutputStream());
        PrintUtil.print(socket.toString() + "连接到服务器.", SystemLog.Level.info);
    }

    public void run() {
        String content;
        while((content = readFromClient()) != null){
            PrintUtil.print("command:" + content, SystemLog.Level.info);
            ////客户端关闭，关闭当前线程
            if(content.equalsIgnoreCase("quit")){
                Thread.currentThread().interrupt(); //close thread
            }
            ////检查客户端命令格式是否正确
            if(!CommandProcessUtil.checkCommand(content)){
                printStream.println("1002");
                continue; ///命令格式不正确
            }
            ////执行客户端发送的命令
            byte[] buff = CommandProcessUtil.processCommand(content);
            try {
                printStream.write(buff);
                printStream.write(App.SERVER_OK.getBytes(StandardCharsets.UTF_8));
//                if(!socket.isOutputShutdown())
//                    socket.shutdownOutput(); ///半关闭输出流，告诉客户端输出完成
            } catch (IOException e) {
                PrintUtil.print("服务器写数据到流中失败." + e.getMessage(), SystemLog.Level.error);
            }
            ///异常关闭当前线程
            if(isClose){
                Thread.currentThread().interrupt(); ///close thread
            }
        }
    }

    /**
     * 从线程对应的客户端读取输入
     * @return 客户端输入数据
     */
    @Nullable
    private String readFromClient(){
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            PrintUtil.print(socket.toString() + "读取失败-客户端可能已关闭.线程关闭中...." + e.getLocalizedMessage(), SystemLog.Level.error);
            isClose = true; ///关闭该线程
        }
        return null;
    }
}
