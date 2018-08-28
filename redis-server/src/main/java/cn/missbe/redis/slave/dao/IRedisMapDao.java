package cn.missbe.redis.slave.dao;

import cn.missbe.redis.slave.bean.RedisBean;

import java.util.List;

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

public interface IRedisMapDao {
    /**
     * 清除数据库中所有键值对对象
     */
    void clear();

    /**
     * 保存所有键值对象到数据库中
     */
    void save();

    /**
     * 保存List<RedisBean>到数据库中
     * @param beans 指定List
     */
    void save(List<RedisBean> beans);

    /**
     * 保存指定RedisBean到数据库中
     * @param bean 指定的RedisBean对象
     * @return 持久化是否成功
     */
    boolean save(RedisBean bean);

    /**
     * 更新指定键值对对象
     * @param bean 指定对象
     * @return 更新是否成功
     */
    boolean update(RedisBean bean);

    /**
     * 从数据库中删除指定的RedisBean对象
     * @param bean 指定的RedisBean对象
     * @return 删除是否成功
     */
    boolean delete(RedisBean bean);

}
