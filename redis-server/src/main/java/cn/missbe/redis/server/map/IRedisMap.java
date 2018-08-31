package cn.missbe.redis.server.map;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:32
 *   @author lyg
 *   @version 1.0
 *   @Description 缓存对象操作命令接口
 **/

public interface IRedisMap {
    /**
     * 根据命令将键值对放入对应的Map当中
     * @param command 命令串
     * @param key 键串
     * @param valueNode 值对应的字符串
     * @return 返回结果消息
     */
    String put(String command, String key, String... valueNode);

    /**
     * 通过指定key返回对应值字符串
     * @param key key字符串
     * @return 返回的结果消息
     */
    String get(String key);

    /**
     * 根据指定key移除对应的键值对对象
     * @param key 指定键
     * @return 结果消息
     */
    String delete(String key);

    /**
     * 设置指定键的过期时间
     * @param key 指定键串
     * @param seconds 过期时间
     * @return 结果消息
     */
    String expire(String key, long seconds);
}
