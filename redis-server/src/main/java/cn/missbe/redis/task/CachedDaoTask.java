package cn.missbe.redis.task;

import cn.missbe.redis.App;

public class CachedDaoTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("执行DAO:" + App.REDIS_MODIFY_NUMBER);
            if(App.REDIS_MODIFY_NUMBER >= App.REDIS_SAVE_MAP.get(Long.MAX_VALUE)){
                App.REDIS_MODIFY_NUMBER = 0; ///reset initial 0
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
