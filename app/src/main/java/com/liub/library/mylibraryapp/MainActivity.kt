package com.liub.library.mylibraryapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.liub.baselibrary.base.BaseActivity
import com.liub.baselibrary.base.BaseContract
import com.liub.baselibrary.base.BasePresenter

class MainActivity : BaseActivity<BasePresenter<BaseContract.IView>>() {
    override fun setLayoutId(): Int = R.layout.activity_main

    override fun initPresenter(): BasePresenter<BaseContract.IView>? {
        return null
    }

    override fun bindView(savedInstanceState: Bundle?) {

    }

}