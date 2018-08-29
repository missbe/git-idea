package cn.missbe.redis.client.util;

import org.jetbrains.annotations.NotNull;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 下午12:22
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class CommandProcessUtil {
    private final static String[] commands = {"set","lset", "hset", "get", "del", "expire"};

    /**
     * 检查格式如:set key value形式的命令串是否合法
     * @param command 命令字符串
     * @return 返回值
     */
    public static boolean checkCommand(@NotNull String command){

        String[] commands =  command.split(" ");
        ////命令长度小于等于1不正确
        if(commands.length <= 1){
            return false;
        }
        ///根据命令类型判断命令是否正确
        switch(commands[0].toLowerCase()){
            case "set":
            case "expire":
                return commands.length == 3 && isCommand(commands[0]);
            case "lset":
            case "hset":
                return commands.length >= 3 && isCommand(commands[0]);
            case "get":
            case "del":
                return commands.length == 2 && isCommand(commands[0]);
            default:
                return  false;
        }
    }

    /**
     * 判断给定字符串是否是命令
     * @param command 命令字符串
     * @return 该字符串为命令返回true,否则返回false
     */
    public static boolean isCommand(String command){
        for(String key : commands){
            if(command.equalsIgnoreCase(key)){
                return true;
            }//end if
        }//end for
        return false;
    }
}
