package cn.missbe.redis.dao;

import cn.missbe.redis.bean.RedisBean;
import cn.missbe.util.PrintUtil;
import cn.missbe.util.SystemLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RedisMapDaoImpl implements IRedisMapDao {

    private Connection getConnection(){
        Connection conn = null;
        try {
            conn = ConnectionPoolFactory.getConnection();
        } catch (SQLException e) {
            PrintUtil.print("数据库连接池获取连接失败，请检查." + e.getLocalizedMessage(), SystemLog.Level.error);
        }
        return conn;
    }

    @Override
    public boolean save(RedisBean bean) {
        Connection conn = getConnection();

        String sql = "INSERT INTO redis(`key`,value,hit,timeout) VALUES (?, ?, ?, ?)";
        int affect = 0;
        try {
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, bean.getKey());
            prep.setObject(2, bean.getValue());
            prep.setInt(3, bean.getHit());
            prep.setLong(4, bean.getTimeout());
            affect = prep.executeUpdate();///返回被影响的行数
        } catch (SQLException e) {
            PrintUtil.print("创建插入执行语句失败，请检查.." + e.getLocalizedMessage(), SystemLog.Level.error);
        }
        return affect > 0 ;
    }

    @Override
    public void save(List<RedisBean> beans) {
        int suc = 0;
        for (RedisBean bean : beans){
            boolean res = save(bean);
            suc += res ? 1 : 0;
        }
        PrintUtil.print("成功写入数据库:" + suc + "条，失败:" + (beans.size()-suc));
    }

    @Override
    public boolean update(RedisBean bean) {
        Connection conn = getConnection();
        String sql = "UPDATE redis SET value=?,hit=?,timeout=? WHERE `key`=?";
        int affect = 0;
        try {
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setObject(1,bean.getValue());
            prep.setInt(2, bean.getHit());
            prep.setLong(3, bean.getTimeout());
            prep.setString(4, bean.getKey());
            affect = prep.executeUpdate();  ///返回更新后的结果
        } catch (SQLException e) {
            PrintUtil.print("创建更新执行语句失败，请检查.." + e.getLocalizedMessage(), SystemLog.Level.error);
        }
        return affect > 0;
    }

    @Override
    public boolean delete(RedisBean bean) {
        Connection conn = getConnection();
        String sql = " DELETE FROM redis WHERE `key`=?";
        int affect = 0;
        try {
            PreparedStatement prep = conn.prepareStatement(sql);
             prep.setString(1,bean.getKey());
            affect = prep.executeUpdate();  ///返回更新后的结果
        } catch (SQLException e) {
            PrintUtil.print("创建删除执行语句失败，请检查.." + e.getLocalizedMessage(), SystemLog.Level.error);
        }
        return affect > 0;
    }
}
