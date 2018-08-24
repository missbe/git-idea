package cn.missbe.redis.dao;

import cn.missbe.redis.bean.RedisBean;

import java.util.List;

public interface IRedisMapDao {
    boolean save(RedisBean bean);
    void save(List<RedisBean> beans);
    boolean update(RedisBean bean);
    boolean delete(RedisBean bean);
}
