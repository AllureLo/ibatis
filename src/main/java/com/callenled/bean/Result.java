package com.callenled.bean;

import java.io.Serializable;

/**
 * @Author: callenled
 * @Date: 19-8-16 下午2:34
 */
public class Result implements Serializable {

    private static final long serialVersionUID = 3350135983674896382L;
    /**
     * 成功标示
     */
    private boolean success;

    /**
     * 错误提示
     */
    private String errMsg;

    /**
     * 返回参数
     */
    private Object data;

    private Result(boolean success) {
        super();
        this.success = success;
    }

    private Result(boolean success, Object data) {
        super();
        this.success = success;
        this.data = data;
    }

    private Result(boolean success, String errMsg, Object data) {
        super();
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result success(Object data) {
        return new Result(true, data);
    }

    public static Result success(String errMsg, Object data) {
        return new Result(true, errMsg, data);
    }

    public static Result error(String errMsg) {
        return new Result(false, errMsg, null);
    }
}
