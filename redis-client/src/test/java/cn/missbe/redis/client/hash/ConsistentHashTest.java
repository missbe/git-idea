package cn.missbe.redis.client.hash;

import cn.missbe.util.PrintUtil;
import cn.missbe.util.StringUtils;
import cn.missbe.util.SystemLog;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午9:52
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class ConsistentHashTest {
    private List<String> list;
    private final String ip1 = "127.0.0.1:65532";
    private final String ip2 = "125.81.58.128:65531";
    private final String ip3 = "128.81.59.156:65530";
    private final String ip4 = "123.81.49.97:65529";
    private ConsistentHash<String> stringConsistentHash;
     {
        list = new ArrayList<>();
        list.add(ip1);
        list.add(ip2);
        list.add(ip3);
        list.add(ip4);
        stringConsistentHash = new ConsistentHash<>(16,list);
    }

    @Test
    public void remove() {
        stringConsistentHash.remove(ip2);
    }

    @Test
    public void get() {
        PrintUtil.print( "客户端初始化成功.", SystemLog.Level.info);
        Random random = new Random();
        int num_ip1 = 0, num_ip2 = 0, num_ip3 = 0, num_ip4 = 0;
        for(int i = 0; i < 100000; i++){
            String res = stringConsistentHash.get( StringUtils.random(2 + random.nextInt(10) ));
            System.out.println("RESULT:" + res);
            switch (res){
                case ip1:
                    num_ip1++;
                    break;
                case  ip2:
                    num_ip2++;
                    break;
                case ip3:
                    num_ip3++;
                    break;
                case ip4:
                    num_ip4++;
                    break;
            }
        }///end for
        System.out.println(num_ip1 + ":" + num_ip2  + ":" + num_ip3 + ":" + num_ip4);
    }
}