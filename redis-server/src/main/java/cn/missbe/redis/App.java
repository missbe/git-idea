package cn.missbe.redis;

import cn.missbe.redis.util.PropertiesUtil;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    /***
     * Redis配置常量
     */
    public static   String      DELIMITER                = "%";       ////定义字符串分隔符

    public static   long        TIMEOUT                  = 1000 * 30; ////定义过期时间,单位：毫秒

    public static   int         CACHED_CHECK_PERIOD      = 60;        ///定义检查周期检查缓存的时间，单位：秒

    public static   int         CACHED_CHECK_INITIAL     = 30;        ///定义开始周期检查缓存的时间，单位：秒

    public static   int         REDIS_MODIFY_NUMBER      = 0;         ///键值对对象访问次数

    public static   String[]    SAVE_PERSISTENCE         = {"60:6"};  ////定义60秒内6次更改时持久化到介质

    public static   String      PERSISTENCE_MEDIA        = "db";     ////定义持久化的介质，默认持久化到数据库

    public static final String  REDIS_CONFIG_NAME        = "redis.properties"; // Redis配置文件名称

    public static Map<Long,Long> REDIS_SAVE_MAP          = new HashMap<>();   ////定义持久化的介质，默认持久化到数据库



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
            REDIS_SAVE_MAP.put(Long.valueOf(tmp[0]),Long.valueOf(tmp[1]));
            max = Long.valueOf(tmp[1]) > max ? Long.valueOf(tmp[1]) : max;
        }
        REDIS_SAVE_MAP.put(Long.MAX_VALUE, max);
    }

}
