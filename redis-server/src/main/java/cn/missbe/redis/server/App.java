package cn.missbe.redis.server;

import cn.missbe.redis.server.util.PropertiesUtil;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.util.*;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:32
 *   @author lyg
 *   @version 1.0
 *   @Description 主服务器需要的常量信息
 **/

public class App {

    /**
     * 数据库常量
     */
    public final static String   DB_FILE_NAME            = "db.properties"; // 数据 URL

    public final static String   JDBC_DRIVER             = "jdbcDriver"; // 数据库驱动

    public final static String   DB_URL                  = "dbUrl"; // 数据 URL

    public final static String   DB_USERNAME             = "dbUsername"; // 数据库用户名

    public final static String   DB_PASSWORD             = "dbPassword"; // 数据库用户密码

    public final static String   TESTTABLE               = "redis"; //测试表名

    public final static String   SERVER_OK               = "SERVER_OK"; //结束写入标志

    /***
     * Redis配置常量
     */
    public static   String      DELIMITER                = "%";       ////定义字符串持久化策略文件中的分隔符

    public static   long        TIMEOUT                  = 1000 * 30; ////定义默认过期时间,单位：毫秒

    public static   int         CACHED_CHECK_PERIOD      = 60;        ///定义检查周期检查缓存的时间，单位：秒

    public static   int         CACHED_CHECK_INITIAL     = 30;        ///定义开始周期检查缓存的时间，单位：秒

    public static   String[]    SAVE_PERSISTENCE         = {"60:6"};  ////默认定义60秒内6次更改时持久化到介质

    public static   String      SAVE_FILENAME            = "dump.txt";  ////定义持久策略的配置文件名称

    public static   String      PERSISTENCE_MEDIA        = "db";     ////定义持久化的介质，默认持久化到数据库

    public static final String  REDIS_CONFIG_NAME        = "redis.properties"; // Redis配置文件名称

    public static List<RedisSavePersistences> REDIS_SAVE_PERSISTENCES = new ArrayList<>();////保持持久化策略的对象列表

    /**
     * 服务器采用的端口
     */
    public static final int      PORT                    = 65532;



    /**
     * 读取redis缓存配置文件
     */
    public static  void loadRedisConfig(){
        Properties prop = PropertiesUtil.readRedisConfig();
        ///如果配置文件加载成功，采用配置文件当中的配置，否则采用代码当中的默认配置
        if(prop != null){
            PrintUtil.print("采用Redis配置文件当中的配置." + prop, SystemLog.Level.info);
            TIMEOUT = prop.getProperty("redis.timeout") == null ? 1000 * 60 *10 : Long.valueOf(prop.getProperty("redis.timeout"));

            PERSISTENCE_MEDIA    = prop.getProperty("redis.persistence")  == null ? "db" : prop.getProperty("redis.persistence");

            SAVE_FILENAME        = prop.getProperty("redis.filename")  == null ? "dump.txt" : prop.getProperty("redis.filename") + ".json";

            CACHED_CHECK_PERIOD  = prop.getProperty("redis.period") == null ?  1000 * 60 : Integer.valueOf(prop.getProperty("redis.period"));

            CACHED_CHECK_INITIAL = prop.getProperty("redis.initial") == null ?  1000 * 60 : Integer.valueOf(prop.getProperty("redis.initial"));

            SAVE_PERSISTENCE     = prop.getProperty("redis.save") == null ? new String[]{"60:6"} : prop.getProperty("redis.save").replaceAll(" ","" ).split(DELIMITER);
        }else{
            PrintUtil.print("采用App代码当中的默认配置.", SystemLog.Level.info);
        }
        preprocessing(); ///处理持久化策略
    }

    /**
     * 持久化到存储介质预处理方法
     */
    public static void preprocessing(){
        String[] stateges = App.SAVE_PERSISTENCE;
        Long max =  Long.MIN_VALUE;
        for(String str : stateges){
            String[] tmp = str.split(":");
            RedisSavePersistences savePersistences = new RedisSavePersistences();
            savePersistences.modifyNumber = Integer.valueOf(tmp[1]);
            savePersistences.saveDelayTime = Integer.valueOf(tmp[0]);
            REDIS_SAVE_PERSISTENCES.add(savePersistences);////难受，不要忘记加这个
        }
    }///end process

    /**
     * 修改持久化策略计数器,所有计数器访问加1
     */
    public static void modifyCounter(){
        for(RedisSavePersistences  persistences : REDIS_SAVE_PERSISTENCES){
            persistences.nowCount++;
        }
    }

    /**
     * APP静态类，负责持久化策略保持
     */
    public static class RedisSavePersistences{
        private int saveDelayTime;
        private int modifyNumber;
        private int nowCount;

        RedisSavePersistences(){
            nowCount = 0;
        }

        public int getSaveDelayTime() {
            return saveDelayTime;
        }

        public int getModifyNumber() {
            return modifyNumber;
        }

        public int getNowCount() {
            return nowCount;
        }

        public void setNowCount(int nowCount) {
            this.nowCount = nowCount;
        }

        /**
         * 当前对象计数器清零
         */
        public void clearCounter(){
            nowCount = 0;
        }

        /**
         * 判断是否进行持久化
         * @return 持久化返回true，否返回false
         */
        public boolean isPersistences(){
            return nowCount > modifyNumber;
        }
    }

}
