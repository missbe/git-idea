package cn.missbe.redis.slave.bean;

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

public class RedisBean {
    private String key;
    private long timeout;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

//
//        public RedisBean copyOf(){
//        RedisBean redisBean = new RedisBean();
//        redisBean.setValue(value);
//        redisBean.setKey(key);
//        redisBean.setTimeout(timeout);
//        return redisBean;
//    }
}
