package cn.missbe.redis.slave.task;

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

public class TestScheduledExecutorService{
    public static void main(String[] args) {
        ScheduledExecutorService execService =   Executors.newScheduledThreadPool(2);
        // 5秒后开始执行 每个2秒执行一次，如果有的任务执行要花费比其周期更长的时间，则将推迟后续执行，但不会同时执行
//        每次相隔相同的时间执行任务，如果任务的执行时间比周期还长，那么下一个任务将立即执行
        execService.scheduleAtFixedRate(()-> {
            System.out.println("任务："+Thread.currentThread().getName()+" 执行了，时间为： "+System.currentTimeMillis());
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5, 2, TimeUnit.SECONDS);
        //5秒后开始执行 每个2秒执行一次，保证固定的延迟为2秒 下一个任务的开始时间与上一个任务的结束时间间隔相同
        execService.scheduleWithFixedDelay(()->{
            System.out.println("任务："+Thread.currentThread().getName()+"执行了，时间为："+System.currentTimeMillis());
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 5, 2, TimeUnit.SECONDS);
//        Thread.sleep(10000L);
//        execService.shutdown();
    }
}
