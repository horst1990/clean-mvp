package com.hor.mvp.data.http;

/**
 * Created by ARX on 2017/2/7.
 */

public class ApiException extends RuntimeException{
    final static private String DEFAULT_ERROR_CODE = "-1";
    final static private String DEFAULT_ERROR_REASON = "服务器异常";

    private String mCode;
    private String mReason;

    public ApiException() {
        this.mCode = DEFAULT_ERROR_CODE;
        this.mReason = DEFAULT_ERROR_REASON;
    }

    public ApiException(String code, String reason) {
        mCode = code;
        mReason = reason;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }
}
