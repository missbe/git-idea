package cn.missbe.rdis.client.util;

public class CommandProcessUtil {
    private final static String[] commands = {"set","lset", "hset", "get", "del", "expire"};

    public static boolean checkCommand(String command){

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
    private static boolean isCommand(String command){
        for(String key : commands){
            if(command.equalsIgnoreCase(key)){
                return true;
            }//end if
        }//end for
        return false;
    }
}
