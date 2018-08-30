package cn.missbe.redis.slave.util;

import cn.missbe.redis.slave.App;
import cn.missbe.redis.slave.dao.FileDaoImpl;
import cn.missbe.redis.slave.dao.IRedisDateRead;
import cn.missbe.redis.slave.map.IRedisMap;
import cn.missbe.redis.slave.map.RedisMapImpl;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

public class CommandProcessUtil {

    private final static String[] commands = {"set","lset", "hset", "get", "del", "expire","backup"};


    public static byte[] processCommand(String command){
        String msg = "命令不能为空或NULL";
        if(command == null || command.equals("")){
            return msg.getBytes(StandardCharsets.UTF_8);
        }

        IRedisMap redisMap = RedisMapImpl.RedisMapHolder.getInstance(); ////确保单例模式

        String[] commands = command.split(" ");
        switch(commands[0].toLowerCase()){
            case "set":
            case "lset":
            case "hset":
                ////持久化对象计数器加1
                App.modifyCounter();
                int length = commands[0].length() + commands[1].length() + 2;
                msg = redisMap.put(commands[0],commands[1],command.substring(length));
                break;
            case "get":
                ////持久化对象计数器加1
                App.modifyCounter();
                msg = redisMap.get(commands[1]);
                break;
            case "del":
                ////持久化对象计数器加1
                App.modifyCounter();
                msg = redisMap.delete(commands[1]);
                break;
            case "expire":
                msg = redisMap.expire(commands[1],commands[2].equalsIgnoreCase("ever") ? Long.MAX_VALUE : Long.valueOf(commands[2]));
                break;
            case "backup":
                IRedisDateRead dateRead = FileDaoImpl.getInstance();
                return dateRead.read(redisMap);
        }
        return msg.getBytes(StandardCharsets.UTF_8);
    }


    public static boolean checkCommand(@NotNull String commandLine){
        String[] commands =  commandLine.split(" ");
        ////命令长度小于1不正确
        if(commands.length < 1){
            return false;
        }
        ///根据命令类型判断命令是否正确
        String command = commands[0].toLowerCase();
        switch(command){
            case "get":
            case "del":
                return commands.length == 2 && isCommand(command);
            case "set":
            case "expire":
                return commands.length == 3 && isCommand(command);
            case "lset":
            case "hset":
                return commands.length >= 3 && isCommand(command);
            case"backup":
                return commands.length == 1 && isCommand(command);
            default:
                return  false;
        }
    }

    /**
     * 判断给定字符串是否是命令
     * @param command 命令字符串
     * @return 该字符串为命令返回true,否则返回false
     */
    private static boolean isCommand(String command){
        for(String key : commands){
            if(command.equalsIgnoreCase(key)){
                return true;
            }//end if
        }//end for
        return false;
    }
}
