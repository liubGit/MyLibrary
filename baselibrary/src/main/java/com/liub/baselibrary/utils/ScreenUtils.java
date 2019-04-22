package com.liub.baselibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 分辨率工具
 *
 * @author liub
 */
public class ScreenUtils {
    private static ScreenUtils dpiUtil;
    private static DisplayMetrics dm;

    private ScreenUtils(Context context) {
        if (context == null)
            throw new NullPointerException();
        dm = context.getResources().getDisplayMetrics();
    }

    /**
     * ScreenUtils
     * 第一次启动需要调用的方法
     *
     * @return
     */
    public static void init(Context context) {
        if (dpiUtil == null) {
            dpiUtil = new ScreenUtils(context);
        }
    }

    /**
     * dpi转换成像素
     *
     * @param dpi
     * @return px
     */
    public static float dpi2Px(float dpi) {
        return dpi * dm.density + 0.5f;
    }

    /**
     * 像素转换成dpi
     *
     * @param px
     * @return dpi
     */
    public static int px2Dpi(Context context, int px) {
        if (dm == null)
            dm = context.getResources().getDisplayMetrics();
        return (int) (px / dm.density + 0.5f);
    }

    /**
     * pt转换成px
     */
    public static int pt2Px(Context context, int pt) {
        if (dm == null)
            dm = context.getResources().getDisplayMetrics();
        return TypedValue.complexToDimensionPixelSize(pt, dm);
    }

    /**
     * pt转换成dpi
     */
    public static int pt2Dpi(Context context, int pt) {
        if (dm == null)
            dm = context.getResources().getDisplayMetrics();
        return px2Dpi(context, TypedValue.complexToDimensionPixelSize(pt, dm));
    }

    /**
     * pt转换成sp
     */
    public static int pt2Sp(Context context, int pt) {
        if (dm == null)
            dm = context.getResources().getDisplayMetrics();
        return px2Sp(context, TypedValue.complexToDimensionPixelSize(pt, dm));
    }

    /**
     * 像素转换成sp
     */
    public static int px2Sp(Context context, float px) {
        if (dm == null)
            dm = context.getResources().getDisplayMetrics();
        return (int) (px / dm.scaledDensity + 0.5f);
    }

    /**
     * 获取当前屏幕宽度，单位是PX
     *
     * @return 屏幕宽度，单位是PX
     */
    public static int getScreenWidth() {
        if (dm == null) return 0;
        return dm.widthPixels;
    }

    /**
     * 获取当前屏幕高度，单位是PX
     *
     * @return 屏幕高度，单位是PX
     */
    public static int getScreenHeight() {
        if (dm == null) return 0;
        return dm.heightPixels;
    }
}
