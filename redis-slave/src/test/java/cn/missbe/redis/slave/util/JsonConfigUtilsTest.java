package cn.missbe.redis.slave.util;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:56
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class JsonConfigUtilsTest {

    @Test
    public void getIpCluster() throws IOException {
        String msg =  "[\n" +
                "    {\"ip\": \"127.0.0.1\",\n" +
                "    \"port\": \"65532\"\n" +
                "    }\n" +
                "]";
        System.out.println(msg);
        List<String[]> res = JsonConfigUtils.getIpCluster();
        for (String[] tmp : res){
            System.out.println(tmp[0] + ":" + tmp[1]);
        }
    }

    @Test
    public void getDelayTime() throws IOException {
        long [] delays = JsonConfigUtils.getDelayTime();
        System.out.println(delays[0] + ":" + delays[1]);
    }
}