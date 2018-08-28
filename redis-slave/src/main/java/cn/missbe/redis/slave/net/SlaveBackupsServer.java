package cn.missbe.redis.slave.net;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.task.SlaveBackupTaskExecutor;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 上午8:53
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class SlaveBackupsServer {

    public static void main(String[] args) {

        App.initialEnv();///初始化参数

        SlaveBackupTaskExecutor.startTaskExecutor();///开启备份服务器备份任务
    }
}
