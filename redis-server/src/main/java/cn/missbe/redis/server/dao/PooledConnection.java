package cn.missbe.redis.server.dao;

import java.sql.Connection;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-31 下午5:28
 *   @author lyg
 *   @version 1.0
 *   @Description 数据库连接池包装对象
 **/

class PooledConnection {
    Connection connection ; ///数据库连接
    private boolean busy = false; ///该连接是否正使用

    ////根据一个Connection构造一个PooledConnection
    PooledConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 获取该连接对象
    **/
    Connection getConnection() {
        return connection;
    }

    /**
     * 设置该连接
     */
    void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * 该连接是否空闲
     **/
    boolean isBusy() {
        return busy;
    }

    /**
     * 设置该连接是否空闲
     **/
    void setBusy(boolean busy) {
        this.busy = busy;
    }
}
