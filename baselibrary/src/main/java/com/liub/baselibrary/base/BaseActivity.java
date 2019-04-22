package com.liub.baselibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.liub.baselibrary.R;
import com.liub.baselibrary.utils.ActivityStack;
import com.liub.baselibrary.utils.ScreenUtils;


/**
 * Create by liub on 2019/4/4
 * Describe:
 */
public abstract class BaseActivity<P extends BaseContract.IPresenter> extends RxAppCompatActivity implements BaseContract.IView {

    protected Context mContext;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(setLayoutId());
        ScreenUtils.init(this);
        ActivityStack.getInstance().addActivity(this);
        mContext = this;
        mPresenter = initPresenter();
        attachView();
        bindView(savedInstanceState);
        //左近左出
        overridePendingTransition(R.anim.enter_trans, R.anim.exit_scale);
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private boolean isFirstFlag = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstFlag && hasFocus) {
            initData();
            isFirstFlag = false;
        }
    }

    private void initData() {
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    protected abstract int setLayoutId();

    protected abstract P initPresenter();

    protected abstract void bindView(Bundle savedInstanceState);

    @Override
    public LifecycleTransformer<Object> bindToLife(LifecycleTransformer lifecycleTransformer) {
        return this.bindToLifecycle();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_scale, R.anim.exit_trans);
//        overridePendingTransition(R.anim.enter_trans, R.anim.exit_scale);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestory();
        }
        ActivityStack.getInstance().removeActivity(this);
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

    @Override
    public void onRetry() {
        showToast("没有网络！！！！");
    }

    @Override
    public void showNoNet() {

    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 退出按钮
     * @param context
     * @param splash
     */
    public void exitToSplash(Context context, Class<?> splash){
        ActivityStack.getInstance().finishAllActivity();
        startActivity(new Intent(context,splash));
    }
}
