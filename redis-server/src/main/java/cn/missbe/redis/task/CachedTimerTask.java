package cn.missbe.redis.task;

import cn.missbe.redis.map.KeyValueNode;
import cn.missbe.redis.map.RedisMapImpl;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.util.Iterator;
import java.util.Map;

public class CachedTimerTask implements Runnable{
    private RedisMapImpl redisMap = RedisMapImpl.RedisMapHolder.getInstance();

    /**
     * 负责将缓存过期的键值对对象删除
     */
    @Override
    public void run() {
//        System.out.println("MAP:" + redisMap.getMaps());
        Iterator<Map.Entry<String,KeyValueNode>> iterator = redisMap.getMaps().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,KeyValueNode> entry = iterator.next();
            KeyValueNode valueNode = entry.getValue();
            if(System.currentTimeMillis() - valueNode.getTimeOut() > 0){
               iterator.remove();////移除缓存
                PrintUtil.print(entry.getKey() + "缓存对象过期-进行主动删除.", SystemLog.Level.warning);
            }
//            System.out.println("当前正在处理缓存对象:" + entry.getKey() + ":" +valueNode);
        }
    }
}
