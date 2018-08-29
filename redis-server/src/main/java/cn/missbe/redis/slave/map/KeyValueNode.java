package cn.missbe.redis.slave.map;

import cn.missbe.redis.slave.App;

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

public class KeyValueNode {
    private String  value;    //键对应的值
    private long    timeOut;  //过期时间

    KeyValueNode(String value) {
        timeOut = nowSystemMills() + App.TIMEOUT * 1000; ///当前时间毫秒数加上缓存毫秒时间
        this.value = value;
    }

    String getValue() {
        return  value ;
    }

    /**
     * 判断该对象是否已经过期
     * @return true表示过期，false表示未过期
     */
    boolean isTimeout() {
//        System.out.println(nowSystemMills() + "-" + timeOut + "=" + (nowSystemMills() - timeOut));
//        System.out.println(DateUtil.nowTimeInMillis() + "-" + timeOut + "=" + (DateUtil.nowTimeInMillis() - timeOut));
//        System.out.println(new Date().getTime() + "-" + timeOut + "=" + (new Date().getTime() - timeOut));
        if(timeOut == Long.MAX_VALUE){
            return false;
        }
        //是否过期
        return nowSystemMills() - timeOut > 0;
    }

    /**
     * 获取当前系统时间毫秒数
     * @return 系统时间毫秒数
     */
    private long nowSystemMills(){
        return System.currentTimeMillis();
    }

    void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    long getTimeOut() {
        return timeOut;
    }

    /**
     * 将调用对象复制一个副本，返回该副本
     * @return 副本对象
     */
    KeyValueNode copyOf(){
        KeyValueNode copyNode = new KeyValueNode(this.value);
        copyNode.timeOut = this.timeOut;
        return  copyNode;
    }

    @Override
    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        if(value instanceof  String){
//            builder.append((String)value);
//        }else if(value instanceof List){
//            ////泛型擦除，不能判断List里面的具体类型
//            for (String tmp : (List<String>)value)
//                builder.append(tmp);
//        }
        return  value ;
    }
}
