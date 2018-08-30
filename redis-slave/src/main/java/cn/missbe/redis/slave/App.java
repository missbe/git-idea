package cn.missbe.redis.slave;

import cn.missbe.redis.slave.util.JsonConfigUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.IOException;

/**
 * 全局参数量
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
