package com.liub.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Create by liub on 2019/4/4
 * Describe:
 */
public abstract class BaseFragment<P extends BaseContract.IPresenter> extends RxFragment implements BaseContract.IView {
    protected BaseActivity mContext;
    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mContext = (BaseActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        attachView();
    }

    private boolean initSuccess = false;

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && mPresenter != null && !initSuccess) {
            mPresenter.onStart();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!initSuccess && mPresenter != null && getUserVisibleHint()) {
            initSuccess = true;
            mPresenter.onStart();
        } else {
            initSuccess = false;
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view, savedInstanceState);
    }

    protected abstract P initPresenter();

    protected abstract int setLayoutId();

    protected abstract void bindView(View view, Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.onDestory();
    }

    @Override
    public void showLoding() {

    }

    @Override
    public void hideLoding() {

    }

    @Override
    public void showErrorView(String msg) {
        showToast(msg);
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public LifecycleTransformer<Object> bindToLife(LifecycleTransformer lifecycleTransformer) {
        return this.bindToLifecycle();
    }
}
