package cn.missbe.util;

import cn.missbe.util.SystemLog.Level;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
public class PrintUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
    public static final Logger logger = Logger.getLogger("printlog");
    private static final Object App = "cn.missbe.util-";

    /**
     * @param msg
     * @param level0 可选参数，根据第一个参数级别输出，留空则为Level.DEBUG
     * @Description 根据配置，判断是否打印到控制台
     */
    public static void print(Object msg, Level... level0) {
        Level level = Level.debug;
        if (level0.length != 0) {
            level = level0[0];
        }
        String msgStr;
        if (msg != null) {
            msgStr = msg.toString();
        } else {
            msgStr = "空值打印.";
        }
        if (msgStr.trim().equals("")) {
            msgStr = "Nothing.";
        }
        StackTraceElement[] temp = Thread.currentThread().getStackTrace();
        StackTraceElement method = temp[2];
        for (StackTraceElement stackTraceElement : temp) {
            if (stackTraceElement.getClassName().contains("police")
                    && !stackTraceElement.getClassName().contains("CGLIB")
                    && !stackTraceElement.getMethodName().equals("print")
                    && !stackTraceElement.getMethodName().equals("around")) {
                method = stackTraceElement;
                break;
            }
        }
        String fromLine = method.getClassName().replaceAll(App + ".", "") + "." + method.getMethodName()
                + "() line@" + method.getLineNumber();
        String logMsgStr;
        synchronized (sdf) {
            logMsgStr = sdf.format(Calendar.getInstance().getTime()) + " :" + msgStr + " @来自：" + fromLine;
            // 判断打印日志等级
            if (level.equals(Level.error)
                    || Level.AmoreB(level, Level.strToLevel("debug"))){
                switch (level) {
                    case debug:
                        logger.debug(logMsgStr);
                        break;
                    case info:
                        logger.info(logMsgStr);
                        break;
                    case warning:
                        logger.warn(logMsgStr);
                        break;
                    case error:
                        logger.error(logMsgStr);
                        break;
                    default:
                        logger.debug(logMsgStr);
                }
            }
        }
    }
}

