package com.liub.baselibrary.net;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public interface BaseObserverListener<T> {
    void onSuccess(T result);

    void onComplete();

    void onError(Throwable throwable);

    void onBusinessError(int code, String msg);//业务错误
}
