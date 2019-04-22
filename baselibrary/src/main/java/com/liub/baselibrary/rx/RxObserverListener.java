package com.liub.baselibrary.rx;

import android.util.Log;

import com.liub.baselibrary.base.BaseContract;
import com.liub.baselibrary.net.BaseObserverListener;
import com.liub.baselibrary.net.RetrofitException;

/**
 * Create by liub on 2019/4/8
 * Describe:
 */
public abstract class RxObserverListener<T> implements BaseObserverListener<T> {
    private BaseContract.IView iView;
    public RxObserverListener(BaseContract.IView view) {
        this.iView=view;
    }


    @Override
    public void onComplete() {
        iView.hideLoding();
    }

    @Override
    public void onError(Throwable throwable) {
        RetrofitException.ResponseThrowable responseThrowable = RetrofitException.getResponseThrowable(throwable);
        Log.e("TAG", "e.code=" + responseThrowable.getCode() + responseThrowable.getMessage());
        iView.showErrorView(responseThrowable.getMessage());
    }

    @Override
    public void onBusinessError(int code, String msg) {
        iView.showNoNet();
    }
}
