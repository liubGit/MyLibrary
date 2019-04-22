package com.liub.baselibrary.net;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.text.ParseException;

import okhttp3.Connection;
import retrofit2.HttpException;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public class RetrofitException {

    /**
     * ========================返回的code==================================
     */
    private static int UNAUTHORIZED = 401;
    private static int FORBIDDEN = 403;
    private int NOT_FOUND = 404;
    private int REQUEST_TIMEOUT = 408;
    private int INTERNAL_SERVER_ERROR = 500;
    private int BAD_GATEWAY = 502;
    private int SERVICE_UNAVAILABLE = 503;
    private int GATEWAY_TIMEOUT = 504;

    //未知错误
    private int UNKNOWN = 1000;
    //解析错误
    private static int PARSE_ERROR = 1001;
    //网络错误
    private static int NETWORD_ERROR = 1002;
    //协议出错
    private static int HTTP_ERROR = 1003;
    //证书出错
    private static int SSL_ERROR = 1005;

    public static class ResponseThrowable extends Exception {
        private int code = 0;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private class ServerException extends RuntimeException {
        private int code = 0;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static ResponseThrowable getResponseThrowable(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            ex = new ResponseThrowable();
            int code = ((HttpException) e).code();
            if (code == UNAUTHORIZED || code == FORBIDDEN) {
                ex.code = HTTP_ERROR;
                ex.message = "请检查权限";
            } else {
                ex.code = HTTP_ERROR;
                ex.message = "请检查你的网络连接";
            }
            return ex;

        } else if (e instanceof ServerException) {
            ex = new ResponseThrowable();
            ex.code = ((ServerException) e).code;
            ex.message = ((ServerException) e).message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable();
            ex.code = PARSE_ERROR;
            ex.message = "数据解析错误";
            return ex;
        } else if (e instanceof Connection) {
            ex = new ResponseThrowable();
            ex.code = NETWORD_ERROR;
            ex.message = "网络连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable();
            ex.code = SSL_ERROR;
            ex.message = "证书验证失败";
            return ex;
        } else {
            ex = new ResponseThrowable();
            ex.code = NETWORD_ERROR;
            ex.message = "请检查你的网络连接";
            return ex;
        }
    }
}
