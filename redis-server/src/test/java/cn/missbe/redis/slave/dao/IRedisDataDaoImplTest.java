package cn.missbe.redis.slave.dao;

import cn.missbe.redis.slave.bean.RedisBean;
import org.junit.Test;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:32
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class IRedisDataDaoImplTest {
    private  static RedisBean bean = new RedisBean();
    static {
        bean.setKey("missbe");
        bean.setTimeout(System.currentTimeMillis());
        bean.setValue("我是一个对象");
    }

    @Test
    public void save() {
//        IRedisDataDao redisMap = new RedisDataDaoImpl();
//        boolean isSucc = redisMap.save(bean);
//        if(isSucc){
//            System.out.println("插入对象成功.");
//        }else{
//            System.out.println("插入对象失败.");
//        }
//
//        List<RedisBean> beans = new ArrayList<>();
//        for(int i = 10 ;i < 20; i++){
//            RedisBean bean1 =  bean.copyOf();
//            bean1.setValue(bean1.getValue() + String.valueOf(i));
//            bean1.setTimeout(System.currentTimeMillis());
//            beans.add(bean1);
//        }
//        System.out.println(JSON.toJSONString(beans));
    }

    @Test
    public void update() {
        IRedisDataDao redisMap = new RedisDataDaoImpl();
        boolean isSucc = redisMap.update(bean);
        if(isSucc){
            System.out.println("插入对象成功.");
        }else{
            System.out.println("插入对象失败.");
        }
    }

    @Test
    public void delete() {
        IRedisDataDao redisMap = new RedisDataDaoImpl();
        boolean isSucc =  redisMap.delete(bean);
        if(isSucc){
            System.out.println("插入对象成功.");
        }else{
            System.out.println("插入对象失败.");
        }
    }
}