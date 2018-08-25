package cn.missbe.rdis.client.util;

public class CommandProcessUtil {

    public static boolean checkCommand(String command){

        String[] commands =  command.split(" ");
        ////命令长度小于等于1不正确
        if(commands.length <= 1){
            return false;
        }
        ///根据命令类型判断命令是否正确
        switch(commands[0].toLowerCase()){
            case "set":
            case "lset":
            case "hset":
                return commands.length >= 3;
            case "get":
            case "del":
                return commands.length == 2;
            default:
                return  false;
        }
    }
}
