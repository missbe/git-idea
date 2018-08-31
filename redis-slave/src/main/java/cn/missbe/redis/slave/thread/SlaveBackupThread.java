package cn.missbe.redis.slave.thread;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.util.FileDaoUtils;
import cn.missbe.util.DateUtil;
import cn.missbe.util.IOUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 下午12:36
 *   @author lyg
 *   @version 1.0
 *   @Description 备份服务线程
 **/

public class SlaveBackupThread implements Runnable {
    private String preffix;
    private PrintStream ps;
    private BufferedInputStream reader;

    public SlaveBackupThread(String ip, String port) {
        try {
           this.preffix = ip +"_" + port +" _" + DateUtil.formateDateyyyyMM(new Date());
            Socket socket = new Socket(ip, Integer.valueOf(port));
            reader = new BufferedInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            PrintUtil.print("获取服务器通道流成功,数据备份任务开始.");
        } catch (IOException e) {
            e.printStackTrace();
            PrintUtil.print("服务器主机异常，连接异常，当前线程关闭.", SystemLog.Level.error);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        PrintUtil.print("当前线程:." + Thread.currentThread().getName() + "开始数据备份任务.", SystemLog.Level.info);

        ps.println("backup"); ///向服务器发送备份命令

        try {
            saveResult();
            PrintUtil.print("当前线程:." + Thread.currentThread().getName() + "数据备份任务完成.", SystemLog.Level.info);
        } catch (IOException e) {
            PrintUtil.print("该线程备份数据时读取服务器数据发生错误.关闭该线程" + e.getMessage(), SystemLog.Level.error);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 保存服务器回复的数据
     * @throws IOException 读取服务器发送数据发生IO异常
     */
    private void saveResult() throws IOException {
        ///配置备份文件名字的前缀和解析流
        FileDaoUtils.savePersistence(IOUtils.parseStream(reader, App.SERVER_OK),preffix);
    }///end save

}
