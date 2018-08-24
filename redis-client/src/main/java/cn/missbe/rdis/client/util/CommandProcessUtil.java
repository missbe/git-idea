package cn.missbe.rdis.client.util;

public class CommandProcessUtil {

    public static boolean checkCommand(String command){

        String[] commands =  command.split(" ");
        if(commands.length <= 1){
            return false;
        }else if(commands[0].equalsIgnoreCase("set")){
            return commands.length >= 3;
        }else if(commands[0].equalsIgnoreCase("get") || commands[0].equalsIgnoreCase("del")){
            return commands.length == 2;
        }
        return  false;
    }
}
