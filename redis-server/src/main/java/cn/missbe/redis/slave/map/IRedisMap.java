package cn.missbe.redis.slave.map;

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

public interface IRedisMap {
    String put(String command, String key, String... valueNode);
    String get(String key);
    String delete(String key);
    String expire(String key, long seconds);
}
