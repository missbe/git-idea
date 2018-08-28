package cn.missbe.redis.slave.task;

import cn.missbe.redis.slave.map.RedisMapImpl;

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
