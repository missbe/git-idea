package cn.missbe.redis.slave.dao;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.bean.RedisBean;
import cn.missbe.redis.slave.map.IRedisMap;
import cn.missbe.redis.slave.map.KeyValueNode;
import cn.missbe.redis.slave.map.RedisMapImpl;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

public class FileDaoImpl  implements IRedisDataDao,IRedisDateRead{
    private volatile File        redisFile     = createlFile();
    private static   FileDaoImpl FILE_DAO_IMPL = new FileDaoImpl();

    private FileDaoImpl(){}

    /**
     * 获取类对象
     * @return 唯一对象
     */
    @Contract(pure = true)
    public static FileDaoImpl getInstance(){
        return FILE_DAO_IMPL;
    }

    private boolean isExist(){
        return redisFile != null && redisFile.exists();
    }

    @NotNull
    @Contract(" -> new")
    private File createlFile(){
        String path = Objects.requireNonNull(FileDaoImpl.class.getClassLoader().getResource(App.REDIS_CONFIG_NAME)).getPath();
        path = path.substring(0,path.lastIndexOf("/")+1);
        path += App.SAVE_FILENAME;
        return new File(path);
    }

    @Override
    public void clear() {
        if(isExist()){
            FileUtils.deleteQuietly(redisFile);
        }//end if
    }

    @Override
    public void save() {
        if(isExist()){
            redisFile = createlFile();
        }

        List<RedisBean> list = RedisMapImpl.RedisMapHolder.getInstance().allMaps2RedisBean();
        try {
            if(!redisFile.canWrite()){
                Thread.sleep(1000 * 3);
            }
            FileUtils.write(redisFile, JSON.toJSONString(list), Charset.forName("UTF-8"));
        } catch (IOException e) {
            PrintUtil.print("写入文件失败，文件IO异常." + e.getMessage(), SystemLog.Level.error);
        } catch (InterruptedException e) {
            PrintUtil.print("等待文件写入睡眠打断异常.." + e.getCause(), SystemLog.Level.error);
        }
        PrintUtil.print("持久化数据到文件结束.", SystemLog.Level.info);
    }

    @Override
    public void save(List<RedisBean> beans) {
        if(isExist()){
            redisFile = createlFile();
        }
        try {
            if(!redisFile.canWrite()){
                Thread.sleep(1000 * 3);
            }
            FileUtils.write(redisFile, JSON.toJSONString(beans), Charset.forName("UTF-8"));
        } catch (IOException e) {
            PrintUtil.print("写入文件失败，文件IO异常.");
        } catch (InterruptedException e) {
            PrintUtil.print("等待文件写入睡眠打断异常.." + e.getCause(), SystemLog.Level.error);
        }
        PrintUtil.print("持久化数据到文件结束.", SystemLog.Level.info);

    }

    @Override
    public boolean save(RedisBean bean) {
        List<RedisBean> redisBeans = new ArrayList<>();
        redisBeans.add(bean);
        save(redisBeans);
        return false;
    }

    @Override
    public boolean update(RedisBean bean) {
        return false;
    }

    @Override
    public boolean delete(RedisBean bean) {
        return false;
    }

    @Override
    public byte[] read(IRedisMap redisMap) {
        if(!isExist()){
            redisFile = createlFile();
        }
        if(isExist() && redisFile.canRead()){
            try(
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(redisFile)))
            ) {
                String line;
                StringBuilder builder = new StringBuilder();
                while ( (line = reader.readLine()) != null){
                    builder.append(line);
                }
                return builder.toString().getBytes(StandardCharsets.UTF_8);
            } catch (FileNotFoundException e) {
                PrintUtil.print("读取文件时，文件查找失败，读取流创建失败." + e.getCause(), SystemLog.Level.error);
            } catch (IOException e) {
                PrintUtil.print("读取文件时，文件IO异常，读取文件数据失败." + e.getCause(), SystemLog.Level.error);
            }
        }
        ///将map缓存中的键值数据返回
        List<RedisBean> allRedis =  ((RedisMapImpl)redisMap).allMaps2RedisBean();
        return JSON.toJSONString(allRedis).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Map<String, List<KeyValueNode>> read2list(String path) {
        return null;
    }

    @Override
    public Map<String, KeyValueNode> read(String path) {
        return null;
    }

    @Override
    public Map<String, Set<KeyValueNode>> read2Set(String path) {
        return null;
    }
}
