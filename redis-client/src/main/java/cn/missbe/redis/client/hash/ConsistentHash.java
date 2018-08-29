package cn.missbe.redis.client.hash;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性Hash算法
 * @param <T> 节点类型
 */
class ConsistentHash<T> {

    /**
     * Hash计算对象，用于自定义hash算法
     */
    private HashFunction hashFunc;

    /**
     * 节点上复制的虚拟节点个数
     */
    private final int numberOfReplicas;

    /**
     * 一致性Hash环
     */
    private final SortedMap<Long, T> circle = new TreeMap<>();

    /**
     * 构造，使用Java默认的Hash算法
     * @param numberOfReplicas 复制的节点个数，增加每个节点的复制节点有利于负载均衡
     * @param nodes 节点对象
     */
    ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
        this(key -> fnv1HashingAlg(key.toString()), numberOfReplicas, nodes);
    }

    /**
     * 构造一致性Hash算法对象
     * @param hashFunc         hash算法对象
     * @param numberOfReplicas 复制的节点个数，增加每个节点的复制节点有利于负载均衡
     * @param nodes            节点对象
     */
    private ConsistentHash(HashFunction hashFunc, int numberOfReplicas, @NotNull Collection<T> nodes) {
        this.numberOfReplicas = numberOfReplicas;
        this.hashFunc = hashFunc;
        //初始化节点
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加节点<br>每增加一个节点，就会在闭环上增加给定复制节点数<br>
     * 例如复制节点数是2，则每调用此方法一次，增加两个虚拟节点，这两个
     * 节点指向同一Node,由于hash算法会调用node的toString方法，故按照toString去重
     * @param node 节点对象
     */
    private void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            long hash = hashFunc.hash(node.toString() + "&&VirtualNode" +String.valueOf(i));
            circle.put(hash, node);
//            System.out.println(node + "->" + hash);
        }
    }

    /**
     * 移除节点的同时移除相应的虚拟节点
     * @param node 节点对象
     */
    void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunc.hash(node.toString() + "&&VirtualNode" + String.valueOf(i)));
        }
    }

    /**
     * 获得一个最近的顺时针实际节点
     * @param key 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * @return 节点对象
     */
    T get(Object key) {
        ///判断是否为空
        if (circle.isEmpty()) {
            return null;
        }
        ///遍历环
        long hash = hashFunc.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash); //返回此映射的部分视图，其键大于等于 hash
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        //正好命中
        return circle.get(hash);
    }

//    private static long md5HashingAlg(@NotNull String key) {
//        MessageDigest md5;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//            md5.reset();
//            md5.update(key.getBytes());
//            byte[] bKey = md5.digest();
//            return ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8)| (long) (bKey[0] & 0xFF);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return 0l;
//    }

    /**
     * 使用FNV1hash算法
     * @param key 算法处理对象
     * @return 处理结果
     */
    private static long fnv1HashingAlg(@NotNull String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;

        for (int i = 0; i < key.length(); i++)
            hash = (hash ^ key.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        return hash < 0 ? Math.abs(hash) : hash;
    }

}
