package cn.missbe.redis.client.net;

import cn.missbe.rdis.client.util.CommandProcessUtil;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class RedisClient {
    private Socket          socket;
    private BufferedReader  reader;
    private PrintStream     printStream;

    private RedisClient(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printStream = new PrintStream(socket.getOutputStream());
    }

    private void sendCommand(String command){
        printStream.println(command);
    }

    private String getMsg() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            PrintUtil.print("服务器端已经关闭或出现错误.结束访问", SystemLog.Level.error);
        }
        return "exit";
    }

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

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 65532);
        socket.setSoTimeout(10000); //timeout
        RedisClient redisClient = new RedisClient(socket);
        String command ;
        PrintUtil.print(socket.toString() + "客户端初始化成功.", SystemLog.Level.info);
        while(true){
           System.out.println("Please Input Command:");
           Scanner scanner = new Scanner(System.in);
           command = scanner.nextLine();

           ////退出客户端
            if(command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")){
                redisClient.sendCommand("quit");
                break; ///退出
            }
            ////检查命令格式是否正确
           if(!CommandProcessUtil.checkCommand(command)){
               System.out.println("命令格式不正确,请检查!");
               continue;
           }
           ///发送命令，得到回复
           redisClient.sendCommand(command);
           ///服务器返回的消息
           String msg = redisClient.getMsg();
           if(msg.equalsIgnoreCase("exit")){
               break; ///退出连接
           }
           System.out.println(msg);
       }
        redisClient.close(); ///close
        PrintUtil.print(socket.toString() + "终止与服务器连接.", SystemLog.Level.info);
    }
}
