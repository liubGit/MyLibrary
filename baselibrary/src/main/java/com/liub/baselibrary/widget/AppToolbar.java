package com.liub.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liub.baselibrary.R;
import com.liub.baselibrary.listener.NoDoubleClickListener;

/**
 * Created by liub on 2018-09-18.
 * dec:自定义标题
 */

public class AppToolbar extends LinearLayout {

    private Context context;
    private ImageButton mBackButton, mMenuLeftButton, mMenuRightButton;
    private TextView mBackContent, mTitleText, mMenuRightText, mMenuLeftText;
    private LinearLayout mMenuTextLin, mMenuImgLin;

    private String mTitleStr;
    private CharSequence mMenuStr;

    private boolean isBackEnable;

    private View.OnClickListener onClickListener;
    private OnTextMenuClickListener onTextMenuClickListener;
    private OnMenuItemClickListener listener1;

    public interface OnMenuItemClickListener {
        /**
         * 点击事件
         *
         * @param first 是否第一个按钮的设置
         */
        void onClick(boolean first);
    }

    public AppToolbar(Context context) {
        super(context);
        init(context, null);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        LinearLayout.inflate(context, R.layout.layout_ui_actionbar, this);
        initView(this);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AppToolbar, 0, 0);
        CharSequence sequence = typedArray.getText(R.styleable.AppToolbar_tb_title);
        isBackEnable = typedArray.getBoolean(R.styleable.AppToolbar_tb_enable_back, true);
        CharSequence menuChar = typedArray.getText(R.styleable.AppToolbar_tb_menue_title);
        if (!TextUtils.isEmpty(sequence)) {
            mTitleStr = sequence.toString();
        }
        if (!TextUtils.isEmpty(menuChar)) {
            mMenuStr = menuChar.toString();
        }
        typedArray.recycle();
        setTitle(mTitleStr);
        setBackButtonEnable(isBackEnable);
        setTextMenu(mMenuStr);
    }

    private void initView(View appToolbar) {
        mBackButton = (ImageButton) appToolbar.findViewById(R.id.ui_toolbar_back);
        mBackContent = (TextView) appToolbar.findViewById(R.id.ui_toolbar_back_content);
        mTitleText = (TextView) appToolbar.findViewById(R.id.ui_toolbar_title);
        mMenuImgLin = (LinearLayout) appToolbar.findViewById(R.id.ui_toolbar_img_lin);
        mMenuLeftButton = (ImageButton) appToolbar.findViewById(R.id.ui_toolbar_img_left);
        mMenuRightButton = (ImageButton) appToolbar.findViewById(R.id.ui_toolbar_img_right);
        mMenuTextLin = (LinearLayout) appToolbar.findViewById(R.id.ui_toolbar_text_lin);
        mMenuLeftText = (TextView) appToolbar.findViewById(R.id.ui_toolbar_text_left);
        mMenuRightText = (TextView) appToolbar.findViewById(R.id.ui_toolbar_text_right);
    }

    private NoDoubleClickListener onclickListener = new NoDoubleClickListener() {
        @Override
        public void noDoubleClick(View view) {
            int i = view.getId();
            if (i == R.id.ui_toolbar_back) {
                onClickListener.onClick(view);
            } else if (i == R.id.ui_toolbar_text_left) {
                if (onTextMenuClickListener != null) {
                    onTextMenuClickListener.onClick(mMenuLeftText.getText().toString());
                }
            } else if (i==R.id.ui_toolbar_img_left){
                if (listener1 != null) {
                    listener1.onClick(true);
                }
            }
        }
    };

    public void setTitle(String mTitleStr) {
        if (mTitleStr == null) {
            mTitleStr = "";
        }
        mTitleText.setText(mTitleStr);

    }

    private void setBackButtonEnable(boolean enable) {
        if (enable) {
            isBackEnable = true;
            mBackButton.setVisibility(VISIBLE);
            mBackButton.setOnClickListener(onclickListener);
        } else {
            isBackEnable = false;
            mBackButton.setVisibility(GONE);
            mBackButton.setOnClickListener(onclickListener);
        }
    }

    public void setTextMenu(CharSequence mMenuString) {
        if (!TextUtils.isEmpty(mMenuString)) {
            mMenuStr = mMenuString;
            mMenuLeftText.setVisibility(VISIBLE);
            mMenuTextLin.setVisibility(VISIBLE);
            mMenuLeftText.setText(mMenuStr);
            mMenuLeftText.setOnClickListener(onclickListener);
        } else {
            mMenuTextLin.setVisibility(GONE);
            mMenuLeftText.setVisibility(GONE);
        }
    }


    public void setTextMenu(CharSequence text, OnTextMenuClickListener listener) {
        setTextMenu(text);
        this.setTextMenuAction(listener);
    }

    /**
     * 设置单个图标按钮
     */
    private boolean isDrawableMenuShow = false;

    public void setSingleMenu(Drawable mMenuDrawableFir, OnMenuItemClickListener listener) {
        this.listener1 = listener;
        if (mMenuDrawableFir != null) {
            isDrawableMenuShow = true;
            mMenuLeftButton.setVisibility(VISIBLE);
            mMenuImgLin.setVisibility(VISIBLE);

            mMenuLeftButton.setOnClickListener(onclickListener);
            mMenuLeftButton.setImageDrawable(mMenuDrawableFir);
            mMenuRightButton.setVisibility(GONE);

            // 隐藏
//            if (isTextMenuShow) {
//                setTextMenuLayoutVisible(false);
//                isTextMenuShow = false;
//            }
        } else {
            mMenuLeftButton.setVisibility(INVISIBLE);
            mMenuLeftButton.setOnClickListener(null);
        }
    }

    /**
     * 设置单个图标按钮
     */
    public void setSingleMenu(int iconRes, OnMenuItemClickListener listener) {
        if (iconRes != 0)
            setSingleMenu(context.getResources().getDrawable(iconRes), listener);
        else
            setSingleMenu(null, listener);
    }

    public void setTextMenuAction(OnTextMenuClickListener listener) {
        this.onTextMenuClickListener = listener;
    }

    /**
     * 设置回退动作
     */
    public void setOnBackAction(OnClickListener listener) {
        this.onClickListener = listener;
    }
    public interface OnTextMenuClickListener {
        void onClick(String var1);
    }
}
