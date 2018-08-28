package cn.missbe.redis.slave.task;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.dao.FileDaoImpl;
import cn.missbe.redis.slave.dao.IRedisDataDao;
import cn.missbe.redis.slave.dao.RedisDataDaoImpl;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

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

public class CachedDaoTask implements Runnable {
    private IRedisDataDao dao       = new RedisDataDaoImpl();
    private IRedisDataDao fileDao   = FileDaoImpl.getInstance();
    private static long  max_count = Long.MIN_VALUE;
    private long count;

    CachedDaoTask(Long aLong) {
        this.count = aLong;
        max_count = count > max_count ? count : max_count;
    }

    @Override
    public void run() {
        if(App.REDIS_MODIFY_NUMBER >= count){

            if(App.PERSISTENCE_MEDIA.equalsIgnoreCase("db")){
                PrintUtil.print("正在将数据持久化到数据库，当前访问量:" +  App.REDIS_MODIFY_NUMBER, SystemLog.Level.info);
                dao.clear();///清除数据库原来的数据
                dao.save(); ///持久化所有未过期键值对对象到数据库
            }else if(App.PERSISTENCE_MEDIA.equalsIgnoreCase("file")){
                PrintUtil.print("正在将数据持久化到文件，当前访问量:" +  App.REDIS_MODIFY_NUMBER, SystemLog.Level.info);
                fileDao.clear();
                fileDao.save();
            }
        }

//        for(Long mum : App.REDIS_SAVE_MAP.values()){//
//        }///end for

        if(App.REDIS_MODIFY_NUMBER >= max_count){
            App.REDIS_MODIFY_NUMBER = 0; ///reset initial 0
            PrintUtil.print("即将当前访问量清零:" + App.REDIS_MODIFY_NUMBER + "->" +  App.REDIS_MODIFY_NUMBER, SystemLog.Level.info);
        }
    }
}
