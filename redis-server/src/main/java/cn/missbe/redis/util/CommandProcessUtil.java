package cn.missbe.redis.util;

import cn.missbe.redis.map.IRedisMap;
import cn.missbe.redis.map.RedisMapImpl;

public class CommandProcessUtil {

    public static String processCommand(String command){
        String msg = "";
        if(command == null || command.equals("")){
            msg = "命令不能为空或NULL";
            return msg;
        }
        IRedisMap redisMap = RedisMapImpl.RedisMapHolder.getInstance(); ////确保单例模式
        String[] commands = command.split(" ");
        if(commands[0].equals("set")){
            int length = commands[0].length() + commands[1].length() + 2;
            msg = redisMap.put(commands[1],command.substring(length));
        }else if(commands[0].equalsIgnoreCase("get")){
            msg = redisMap.getValue(commands[1]);
        }else if(commands[0].equalsIgnoreCase("del")){
            msg = redisMap.delete(commands[1]);
        }
        return msg;
    }

    public static boolean checkCommand(String command){
        if(command.length() <= 1){
            return false;
        }
        String[] commands =  command.split(" ");
        if(commands[0].equalsIgnoreCase("set")){
            return commands.length >= 3;
        }else if(commands[0].equalsIgnoreCase("get") || commands[0].equalsIgnoreCase("del")){
            return commands.length == 2;
        }
        return  false;
    }
}
