package com.charley.rtsp.entity;

import java.io.Serializable;

/**
 * @author wchongxiang_vendor
 * @version 1.0
 * @ClassName ResultModel
 * @Description: TODO
 * @date 2018/10/31 11:44
 **/
public class ResultModel implements Serializable {

    public boolean success=true;
    public String message="operation success";
    public Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
