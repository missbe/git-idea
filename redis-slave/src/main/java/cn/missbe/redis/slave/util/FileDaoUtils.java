package cn.missbe.redis.slave.util;

import cn.missbe.redis.slave.App;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.*;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-28 上午11:47
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class FileDaoUtils {
    private static String fileName = "dump.json";

    private static File getFile(){
        String path =  FileDaoUtils.class.getClassLoader().getResource(App.IP_CLUSTER_FILE).getPath();
        path = path.substring(0, path.lastIndexOf(App.IP_CLUSTER_FILE)+1);
        fileName = path + fileName;
        File file = new File(fileName);
        if(!file.exists()){
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile() ;
            } catch (IOException e) {
                PrintUtil.print("创建备份文件:" + fileName + "失败.", SystemLog.Level.error);
            }
        }///end if
        return file;
    }
    public static void savePersistence(String contents){
        File file = getFile();
        try(
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        ) {
            buffer.write(contents);
            buffer.flush();
        } catch (FileNotFoundException e) {
            PrintUtil.print("文件查找失败，请检查文件路径.详情:" + e.getCause(), SystemLog.Level.error);
        } catch (IOException e) {
            PrintUtil.print("文件写入IO异常，详情:" + e.getCause(), SystemLog.Level.error);
        }
    }

    public static void setBackFileName(String ip) {
        fileName = ip + "_" + fileName;
    }
}
