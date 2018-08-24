package cn.missbe.redis.map;

public interface IRedisMap {
    String put(String key, String... valueNode);
    String getValue(String key);
    String delete(String key);
}
