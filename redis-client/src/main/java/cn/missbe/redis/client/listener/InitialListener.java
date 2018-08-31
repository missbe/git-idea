package cn.missbe.redis.client.listener;

import cn.missbe.redis.client.App;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-30 下午4:43
 *   @author lyg
 *   @version 1.0
 *   @Description 自定义监听器，负责初始化Web窗口
 **/

public class InitialListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        PrintUtil.print("Redis应用初始化.", SystemLog.Level.info);
        App.initConfig();///加载初始环境配置
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        PrintUtil.print("Redis应用关闭.", SystemLog.Level.info);
    }
}
