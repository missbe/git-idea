package cn.missbe.redis.client.hash;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午9:24
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

/**
 * Hash算法对象，用于自定义hash算法
 */
public interface HashFunction {
    public Long hash(Object key);
}
