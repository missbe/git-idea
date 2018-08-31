package cn.missbe.util;

import java.io.Serializable;

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

public class SystemLog implements Serializable {

    private static final long serialVersionUID = -6854423671693792099L;
    /**
     * 日志级别枚举<br>
     * debug Level : 指出细粒度信息事件对调试应用程序是非常有帮助的。<br>
     * info level : 表明消息在粗粒度级别上突出强调应用程序的运行过程。<br>
     * warning level : 表明会出现潜在错误的情形。<br>
     * error level : 指出发生错误事件。
     */
    public enum Level {
        debug, info, warning, error;

        public static boolean AmoreB(Level A, Level B) {
            return A.equals(error) || toInt(A) >= toInt(B);
        }

        public static Level strToLevel(String levelStr) {
            if (StringUtils.isBlank(levelStr)) {
                return debug;
            }
            levelStr = levelStr.toLowerCase();
            switch (levelStr) {
                case "debug":
                    return debug;
                case "info":
                    return info;
                case "warning":
                    return warning;
                case "error":
                    return error;
                default:
                    return debug;
            }
        }

        private static int toInt(Level level) {
            switch (level) {
                case debug:
                    return 0;
                case info:
                    return 1;
                case warning:
                    return 2;
                case error:
                    return 3;
                default:
                    return 0;
            }
        }
    }
}
