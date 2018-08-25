package cn.missbe.redis.task;

import cn.missbe.redis.App;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskScheduleExecutor {
//    private static ScheduledExecutorService service = null;

    /**
     * 定时任务，每隔一分钟检查缓存是否过期
     */
    public static void startTaskScheduleExecutor(){
        int corePoolSize = 2;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(corePoolSize);
        service.scheduleWithFixedDelay(new CachedTimerTask(),App.CACHED_CHECK_INITIAL, App.CACHED_CHECK_PERIOD, TimeUnit.SECONDS);
    }

}
