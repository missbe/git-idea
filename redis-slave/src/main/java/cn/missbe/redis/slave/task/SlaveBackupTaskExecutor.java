package cn.missbe.redis.slave.task;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.util.JsonConfigUtils;
import cn.missbe.redis.thread.SlaveBackupThread;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 上午9:20
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class SlaveBackupTaskExecutor {

    public static void startTaskExecutor(){

        List<String[]> ipCluster;
        try {
            ipCluster = JsonConfigUtils.getIpCluster();
        } catch (IOException e) {
            PrintUtil.print("服务器IP信息加载失败，任务调度失败." + e.getMessage(), SystemLog.Level.error);
            return;
        }

        int corePoolSize = ipCluster.size();
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(corePoolSize);
        PrintUtil.print("开启周期请求服务器备份数据任务.");
        for(String[] ips : ipCluster){
            service.scheduleAtFixedRate(new SlaveBackupThread(ips[0],ips[1]), App.SLAVE_INITIAL_DELAY,App.SLAVE_PERIOD_DELAY, TimeUnit.SECONDS);
        }
    }
}
