package com.plain.awesome_clock.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

/**
 * BaseActivity
 *
 * @author Plain
 * @date 2019-11-28 13:49
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setInit() {
        initBar()
        initData()
        initView()
        setListener()
    }

    private fun initBar() {
        //隐藏导航栏和状态栏
        ImmersionBar.with(this)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .init()
        //屏幕常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    protected open fun initData() {

    }

    protected open fun initView() {

    }

    protected open fun setListener() {

    }


}