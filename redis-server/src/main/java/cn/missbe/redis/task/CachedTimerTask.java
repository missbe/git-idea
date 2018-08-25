package cn.missbe.redis.task;

import cn.missbe.redis.map.RedisMapImpl;

public class CachedTimerTask implements Runnable{
    private RedisMapImpl redisMap = RedisMapImpl.RedisMapHolder.getInstance();

    /**
     * 负责将缓存过期的键值对对象删除
     */
    @Override
    public void run() {
        redisMap.clearExpireKey();  ///清理过期缓存对象
    }
}
