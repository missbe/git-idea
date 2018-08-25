package cn.missbe.redis.map;

public interface IRedisMap {
    String put(String command, String key, String... valueNode);
    String get(String key);
    String delete(String key);
    String expire(String key, long seconds);
}
