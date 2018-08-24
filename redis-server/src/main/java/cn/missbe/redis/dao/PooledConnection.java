package cn.missbe.redis.dao;

import java.sql.Connection;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:resources_search_java
 *   @Date:18-8-13 下午1:16
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class PooledConnection {
    Connection connection = null; ///数据库连接
    boolean busy = false; ///该连接是否正使用

    ////根据一个Connection构造一个PooledConnection
    public PooledConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 获取该连接对象
    **/
    public Connection getConnection() {
        return connection;
    }

    /**
     * 设置该连接
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 该连接是否空闲
     **/
    public boolean isBusy() {
        return busy;
    }

    /**
     * 设置该连接是否空闲
     **/
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
