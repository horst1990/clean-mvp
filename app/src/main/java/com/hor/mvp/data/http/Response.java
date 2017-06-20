package com.hor.mvp.data.http;

import java.io.Serializable;

/**
 * Created by ARX on 2017/2/7.
 */

public class Response implements Serializable{

    private static final long serialVersionUID = -251388107413156129L;


    public static final String CODE_SUCCEED = "000000";

    /**
     * code : 200
     * message : 发送验证码成功
     * response : {}
     */

    private String code;
    private String message;
    private String response;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
