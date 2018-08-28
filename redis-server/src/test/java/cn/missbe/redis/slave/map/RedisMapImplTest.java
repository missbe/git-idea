package cn.missbe.redis.slave.map;

import org.junit.Test;

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

public class RedisMapImplTest {
    RedisMapImpl redisMap = RedisMapImpl.RedisMapHolder.getInstance();

    @Test
    public void put() {
        redisMap.put("missbe", "www.missbe.cn");
        redisMap.put("bat", "www.baidu.com","www.alibaba.com", "www.tencent.com");
        System.out.println("Key-Value:" + redisMap);
    }
}