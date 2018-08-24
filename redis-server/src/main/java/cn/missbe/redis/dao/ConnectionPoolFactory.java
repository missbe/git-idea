package cn.missbe.redis.dao;

import cn.missbe.redis.App;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

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

public class ConnectionPoolFactory {
    private static final ConnectionPool connectionPool;

    static {
        PropertiesUtil.reloadPropes();
        HashMap<String, String> propes = PropertiesUtil.getDbProps();

//        System.out.println("DB Properties" + propes);

        String jdbcDriver = propes.get(App.JDBC_DRIVER);
        String dbUrl = propes.get(App.DB_URL);
        String userName = propes.get(App.DB_USERNAME);
        String password = propes.get(App.DB_PASSWORD);

//        System.out.println(jdbcDriver + "--" + dbUrl);

        connectionPool = new ConnectionPool(jdbcDriver, dbUrl, userName, password);
    }


    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public static void returnConnection(Connection connection){
        connectionPool.returnConnection(connection);
    }

    public static void refreshConnection() throws SQLException {
        connectionPool.refreshConnection();
    }

    public static void closeConnectionPool() throws SQLException {
        connectionPool.closeConnectionPool();;
    }

    public static void setMaxConnections(int maxConnections) {
        connectionPool.setMaxConnections(maxConnections);
    }

    public void setIncrementalConnections(int incrementalConnections) {
        connectionPool.setIncrementalConnections(incrementalConnections);
    }

}
