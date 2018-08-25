package cn.missbe.redis.util;

import cn.missbe.redis.App;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-25 下午3:54
 *   @author lyg
 *   @version 1.0
 *   @Description  属性工具，读取配置文件
 **/
public class PropertiesUtil {
    private static Properties prop = new Properties();
    private static HashMap<String, String> dbProps = new HashMap<>();

    static {
        initPropes();
    }
    private  PropertiesUtil(){}

    private static void initPropes(){
        InputStream ios;
        try {
            String path = PropertiesUtil.class.getClassLoader().getResource(App.DB_FILE_NAME).getPath();
//            System.out.println("DB配置文件路径：" + path);
            ios = new FileInputStream(path);
            prop.load(ios);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库配置文件加载失败，数据获取失败！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("数据库配置文件加载IO异常，数据获取失败！");
        }
        for (Object key : prop.keySet()){
            Object values = prop.get(key);
            if(key instanceof String && values instanceof String)
                dbProps.put((String)key, (String)values);
        }
    }

    public static void reloadPropes(){
        initPropes();
    }

    /**
     * 读取Redis配置文件
     * @return 返回配置文件的Properties对象
     */
    public static Properties readRedisConfig(){
        String path = PropertiesUtil.class.getClassLoader().getResource(App.REDIS_CONFIG_NAME).getPath();
        try(
                InputStream ios = new FileInputStream(path);
        ) {
             Properties propes = new Properties();
             propes.load(ios);
             return propes;
        } catch (FileNotFoundException e) {
            PrintUtil.print("Redis配置文件未找到.." + e.getCause(), SystemLog.Level.error);
        } catch (IOException e) {
            PrintUtil.print("Redis配置文件读取失败." + e.getCause(), SystemLog.Level.error);
        }
        return null;
    }

    public static HashMap<String, String> getDbProps() {
        return dbProps;
    }
}
