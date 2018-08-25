package cn.missbe.redis.util;

import cn.missbe.redis.map.IRedisMap;
import cn.missbe.redis.map.RedisMapImpl;

public class CommandProcessUtil {

    public static String processCommand(String command){
        String msg = "命令不能为空或NULL";
        if(command == null || command.equals("")){
            return msg;
        }

        IRedisMap redisMap = RedisMapImpl.RedisMapHolder.getInstance(); ////确保单例模式

        String[] commands = command.split(" ");
        switch(commands[0].toLowerCase()){
            case "set":
            case "lset":
            case "hset":
                int length = commands[0].length() + commands[1].length() + 2;
                msg = redisMap.put(commands[0],commands[1],command.substring(length));
                break;
            case "get":
                msg = redisMap.get(commands[1]);
                break;
            case "del":
                msg = redisMap.delete(commands[1]);
                break;

        }

        return msg;
    }


    public static boolean checkCommand(String commandLine){
        String[] commands =  commandLine.split(" ");
        ////命令长度小于等于1不正确
        if(commands.length <= 1){
            return false;
        }
        ///根据命令类型判断命令是否正确
        switch(commands[0].toLowerCase()){
            case "get":
            case "del":
                return commands.length == 2;
            case "set":
            case "lset":
            case "hset":
                return commands.length >= 3;
            default:
                return  false;
        }
    }
}
