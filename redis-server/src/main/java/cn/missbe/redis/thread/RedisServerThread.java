package cn.missbe.redis.thread;

import cn.missbe.redis.task.TaskScheduleExecutor;
import cn.missbe.redis.util.CommandProcessUtil;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class RedisServerThread implements Runnable {
    private Socket socket;                       ///定义当前线程所处理的Socket
    private BufferedReader bufferedReader;      ///该线程对应的输入流
    private PrintStream printStream;            ///该线程对应的输出流
    private boolean isClose         = false;   ///标记该线程是否应该关闭

    public RedisServerThread(Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printStream = new PrintStream(socket.getOutputStream());
        PrintUtil.print(socket.toString() + "连接到服务器.", SystemLog.Level.info);
        PrintUtil.print("缓存定时移除任务开启..", SystemLog.Level.info);
        TaskScheduleExecutor.startTaskScheduleExecutor();////开户任务
    }

    public void run() {
        String content;
        while((content = readFromClient()) != null){
            PrintUtil.print("command:" + content, SystemLog.Level.info);

            if(content.equalsIgnoreCase("quit")){
                Thread.currentThread().interrupt(); //close thread
            }

            if(!CommandProcessUtil.checkCommand(content)){
                printStream.println("提示:命令格式不正确.请检查!");
                continue; ///命令格式不正确
            }

            printStream.println("Result:" + CommandProcessUtil.processCommand(content));
            if(isClose){
                Thread.currentThread().interrupt(); ///close thread
            }
        }
    }

    /**
     * 从线程对应的客户端读取输入
     * @return 客户端输入数据
     */
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
