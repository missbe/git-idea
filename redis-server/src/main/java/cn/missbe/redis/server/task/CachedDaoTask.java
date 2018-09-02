package cn.missbe.redis.server.task;

import cn.missbe.redis.server.App;
import cn.missbe.redis.server.dao.FileDaoImpl;
import cn.missbe.redis.server.dao.IRedisDataDao;
import cn.missbe.redis.server.dao.RedisDataDaoImpl;
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
 *   @Description 缓存对象持久化到硬盘任务，负责持久化
 **/

public class CachedDaoTask implements Runnable {
    private IRedisDataDao dao       = new RedisDataDaoImpl();///负责写入数据库
    private IRedisDataDao fileDao   = FileDaoImpl.getInstance();///负责写入文件
    private App.RedisSavePersistences persistences;  ///负责持久化策略

    CachedDaoTask(App.RedisSavePersistences  persistences) {
        this.persistences = persistences;
    }

    @Override
    public void run() {

//        PrintUtil.print("当前" + Thread.currentThread().getName() + "线程-当前访问量:" + persistences.getNowCount(), SystemLog.Level.info);
        if(persistences.isPersistences()){

            String fileName = App.PORT + "_" + App.SAVE_FILENAME;
            if(App.PERSISTENCE_MEDIA.equalsIgnoreCase("db")){
                PrintUtil.print(Thread.currentThread().getName() + "线程正在将数据持久化到数据库，当前访问量:" +  persistences.getNowCount(), SystemLog.Level.info);
                dao.clear();///清除数据库原来的数据
                dao.save(fileName); ///持久化所有未过期键值对对象到数据库
            }else if(App.PERSISTENCE_MEDIA.equalsIgnoreCase("file")){
                PrintUtil.print(Thread.currentThread().getName() + "线程正在将数据持久化到文件，当前访问量:" +  persistences.getNowCount(), SystemLog.Level.info);
                fileDao.clear();
                fileDao.save(fileName);
            }
            PrintUtil.print("将当前访问量清零重新计算:" + persistences.getNowCount()+ " -> 0" , SystemLog.Level.info);
            persistences.clearCounter(); ////将计数器清零
        }///end persistence
    }
}
