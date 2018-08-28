package cn.missbe.redis.slave;

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

import cn.missbe.redis.slave.util.JsonConfigUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String IP_CLUSTER_FILE  = "ip_cluster.json";
    public static final String SERVER_OK        = "SERVER_OK";
    public static long   SLAVE_INITIAL_DELAY;
    public static long   SLAVE_PERIOD_DELAY;

    /**
     * 初始化各种参数
     */
    public static void initialEnv(){
        try {
            long[] delays = JsonConfigUtils.getDelayTime();
            SLAVE_INITIAL_DELAY = delays[0];
            SLAVE_PERIOD_DELAY  = delays[1];
        } catch (IOException e) {
            PrintUtil.print("配置文件读取失败，采用默认配置.", SystemLog.Level.error);
            SLAVE_INITIAL_DELAY = 60;
            SLAVE_PERIOD_DELAY  = 60;
        }
    }
}
