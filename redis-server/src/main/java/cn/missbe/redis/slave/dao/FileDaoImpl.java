package cn.missbe.redis.slave.dao;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.bean.RedisBean;
import cn.missbe.redis.slave.map.IRedisMap;
import cn.missbe.redis.slave.map.RedisMapImpl;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

public class FileDaoImpl  implements IRedisMapDao {
    private File  redisFile = createlFile();

    private boolean isExist(){
        return  redisFile.exists() && redisFile != null;
    }

    private File createlFile(){
        String path = FileDaoImpl.class.getClassLoader().getResource(App.REDIS_CONFIG_NAME).getPath();
        path = path.substring(0,path.lastIndexOf("/")+1);
        path += App.SAVE_FILENAME;
        return new File(path);
    }
    @Override
    public void clear() {
        if(!isExist()){
            FileUtils.deleteQuietly(redisFile);
        }//end if
    }

    @Override
    public void save() {
        if(!isExist()){
            redisFile = createlFile();
        }

        IRedisMap instance = RedisMapImpl.RedisMapHolder.getInstance();
        List<RedisBean> list = ((RedisMapImpl) instance).listRedisBean();
        try {
            FileUtils.write(redisFile, JSON.toJSONString(list), Charset.forName("UTF-8"));
        } catch (IOException e) {
            PrintUtil.print("写入文件失败，文件IO异常.", SystemLog.Level.error);
        }
        PrintUtil.print("持久化数据到文件结束.", SystemLog.Level.info);
    }

    @Override
    public void save(List<RedisBean> beans) {
        if(!isExist()){
            redisFile = createlFile();
        }

        try {
            FileUtils.write(redisFile, JSON.toJSONString(beans), Charset.forName("UTF-8"));
        } catch (IOException e) {
            PrintUtil.print("写入文件失败，文件IO异常.");
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
}
