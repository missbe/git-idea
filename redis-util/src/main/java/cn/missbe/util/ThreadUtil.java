package cn.missbe.util;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 下午2:46
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class ThreadUtil {
    /**
     * 当前线程睡眠毫秒数
     **/
    public static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {

            PrintUtil.print("线程睡眠中断" + e.getCause(), SystemLog.Level.error);
        }
    }
}
