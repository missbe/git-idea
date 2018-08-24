package cn.missbe.redis.map;

import cn.missbe.redis.App;

import java.util.List;

public class KeyValueNode {
    private Object value;   //键对应的值
    private int hit;        //命中数
    private long timeOut;  //过期时间

    KeyValueNode() {
        hit = 0;
        timeOut = System.currentTimeMillis() + App.TIMEOUT;
    }

    void setValue(Object value) {
        this.value = value;
    }

    int getHit() {
        return hit;
    }

    void setHit(int hit) {
        this.hit = hit;
    }

    public long getTimeOut() {
        return timeOut;
    }

    @SuppressWarnings("unchecked")
    String getValue() {
         StringBuilder builder = new StringBuilder();
        if(value instanceof  String){
            builder.append((String)value);
        }else if(value instanceof List){
            ////泛型擦除，不能判断List里面的具体类型
            for (String tmp : (List<String>)value)
                builder.append(tmp);
        }
        return  builder.toString() ;
    }

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        if(value instanceof  String){
//            builder.append((String)value);
//        }else if(value instanceof List){
//            ////泛型擦除，不能判断List里面的具体类型
//            for (String tmp : (List<String>)value)
//                builder.append(tmp);
//        }
//        return  builder.toString() ;
//    }
}
