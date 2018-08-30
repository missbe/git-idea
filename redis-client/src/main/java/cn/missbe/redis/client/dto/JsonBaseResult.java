package cn.missbe.redis.client.dto;

import java.io.Serializable;

/**
 *   Description:java_code
 *   mail: love1208tt@foxmail.com
 *   Copyright (c) 2018. missbe
 *   This program is protected by copyright laws.
 *   Program Name:redisjava
 *   @Date:18-8-29 上午11:34
 *   @author lyg
 *   @version 1.0
 *   @Description
 **/

public class JsonBaseResult implements Serializable {
    private Object result;
    private boolean success;

    public JsonBaseResult(Object result, boolean success) {
        super();
        this.result = result;
        this.success = success;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        if (this.success) {
            return result.toString();
        } else {
            return "正常";
        }
    }

}
