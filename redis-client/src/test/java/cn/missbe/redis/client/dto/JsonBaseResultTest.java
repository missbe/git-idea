package cn.missbe.redis.client.dto;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午11:35
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class JsonBaseResultTest {

    @Test
    public void result() {
        String res = "value1 value2 value3 value4";
        JsonBaseResult baseResult = new JsonBaseResult(res, true);
        System.out.println(JSON.toJSONString(baseResult));

        baseResult.setResult(res);
    }
}