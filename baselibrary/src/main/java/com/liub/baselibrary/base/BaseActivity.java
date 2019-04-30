package com.liub.baselibrary.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.liub.baselibrary.R;
import com.liub.baselibrary.listener.NoDoubleClickListener;
import com.liub.baselibrary.utils.ActivityStack;
import com.liub.baselibrary.utils.ScreenUtils;
import com.liub.baselibrary.utils.SoftInputUtils;
import com.liub.baselibrary.widget.AppToolbar;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;


/**
 * Create by liub on 2019/4/4
 * Describe:
 */
public abstract class BaseActivity<P extends BaseContract.IPresenter> extends RxAppCompatActivity implements BaseContract.IView {

    protected Context mContext;
    protected P mPresenter;
    protected Handler mHealder = new LeakHandler(this);
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
        showLoadingDialog("正在加载...");
    }

    @Override
    public void hideLoding() {
        hideLoadingDialog();
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
     * 设置返回键的默认动作（关闭页面）
     */
    public void setToolbarDefaultBackAction(AppToolbar toolbar) {
        toolbar.setOnBackAction(new NoDoubleClickListener() {
            @Override
            public void noDoubleClick(View v) {
                onBack();
            }
        });
    }


    /**
     * 设置返回键默认动作
     */
    public void setToolbarDefaultBackAction() {
        onBack();
    }

    private void onBack() {
        //获取焦点的view getCurrentFocus()
        if (this.getCurrentFocus() != null) {
            SoftInputUtils.closeSoftInput(getApplicationContext(), this.getCurrentFocus());
        }
        ActivityCompat.finishAfterTransition(this);
    }

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }


    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 创建LoadingDialog
     */
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 退出按钮
     *
     * @param context
     * @param splash
     */
    public void exitToSplash(Context context, Class<?> splash) {
        ActivityStack.getInstance().finishAllActivity();
        startActivity(new Intent(context, splash));
    }

    /**
     * 防内存泄漏的handler
     */
    private static class LeakHandler extends Handler {
        WeakReference<BaseActivity> reference;

        LeakHandler(BaseActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (reference == null) return;
            BaseActivity activity = reference.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    protected void handleMessage(Message msg) {

    }
}
