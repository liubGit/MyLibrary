package com.liub.baselibrary.listener

import android.view.View

/**
Created by liub on 2018-09-18.
防止过快重复点击
 */
abstract class NoDoubleClickListener : View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于500毫秒
    private var lastClickTime: Long = 0

    @Synchronized
    override fun onClick(v: View?) {
        val curClickTime = System.currentTimeMillis()
        if (curClickTime - lastClickTime < 500)
            return
        // 超过点击间隔后再将lastClickTime重置为当前点击时间
        lastClickTime = curClickTime
        noDoubleClick(v)
    }

    abstract fun noDoubleClick(v: View?)
}