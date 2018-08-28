package cn.missbe.redis.slave.util;

import cn.missbe.redis.slave.App;
import org.junit.Test;

import java.util.Properties;

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

public class PropertiesUtilTest {

    @Test
    public void readRedisConfig() {
        Properties prop = PropertiesUtil.readRedisConfig();
        System.out.println(prop);
        String[] saves = prop.getProperty("redis.save").replaceAll(" ","" ).split(App.DELIMITER);
        for (String s : saves)
            System.out.println(s);
    }
}