package cn.missbe.redis.slave.task;

import cn.missbe.redis.slave.App;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

public class TaskScheduleExecutor {
//    private static ScheduledExecutorService service = null;

    /**
     * 定时任务，每隔一分钟检查缓存是否过期
     */
    public static void startTaskScheduleExecutor(){

        int corePoolSize = App.REDIS_SAVE_MAP.size();

        ScheduledExecutorService service = Executors.newScheduledThreadPool(corePoolSize);
        PrintUtil.print("缓存定时检查移除对象任务开启..", SystemLog.Level.info);
        service.scheduleWithFixedDelay(new CachedTimerTask(), App.CACHED_CHECK_INITIAL, App.CACHED_CHECK_PERIOD, TimeUnit.SECONDS);

        PrintUtil.print("缓存定时持久到存储介质任务开启..", SystemLog.Level.info);
        for(Long key : App.REDIS_SAVE_MAP.keySet()){
            service.scheduleAtFixedRate(new CachedDaoTask(App.REDIS_SAVE_MAP.get(key)), App.CACHED_CHECK_INITIAL, key, TimeUnit.SECONDS );
        }//end for
    }

}
