package com.liub.baselibrary.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by liub on 2015/12/15.
 * 软键盘显示隐藏
 */
public class SoftInputUtils {

    public static void openSoftInput(Context context, final View focusView) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        focusView.post(new Runnable() {
            @Override
            public void run() {
                focusView.requestFocus();
                imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
            }
        });
    }

    public static void closeSoftInput(Context context, final View focusView) {
        if (focusView == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        focusView.clearFocus();
    }

    public static boolean isActive(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive(view);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
