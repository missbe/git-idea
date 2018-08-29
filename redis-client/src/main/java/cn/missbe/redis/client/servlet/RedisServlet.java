package cn.missbe.redis.client.servlet;

import cn.missbe.redis.client.util.CommandProcessUtil;
import cn.missbe.redis.client.hash.ConsistentHash;
import org.jetbrains.annotations.Contract;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午10:50
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/
@WebServlet(urlPatterns = {"/redis/data/cached"})
public class RedisServlet extends HttpServlet {
    private ConsistentHash<String> consistentHash ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg;
       String command = req.getParameter("command");
       boolean isCommand =  CommandProcessUtil.isCommand(command);
       if(!isCommand){
           msg = "提示:你输入的命令不合法.";
       }
       String key = req.getParameter("key");
       String value = req.getParameter("value");
    }

    /**
     * 加载服务器IP信息，配置一至性Hash对象
     */
    private void hashProcess() {
        List<String> list = new ArrayList<>(getServerInfo());
        int virtualNode = 5;
        consistentHash = new ConsistentHash<>(virtualNode,list);
    }

    /**
     * 从备份服务器上获取主服务服务器配置信息
     * @return  服务器IP和端口列表
     */
    @Contract(pure = true)
    private Collection<? extends String> getServerInfo() {
        return new ArrayList<>();
    }

    /**
     * 根据Key计算hash值，将值发送到对应服务器
     * @param key 客户端发送的值
     * @return 返回服务器IP信息
     */
    private String getServer(String key){
        return consistentHash.get(key);
    }
}
