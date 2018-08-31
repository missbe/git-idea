package cn.missbe.redis.client.hash;

/**
 * Hash算法对象，用于自定义hash算法
 */
public interface HashFunction {
    /**
     * 对给定进行Hash，返回Hash结果值
     * @param key 指定值
     * @return 值的Hash结果
     */
    Long hash(Object key);
}
