package cn.missbe.redis.map;

import cn.missbe.redis.App;

public class KeyValueNode {
    private String  value;    //键对应的值
    private long    timeOut;  //过期时间

    KeyValueNode(String value) {
        timeOut = nowSystemMills() + App.TIMEOUT * 1000; ///当前时间毫秒数加上缓存毫秒时间
        this.value = value;
    }

    String getValue() {
        return  value ;
    }

    boolean isTimeout() {
//        System.out.println(nowSystemMills() + "-" + timeOut + "=" + (nowSystemMills() - timeOut));
//        System.out.println(DateUtil.nowTimeInMillis() + "-" + timeOut + "=" + (DateUtil.nowTimeInMillis() - timeOut));
//        System.out.println(new Date().getTime() + "-" + timeOut + "=" + (new Date().getTime() - timeOut));

        //是否过期
        return nowSystemMills() - timeOut > 0;
    }

    /**
     * 获取当前系统时间毫秒数
     * @return 系统时间毫秒数
     */
    private long nowSystemMills(){
        return System.currentTimeMillis();
    }

    void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
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
