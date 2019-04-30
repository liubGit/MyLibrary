package com.liub.baselibrary.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liub.baselibrary.R;

/**
 * Create by liub on 2019/4/26
 * Describe: 自定义弹框
 */
public class AlertDialog extends DialogFragment implements View.OnClickListener {

    private View contentView, mDivider;
    private TextView mTitle, mContent, mCancel, mAccept;
    private EditText mEdit;
    private FrameLayout mContentLayout;
    private boolean onlyAccept;
    private String mCancelStr, mAcceptStr;
    private boolean onEdit;

    private View.OnClickListener mCancelListener;
    private View.OnClickListener mAcceptListener;
    private OnEditPasswordListener onEditPasswordListener;

    public interface OnEditPasswordListener {

        void onEdit(String string, View v);
    }

    public static AlertDialog newInstance(String content) {
        return newInstance(null, content, false);
    }

    public static AlertDialog newInstance(String title, String content) {
        return newInstance(title, content, false);
    }

    public static AlertDialog newInstance(String content, boolean onlyAccept) {
        return newInstance(null, content, onlyAccept);
    }

    public static AlertDialog newInstance(String title, String msg, boolean onlyAccept) {
        AlertDialog dialog = new AlertDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", msg);
        dialog.setArguments(bundle);
        dialog.setOnlyAccept(onlyAccept);
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * 自定义view
     */
    public static AlertDialog newInstance(View contentView) {
        AlertDialog dialog = new AlertDialog();
        dialog.setContentView(contentView);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.getAttributes().width = getContext().getResources().getDisplayMetrics().widthPixels - 25 * 2;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View inflat = inflater.inflate(R.layout.lib_ui_dialog_custom, container, false);
        initView(inflat);
        return inflat;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String title = null;
        String content = null;
        if (getArguments() != null) {
            if (getArguments().containsKey("title")) {
                title = getArguments().getString("title");
            }
            if (getArguments().containsKey("content")) {
                content = getArguments().getString("content");
            }
            if (getArguments().containsKey("onlyAccept")) {
                onlyAccept = getArguments().getBoolean("onlyAccept", false);
            }
        }

        //设置标题
        if (TextUtils.isEmpty(title)) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }
        //设置内容
        if (TextUtils.isEmpty(content)) {
            mContent.setVisibility(View.GONE);
        } else {
            mContent.setVisibility(View.VISIBLE);
            mContent.setText(content);
        }
        if (!TextUtils.isEmpty(mCancelStr)) {
            mCancel.setText(mCancelStr);
        }
        if (!TextUtils.isEmpty(mAcceptStr)) {
            mAccept.setText(mAcceptStr);
        }
        mEdit.setVisibility(onEdit ? View.VISIBLE : View.GONE);

        //只有一个确定按钮
        setOnlyAccept(onlyAccept);


        mCancel.setOnClickListener(this);
        mAccept.setOnClickListener(this);

        // 展示内容替换
        replaceContentView();
    }

    private void initView(View rootView) {
        mTitle = rootView.findViewById(R.id.tv_title);
        mContent = rootView.findViewById(R.id.tv_content);
        mEdit = rootView.findViewById(R.id.edit_password);
        mCancel = rootView.findViewById(R.id.btn_cancel);
        mAccept = rootView.findViewById(R.id.btn_accept);
        mDivider = rootView.findViewById(R.id.divider_button);
        mContentLayout = rootView.findViewById(R.id.fl_content);

    }

    public AlertDialog setOnlyAccept(boolean onlyAccept) {
        this.onlyAccept = onlyAccept;
        if (this.onlyAccept && mDivider != null && mCancel != null && mAccept != null) {
            mDivider.setVisibility(View.GONE);
            mCancel.setVisibility(View.GONE);
            mAccept.setBackgroundResource(R.drawable.lib_ui_bottom8corner_white_selector);
        }
        return this;
    }

    private void setContentView(View contentView) {
        this.contentView = contentView;
        replaceContentView();
    }


    private void replaceContentView() {
        if (contentView != null && mContentLayout != null) {
            mContentLayout.removeAllViews();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            mContentLayout.addView(contentView);
        }
    }

    public AlertDialog setEditPassword(boolean showEdit, OnEditPasswordListener onEditPasswordListener) {
        this.onEdit = showEdit;
        if (mEdit != null) {
            mEdit.setVisibility(View.VISIBLE);
        }
        this.onEditPasswordListener = onEditPasswordListener;
        return this;
    }

    public AlertDialog setCancelListener(View.OnClickListener listener) {
        this.mCancelListener = listener;
        return this;
    }

    public AlertDialog setCancelListener(String cancel, View.OnClickListener listener) {
        if (!TextUtils.isEmpty(cancel)) {
            mCancelStr = cancel;
        }
        this.mCancelListener = listener;
        return this;
    }

    public AlertDialog setAcceptListener(View.OnClickListener listener) {
        this.mAcceptListener = listener;
        return this;
    }

    public AlertDialog setAcceptListener(String accept, View.OnClickListener listener) {
        if (!TextUtils.isEmpty(accept)) {
            mAcceptStr = accept;
        }
        this.mAcceptListener = listener;
        return this;
    }

    public void show(AppCompatActivity activity, String tag) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_accept) {
            if (onEdit && onEditPasswordListener != null) {
                // 如果没填
                if (TextUtils.isEmpty(mEdit.getText().toString())) {
                    Toast.makeText(getContext(), "请先输入服务器地址再确定", Toast.LENGTH_SHORT).show();
                    return;
                }
                onEditPasswordListener.onEdit(mEdit.getText().toString(), v);
            }

            dismiss();
            if (mAcceptListener != null) {
                mAcceptListener.onClick(v);
            }
        } else {
            dismiss();
            if (mCancelListener != null) {
                mCancelListener.onClick(v);
            }
        }
    }
}
