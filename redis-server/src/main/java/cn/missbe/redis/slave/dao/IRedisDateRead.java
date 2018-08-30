package cn.missbe.redis.slave.dao;

import cn.missbe.redis.slave.map.IRedisMap;
import cn.missbe.redis.slave.map.KeyValueNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 下午2:26
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public interface IRedisDateRead {
    /**
     * 获取缓存中的键值对对象，优先从服务器文件中读取数据，如果
     * 文件读取失败，再从 IRedisDataDao获取数据,返回对应字节数据
     * @param redisDataDao 键值对数据持有者
     * @return 字节数组数据
     */
    byte[] read(IRedisMap redisDataDao);

    /**
     * 从持久化文件中读取键值对数据，键类型为String，值类型为List<KeyValueNode>
     * @param path 文件路径
     * @return Map<String, ArrayList<KeyValueNode>>结果
     */
    Map<String, ArrayList<KeyValueNode>> read2list(String path);

    /**
     * 从持久化文件中读取键值对数据，键类型为String，值类型为KeyValueNode
     * @param path 文件路径
     * @return Map<String, KeyValueNode>结果
     */
    Map<String, KeyValueNode> read(String path);

    /**
     * 从持久化文件中读取键值对数据，键类型为String，值类型为Set<KeyValueNode>
     * @param path 文件路径
     * @return Map<String, HashSet<KeyValueNode>>结果
     */
    Map<String, HashSet<KeyValueNode>> read2Set(String path);
}
