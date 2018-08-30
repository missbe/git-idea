package cn.missbe.redis.client;

import cn.missbe.redis.client.bean.HashBean;
import cn.missbe.redis.client.net.RedisServerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 上午11:30
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class App {
    public static final  String   SERVER_OK      = "SERVER_OK"; //结束写入标志
    public static final String   IP_SLAVE_FILE  = "slave_ip.json";
    public static final int      HASH_LENGTH    = 300000; ///哈希槽长度
    private static List<HashBean> hashBeans      = new ArrayList<>(); //客户端保持的全局服务器数据

    /**
     * 初始化服务器数据，分槽
     */
    public static void initConfig(){
        String[] ips = RedisServerInfo.getIps();

        int i =0, len = HASH_LENGTH / ips.length;
        for(String ip : ips){
            HashBean bean = new HashBean();
            bean.setHashStart(i++ * len);
            bean.setHashEnd(i * len -1);
            String[] info = ip.split(":");
            bean.setIp(info[0]);
            bean.setPort(Integer.valueOf(info[1]));
            hashBeans.add(bean);///添加进入列表
        }
    }//end initial config

    public static List<HashBean> getHashBeans() {
        return hashBeans;
    }

}
