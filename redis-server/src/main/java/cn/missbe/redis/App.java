package cn.missbe.redis;

public class App {
    public static final  String   DELIMITER              = "|";           ////定义字符串分隔符

    public static final  long    TIMEOUT                 = 1000 * 30;     ////定义过期时间,单位：毫秒

    public static final  int     CACHED_CHECK_PERIOD     = 60;            ///定义检查周期检查缓存的时间，单位：秒

    public static final  int     CACHED_CHECK_INITIAL    = 30;           ///定义开始周期检查缓存的时间，单位：秒

    public final static String   DB_FILE_NAME            = "db.properties"; // 数据 URL

    public final static String   JDBC_DRIVER             = "jdbcDriver"; // 数据库驱动

    public final static String   DB_URL                  = "dbUrl"; // 数据 URL

    public final static String   DB_USERNAME             = "dbUsername"; // 数据库用户名

    public final static String   DB_PASSWORD             = "dbPassword"; // 数据库用户密码

    public final static String   TESTTABLE               = "redis"; //
}
