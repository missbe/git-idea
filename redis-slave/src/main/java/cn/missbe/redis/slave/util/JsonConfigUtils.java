package cn.missbe.redis.slave.util;

import cn.missbe.redis.slave.App;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-27 下午7:36
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class JsonConfigUtils {
    /**
     * 获取配置文件中的IP主端口信息
     * @return List<String[]>数组，第一位为IP，第二位为端口
     * @throws IOException 配置文件读取异常
     */
    public static List<String[]> getIpCluster() throws IOException {
        List<String[]> result = new ArrayList<>();

        JSONArray array = toJsonArray();

        for(int i = 0; i < array.size(); i++){
            JSONObject object = array.getJSONObject(i);
            String[] ips = new String[2];
            ips[0] = object.getString("ip");
            ips[1] = object.getString("port");
            result.add(ips);
        }

        return result;
    }///end

    /**
     * 获取配置文件中的任务调度时间
     * @return 配置文件设置的任务间隔时间数组,第一位为初始时间，第二位任务调度间隔时间
     * @throws IOException 配置文件读取异常
     */
    public static long[] getDelayTime() throws IOException {
        long[] delay = new long[2];
        JSONArray array = toJsonArray();

        int i = 0;
        for(; i < array.size(); i++){
            JSONObject object = array.getJSONObject(i);
            if(object.getLong("initial_delay") != null && object.getLong("period_delay")  != null){
                delay[0] = object.getLong("initial_delay");
                delay[1] = object.getLong("period_delay");
                break;////只查找到第一个设置，其它忽略
            }
        }
        if(i >= array.size()){
            PrintUtil.print("配置文件中没有配置该参数或者配置不正确，请检查.", SystemLog.Level.warning);
        }
        return  delay;
    }

    /**
     * 读取json配置文件，将内容处理JSONArray返回
     * @return JSONArray对象
     * @throws IOException IO异常，配置文件读取失败
     */
    private static JSONArray toJsonArray() throws IOException {
        InputStream ios = JsonConfigUtils.class.getClassLoader().getResourceAsStream(App.IP_CLUSTER_FILE);

        byte[] buff = new byte[1024];
        int len = 0;
        StringBuilder res = new StringBuilder();
        while ((len =ios.read(buff)) > 0){
            res.append(new String(buff,0,len));
        }
        ios.close();

        return JSONArray.parseArray(res.toString());
    }

}
