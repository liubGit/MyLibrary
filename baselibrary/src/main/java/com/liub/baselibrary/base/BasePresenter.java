package com.liub.baselibrary.base;


import com.liub.baselibrary.rx.RxManager;

/**
 * Create by liub on 2019/4/4
 * Describe:
 */
public abstract class BasePresenter<V extends BaseContract.IView> implements BaseContract.IPresenter {
    public V mView;
    public RxManager rxManager;
    @Override
    public void attachView(BaseContract.IView view) {
        mView = (V) view;
        rxManager=new RxManager();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onDestory() {
        rxManager.clear();
        rxManager=null;
    }
}
