package cn.missbe.redis.dao;

import cn.missbe.redis.bean.RedisBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IRedisMapDaoImplTest {
    private  static RedisBean bean = new RedisBean();
    static {
        bean.setHit(3);
        bean.setKey("missbe");
        bean.setTimeout(System.currentTimeMillis());
        bean.setValue("我是一个对象");
    }

    @Test
    public void save() {
        IRedisMapDao redisMap = new RedisMapDaoImpl();
        boolean isSucc = redisMap.save(bean);
        if(isSucc){
            System.out.println("插入对象成功.");
        }else{
            System.out.println("插入对象失败.");
        }

        List<RedisBean> beans = new ArrayList<>();
        for(int i = 10 ;i < 20; i++){
            RedisBean bean1 =  bean.copyOf();
            bean1.setValue(bean1.getValue() + String.valueOf(i));
            bean1.setTimeout(System.currentTimeMillis());
            beans.add(bean1);
        }
        redisMap.save(beans);
    }

    @Test
    public void update() {
        IRedisMapDao redisMap = new RedisMapDaoImpl();
        boolean isSucc = redisMap.update(bean);
        if(isSucc){
            System.out.println("插入对象成功.");
        }else{
            System.out.println("插入对象失败.");
        }
    }

    @Test
    public void delete() {
        IRedisMapDao redisMap = new RedisMapDaoImpl();
        boolean isSucc =  redisMap.delete(bean);
        if(isSucc){
            System.out.println("插入对象成功.");
        }else{
            System.out.println("插入对象失败.");
        }
    }
}