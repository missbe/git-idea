package cn.missbe.redis.map;

import cn.missbe.redis.App;

public class KeyValueNode {
    private String  value;    //键对应的值
    private long    timeOut;  //过期时间
    private boolean isTimeout;//是否过期

    KeyValueNode(String value) {
        timeOut = System.currentTimeMillis() + App.TIMEOUT;
        this.value = value;
    }

    String getValue() {
        return  value ;
    }

    boolean isTimeout() {
        isTimeout = System.currentTimeMillis() - timeOut > 0;
        return isTimeout;
    }

    public void setTimeout(boolean timeout) {
        isTimeout = timeout;
    }

    @Override
    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        if(value instanceof  String){
//            builder.append((String)value);
//        }else if(value instanceof List){
//            ////泛型擦除，不能判断List里面的具体类型
//            for (String tmp : (List<String>)value)
//                builder.append(tmp);
//        }
        return  value ;
    }
}
