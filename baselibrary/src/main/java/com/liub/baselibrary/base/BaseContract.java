package com.liub.baselibrary.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Create by liub on 2019/4/4
 * Describe:
 */
public interface BaseContract {
    interface IView {
        void showLoding();

        void hideLoding();

        void showErrorView(String msg);

        /**
         * 无网络页面
         */
        void showNoNet();

        void onRetry();

        LifecycleTransformer<Object> bindToLife(LifecycleTransformer lifecycleTransformer);
    }

    interface IPresenter {
        void onStart();

        void attachView(IView view);

        void detachView();

        void onDestory();

    }

    interface BaseModel {

    }
}
