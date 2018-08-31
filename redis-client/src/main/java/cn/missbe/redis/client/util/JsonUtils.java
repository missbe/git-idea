package cn.missbe.redis.client.util;

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
 *   @Date:18-8-29 下午3:06
 *   @author lyg
 *   @version 1.0
 *   @Description 读取配置中的Json数据
 **/

public class JsonUtils {
    /**
     * 获取配置文件中的IP主端口信息
     * @return List<String[]>数组，第一位为IP，第二位为端口
     * @throws IOException 配置文件读取异常
     */
    public static List<String[]> getIpCluster(String filename,String attr) throws IOException {
        List<String[]> result = new ArrayList<>();

        JSONArray array = toJsonArray(filename,attr);

        if(array == null){
            PrintUtil.print("配置文件不存在或者配置错误，程序无法获取服务器IP等信息，程序结束.", SystemLog.Level.error);
            System.exit(-1);
        }

        int size = array.size();
        for(int i = 0; i < size; i++){
            JSONObject jsonObject = array.getJSONObject(i);
            if(jsonObject.getString("port")  != null && jsonObject.getString("ip") != null ){
                String[] ips = new String[2];
                ips[0] = jsonObject.getString("ip");
                ips[1] = jsonObject.getString("port");
                result.add(ips);
            }//end  if
        }

        return result;
    }///end

    /**
     * 读取json配置文件，将内容处理JSONArray返回
     * @return JSONArray对象
     * @throws IOException IO异常，配置文件读取失败
     */
    private static JSONArray toJsonArray(String fileName,String attr) throws IOException {
        InputStream ios = JsonUtils.class.getClassLoader().getResourceAsStream(fileName);

        byte[] buff = new byte[1024];
        int len ;
        StringBuilder res = new StringBuilder();
        while ((len =ios.read(buff)) > 0){
            res.append(new String(buff,0,len));
        }
        ios.close();

        return JSONObject.parseObject(res.toString()).getJSONArray(attr);
    }

}
