package com.plain.awesome_clock

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager

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

    protected fun setSystemUIVisible(show: Boolean) {
        if (show) {
            var uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            uiFlags = uiFlags or 0x00001000
            window.decorView.systemUiVisibility = uiFlags
        } else {
            var uiFlags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            uiFlags = uiFlags or 0x00001000
            window.decorView.systemUiVisibility = uiFlags
        }

        //保持屏幕常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected fun setInit(){
        initData()
        initView()
        setListener()
    }

    protected open fun initData(){

    }

    protected open fun initView(){

    }

    protected open fun setListener(){

    }





}