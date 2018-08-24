package cn.missbe.redis.bean;

public class RedisBean {
    private int hit;
    private String key;
    private long timeout;
    private Object value;

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

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

    public RedisBean copyOf(){
        RedisBean redisBean = new RedisBean();
        redisBean.setValue(value);
        redisBean.setKey(key);
        redisBean.setHit(hit);
        redisBean.setTimeout(timeout);
        return redisBean;
    }
}
