package cn.missbe.redis.slave.util;

import cn.missbe.redis.slave.App;
import cn.missbe.util.FileUtils;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.io.*;
import java.util.Objects;

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
    private static String path = Objects.requireNonNull(FileDaoUtils.class.getClassLoader().getResource(App.IP_CLUSTER_FILE)).getPath();

    private static File getFile(String fileName){
        fileName = path.substring(0, path.lastIndexOf(App.IP_CLUSTER_FILE)+1) + fileName;
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
    public static void savePersistence(String contents,String prefixx){
        File tmp;
        try {
            tmp = File.createTempFile("redis_temp_",".json");
        } catch (IOException e) {
           PrintUtil.print("临时文件创建错误，尝试在项目类路径下创建临时文件.", SystemLog.Level.error);
           tmp = new File(path.substring(0, path.lastIndexOf(App.IP_CLUSTER_FILE)+1) + "tmp.json");
        }
        try(
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp)))
        ) {
            buffer.write(contents);
            buffer.flush();

//            System.out.println(tmp.getCanonicalPath());///获取临时文件数据

            File file = getFile(prefixx + "_dump.json");
            FileUtils.copy(tmp,file);///复制文件，通过流

            ///删除临时文件
            tmp.deleteOnExit();
        } catch (FileNotFoundException e) {
            PrintUtil.print("文件查找失败，请检查文件路径.详情:" + e.getCause(), SystemLog.Level.error);
        } catch (IOException e) {
            PrintUtil.print("文件写入IO异常，详情:" + e.getCause(), SystemLog.Level.error);
        }
    }

}
