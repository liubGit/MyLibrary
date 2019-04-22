package com.liub.baselibrary.listener

import android.view.View
import android.widget.AdapterView

/**
 * 防止多次点击
 * Created by liub on 2018-09-18.
 */
abstract class NoDoubleItemClickListener : AdapterView.OnItemClickListener {
    private var lastClickTime: Long = 0

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < 500)
            return
        lastClickTime = time
        noDoubleClick(parent, view, position, id)
    }

    abstract fun noDoubleClick(parent: AdapterView<*>, view: View, position: Int, id: Long)
}
