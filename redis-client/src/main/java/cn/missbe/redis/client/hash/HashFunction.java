package cn.missbe.redis.client.hash;

/**
 * Hash算法对象，用于自定义hash算法
 */
public interface HashFunction {
    Long hash(Object key);
}
