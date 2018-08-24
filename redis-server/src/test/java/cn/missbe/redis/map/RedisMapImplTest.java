package cn.missbe.redis.map;

import org.junit.Test;

public class RedisMapImplTest {
    RedisMapImpl redisMap = RedisMapImpl.RedisMapHolder.getInstance();

    @Test
    public void put() {
        redisMap.put("missbe", "www.missbe.cn");
        redisMap.put("bat", "www.baidu.com","www.alibaba.com", "www.tencent.com");
        System.out.println("Key-Value:" + redisMap);
    }
}