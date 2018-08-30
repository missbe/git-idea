package cn.missbe.redis.client.net;

import cn.missbe.redis.client.util.CommandProcessUtil;
import cn.missbe.redis.client.App;
import cn.missbe.util.IOUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午9:22
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class RedisClient {
    private Socket                 socket;
    private PrintStream            printStream;
    private BufferedInputStream    reader;

    private RedisClient(@NotNull Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedInputStream(socket.getInputStream());
        printStream = new PrintStream(socket.getOutputStream());
    }

    private void sendCommand(String command){
        printStream.println(command);
    }

    /**
     * 获取服务器返回的结果集，以字节形式返回
     * @return 结果字符串
     */
    @NotNull
    private String getResult() {
        try {
            return IOUtils.parseStream(reader, App.SERVER_OK);
        } catch (IOException e) {
            PrintUtil.print("服务器端已经关闭或出现错误.结束访问", SystemLog.Level.error);
        }

        return "exit";
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 65532);
        socket.setSoTimeout(10000); //timeout
        RedisClient redisClient = new RedisClient(socket);
        String command ;
        PrintUtil.print(socket.toString() + "客户端初始化成功.", SystemLog.Level.info);
        Scanner scanner = new Scanner(System.in);
        while(true){
           System.out.println("Please Input Command:");
           command = scanner.nextLine();

           ////退出客户端
            if(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")){
                redisClient.sendCommand("quit");
                break; ///退出
            }
            ////检查命令格式是否正确
           if(!CommandProcessUtil.checkCommand(command)){
               System.out.println("客户端：命令格式不正确,请检查.");
               continue;
           }

           ///发送命令，得到回复
           redisClient.sendCommand(command);

           ///服务器返回的消息
            String msg = redisClient.getResult();
           if(msg.equalsIgnoreCase("exit")){
               break; ///退出连接
           }
           System.out.println("RESULT:" + msg);
       }
        redisClient.close(); ///close
        PrintUtil.print(socket.toString() + "终止与服务器连接.", SystemLog.Level.info);
    }

    /**
     * 关闭socket和与它相关的流
     */
    private void close(){
        try {
            reader.close();
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            PrintUtil.print(socket.toString() + "客户端资源关闭失败！" + e.getLocalizedMessage(), SystemLog.Level.error);
        }
        printStream.close();
    }
}
