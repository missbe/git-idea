package cn.missbe.redis.map;

import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.util.*;

public class RedisMapImpl implements IRedisMap {
    private Map<String, KeyValueNode>            maps     = new HashMap<>();                                ////保存键值对都为字符串
    private Map<String, ArrayList<KeyValueNode>> listMaps = new HashMap<String, ArrayList<KeyValueNode>>(); ////值为list类型
    private Map<String, HashSet<KeyValueNode>>   setMaps  = new HashMap<String, HashSet<KeyValueNode>>();  ////值为set类型


    /**
     * 私有构造函数，单例化
     */
    private RedisMapImpl(){
    }

    public Map<String, KeyValueNode> getMaps() {
        return maps;
    }

    /**
     * 保持只有一个RedisMapImpl对象
     */
    public static class RedisMapHolder{
        private static  RedisMapImpl redisMapImpl = new RedisMapImpl();

        public  static RedisMapImpl getInstance(){
            return RedisMapHolder.redisMapImpl;
        }
    }

    /**
     *根据command命令将key-value对象放入对应的map中
     * @param command 命令字符串
     * @param key 键-字符串
     * @param valueNode 值对象
     * @return 返回结果消息
     */
    public String put(String command, String key, String... valueNode) {
        String msg ="OK", tmp;
        switch (command){
            case "set":
                msg = removeListMapKeyValueNode(key);

                tmp = removeSetMapKeyValueNode(key);
                msg = tmp.equals("OK") ? (msg.equals("OK") ? "OK" : msg) : tmp;

                KeyValueNode keyValueNode = new KeyValueNode(valueNode[0]);
                maps.put(key,keyValueNode);
                PrintUtil.print(key + "(String)对象缓存成功..", SystemLog.Level.info);
                break;
            case "lset":
                msg = removeMapsKeyValueNode(key); ///检查maps当中是否已经包含该对象

                tmp =removeSetMapKeyValueNode(key);
                msg = tmp.equals("OK") ? (msg.equals("OK") ? "OK" : msg) : tmp;

                ArrayList<KeyValueNode> list =  new ArrayList<>();
                for (int i = 0; i < valueNode.length; i++) {
                    KeyValueNode node = new KeyValueNode(valueNode[i]);
                    list.add(node);
                }
                listMaps.put(key, list);
                PrintUtil.print(key + "(List)对象缓存成功.." + list, SystemLog.Level.info);
                break;
            case "hset":
                msg = removeMapsKeyValueNode(key); ///检查maps当中是否已经包含该对象

                tmp = removeListMapKeyValueNode(key);////检查listMaps当中是否已经包含该对象
                msg = tmp.equals("OK") ? (msg.equals("OK") ? "OK" : msg) : tmp;

                HashSet<KeyValueNode> set = new HashSet<>();
                for (int i = 0; i < valueNode.length; i++) {
                    KeyValueNode node = new KeyValueNode(valueNode[i]);
                    set.add(node);
                }
                setMaps.put(key, set);
                PrintUtil.print(key + "(Set)对象缓存成功..", SystemLog.Level.info);
                break;
        }
        return msg;
    }

    /**
     * 键对象为String，值对象为List类型的Map删除指定Key-Value
     * @param key 指定的key
     * @return 结果消息
     */
    private String removeListMapKeyValueNode(String key) {
        String msg = "OK";
        if(listMaps.containsKey(key)){
            msg = "更新-键:" + key + " 原值:" + listMaps.get(key);
            listMaps.remove(key); ///移除maps当中的键值对对象
            PrintUtil.print(key+ "键(List)更新，从(List)中移除", SystemLog.Level.warning);
        }
        return  msg;
    }

    /**
     * 键值对象都为String类型的Map删除指定Key-Value
     * @param key 指定键
     * @return 结果消息
     */
    private String removeMapsKeyValueNode(String key){
        String msg = "OK";
        if(maps.containsKey(key)){
            msg = "更新-键:" + key + " 原值:" + maps.get(key).getValue();
            PrintUtil.print(key+ "键(String)更新，从(String)中移除", SystemLog.Level.warning);
            maps.remove(key); ///移除maps当中的键值对对象
        }
        return  msg;
    }
    /**
     * 键对象为String，值对象为Set类型的Map删除指定Key-Value
     * @param key 指定的key
     * @return 结果消息
     */
    private String removeSetMapKeyValueNode(String key){
        String msg = "OK";
        if(setMaps.containsKey(key)){
            msg = "更新-键:" + key + " 原值:" + setMaps.get(key);
            PrintUtil.print(key+ "键(Set)更新，从(Set)中移除", SystemLog.Level.warning);
            setMaps.remove(key); ///移除maps当中的键值对对象
        }
        return  msg;
    }

    /**
     * 删除指定key的键值对对象
     * @param key 指定删除对象的key
     * @return 返回删除结果
     */
    public String delete(String key){
        String msg = key + "移除完成.";
        if(maps.containsKey(key)){
            maps.remove(key);
        }else if(setMaps.containsKey(key)){
            setMaps.remove(key);
        }else if(listMaps.containsKey(key)){
            listMaps.remove(key);
        }else{
            msg = "指定删除的键不存在!";
        }
        return msg;
    }

    /**
     * 查找指定key的值
     * @param key 指定key
     * @return key对应的值
     */
    public String get(String key) {
        String msg = "键值对缓存对象已经过期或n已经被移除.";

        if(maps.containsKey(key)){
            KeyValueNode node = maps.get(key);

            if(node.isTimeout() ){
               maps.remove(key); ///已经过时，删除掉
                PrintUtil.print(key + "缓存对象过期-进行惰性删除.", SystemLog.Level.warning);
                return msg;
            }

            PrintUtil.print(key + "(String)缓存对象数据返回成功..", SystemLog.Level.info);
            msg = node.getValue();

        }else if(setMaps.containsKey(key)){
           Set<KeyValueNode> set = setMaps.get(key);
           Iterator<KeyValueNode> iterator  = set.iterator();

//            System.out.println("Get Print Set:" + set);

            StringBuilder builder = new StringBuilder();
           while (iterator.hasNext()){
               KeyValueNode keyValueNode = iterator.next();
               if(keyValueNode != null && keyValueNode.isTimeout() ){
                   setMaps.remove(key); ///已经过时，删除掉
                   PrintUtil.print(key + "缓存对象过期-进行惰性删除.", SystemLog.Level.warning);
                   return msg;
               }
               builder.append(keyValueNode.getValue());
           }

            PrintUtil.print(key + "(Set)缓存对象数据返回成功..", SystemLog.Level.info);
            msg = builder.toString() == null ? "结果为空 " : builder.toString();

        }else if(listMaps.containsKey(key)){
            List<KeyValueNode> list = listMaps.get(key);
            Iterator<KeyValueNode> iterator = list.iterator();

//            System.out.println("Get Print List:" + list);

            StringBuilder builder = new StringBuilder();
            while (iterator.hasNext()){
                KeyValueNode keyValueNode = iterator.next();

                if(keyValueNode != null && keyValueNode.isTimeout() ){
                    listMaps.remove(key); ///已经过时，删除掉
                    PrintUtil.print(key + "缓存对象过期-进行惰性删除.", SystemLog.Level.warning);
                    return msg;
                }
                builder.append(keyValueNode.getValue());
            }

            msg = builder.toString() == null ? "结果为空 " : builder.toString();
            PrintUtil.print(key + "(List)缓存对象数据返回成功..", SystemLog.Level.info);

        }else{
            msg = "该键值对对象不在缓存中.";
        }

        return msg;
    }

    /**
     * 清除过期缓存对象
     */
    public void clearExpireKey(){

        ////清理maps中的过期缓存对象
        Iterator<Map.Entry<String,KeyValueNode>> iterator = maps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, KeyValueNode> entry = iterator.next();
            if (entry.getValue().isTimeout()) {
                iterator.remove();////移除缓存
                PrintUtil.print(entry.getKey() + "(String)缓存对象过期-进行主动删除.", SystemLog.Level.warning);
            }
        }///end maps

        ////清理listMaps中的过期缓存对象
        Iterator<Map.Entry<String,ArrayList<KeyValueNode>>> listIterator  =  listMaps.entrySet().iterator();
        while (listIterator.hasNext()){
            Map.Entry<String,ArrayList<KeyValueNode>> listEntry = listIterator.next();
            if(listEntry.getValue().get(0).isTimeout()){
                listIterator.remove();///移除List中过期缓存对象
                PrintUtil.print(listEntry.getKey() + "(List)缓存对象过期-进行主动删除.", SystemLog.Level.warning);
            }
        }///end listMaps

        ////清理setMaps中的过期缓存对象
        Iterator<Map.Entry<String, HashSet<KeyValueNode>>> setIterator = setMaps.entrySet().iterator();
        while (setIterator.hasNext()){
            Map.Entry<String, HashSet<KeyValueNode>> setEntry = setIterator.next();
            Iterator<KeyValueNode> tmpIterator = setEntry.getValue().iterator();
            KeyValueNode valueNode = tmpIterator.hasNext()  ? tmpIterator.next() : null;
            if(valueNode != null && valueNode.isTimeout()){
                setIterator.remove();///移除Set中过期缓存对象
                PrintUtil.print(setEntry.getKey() + "(Set)缓存对象过期-进行主动删除.", SystemLog.Level.warning);
            }
        }///end setMaps
    }

    @Override
    public String toString() {
        return "maps=" + maps;
    }
}
