package cn.missbe.redis.client.net;

import cn.missbe.redis.client.App;
import cn.missbe.redis.client.util.JsonUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 下午2:33
 *   @author lyg
 *   @version 1.0
 *   @Description 负责从备份服务器拉取主服务器的IP和端口信息
 **/

public class RedisServerInfo {

    private static List<String> infos = serverInfo();

    /**
     * 从备份服务器上面摘取数据
     * @return 数据列表
     */
    private static List<String>  serverInfo(){

        List<String> infoList = new ArrayList<>();
        List<String[]> infos = null;
        boolean flag = false;

        PrintUtil.print("从备份服务器加载服务器配置信息..", SystemLog.Level.info);
        try {
            infos = JsonUtils.getIpCluster(App.IP_SLAVE_FILE,"slaves");
        } catch (IOException e) {
            PrintUtil.print("客户端配置信息加载失败.." + e.getCause(), SystemLog.Level.error);
        }

        assert infos != null;

        for (String[]  ips : infos){
            ///连接
            Socket socket;
            try {
                socket = new Socket(ips[0], Integer.valueOf(ips[1]));
            } catch (IOException e) {
                PrintUtil.print("从备份服务器加载服务器配置信息失败.." + e.getCause(), SystemLog.Level.info);
                flag = true;
                break;
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                infoList.add(reader.readLine());
                ///关闭
                reader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end
        if(flag){
            try {
                PrintUtil.print("从客户端加载服务器配置信息..", SystemLog.Level.info);
                infos = JsonUtils.getIpCluster(App.IP_SLAVE_FILE,"servers");
                for (String[]  ips : infos){
                    infoList.add(ips[0] +":" +ips[1]);
                }//end for
                return infoList;////从本地服务器读取数据
            } catch (IOException e1) {
                PrintUtil.print("服务器信息加载失败，系统退出." + e1.getCause(), SystemLog.Level.error);
                System.exit(-1);
            }
        }
        return infoList;
    }//end method



    @NotNull
    public static String[] getIps() {
        List<String> ips = new ArrayList<>();

        for (String ip : infos) {
            Collections.addAll(ips, ip.split("-"));///分隔信息
        }//end for
        return ips.toArray(new String[0]);
    }
}
