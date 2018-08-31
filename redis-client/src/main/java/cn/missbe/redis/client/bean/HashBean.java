package cn.missbe.redis.client.bean;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 下午4:29
 *   @author lyg
 *   @version 1.0
 *   @Description 主服务器IP和端口信息，哈希槽区间
 **/

public class HashBean {
    private String  ip;
    private int     port;
    private int     hashStart;
    private int     hashEnd;

    private  int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHashStart(int hashStart) {
        this.hashStart = hashStart;
    }

    public void setHashEnd(int hashEnd) {
        this.hashEnd = hashEnd;
    }

    /**
     * 判断当前hash值是否应该在这个区段
     * @param hash hash位置
     * @return 位置是否正确
     */
    public boolean isHash(int hash){
        return hash > hashStart && hash < hashEnd;
    }

    @Override
    public String toString() {
        return "HashBean{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", hashStart=" + hashStart +
                ", hashEnd=" + hashEnd +
                ", count=" + count +
                '}';
    }
}
