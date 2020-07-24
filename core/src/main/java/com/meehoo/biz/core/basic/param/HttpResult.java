package com.meehoo.biz.core.basic.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口统一返回值
 * @author zc
 * @date 2019-07-09
 */
@Data
public class HttpResult<T> implements Serializable{
    public static final int Flag_Success = 0;
    public static final int Flag_Fail= 1;
    public static final int Flag_NotLogin = 4004;
    public static final int Flag_HasCustomer = 2;
    public static final int Flag_PwdWrong= 3;

    private T data;
//    private Throwable ex;
    private String msg;
    private int flag;

    public HttpResult() {
        msg = "成功";
        flag = Flag_Success;
    }

    public HttpResult(T data) {
        this.data = data;
        msg = "成功";
        flag = Flag_Success;
    }

    public HttpResult(Throwable ex) {
        this.msg = ex.getMessage();
        flag = Flag_Fail;
    }
}
