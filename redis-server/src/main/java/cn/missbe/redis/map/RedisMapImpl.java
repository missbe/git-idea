package cn.missbe.redis.map;

import cn.missbe.redis.App;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisMapImpl implements IRedisMap {
    private Map<String, KeyValueNode> maps = new HashMap<>();

    private RedisMapImpl(){

    }

    public Map<String, KeyValueNode> getMaps() {
        return maps;
    }

    public static class RedisMapHolder{
        private static  RedisMapImpl redisMapImpl = new RedisMapImpl();

        public  static RedisMapImpl getInstance(){
            return RedisMapHolder.redisMapImpl;
        }
    }

    public String put(String key, String... valueNode) {
        KeyValueNode keyValueNode = new KeyValueNode();
        if(valueNode.length == 1){
             keyValueNode.setValue(valueNode[0]);
             maps.put(key,keyValueNode);
        }else {
            List<String> list =  new ArrayList<>();
            for (int i = 0; i < valueNode.length; i++) {
                list.add( i== valueNode.length-1 ? valueNode[i] : valueNode[i] + App.DELIMITER); ///添加分隔符存储
            }
            keyValueNode.setValue(list);
            maps.put(key, keyValueNode);
        }
        return "OK";
    }

    public String delete(String key){
        if(maps.containsKey(key)){
            maps.remove(key);
            return key + "删除完成";
        }
        return "指定删除的键不存在!";
    }

    public String getValue(String key) {
        String msg = "键值对缓存对象已经过期或不存在..";
        KeyValueNode node = maps.get(key);

        if(node == null){
            return msg;
        }else if(System.currentTimeMillis() - node.getTimeOut() > 0 ){
            delete(key); ///已经过时，删除掉
            PrintUtil.print(key + "缓存对象过期-进行惰性删除.", SystemLog.Level.warning);
            node = null;
        }else{
            node.setHit(node.getHit()+1);///否则命中数加1
        }

        return node == null ? msg : node.getValue();
    }

    @Override
    public String toString() {
        return "maps=" + maps;
    }
}
