package com.plain.awesome_clock.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * 闪烁的文本
 *
 * @author Plain
 * @date 2019/12/2 11:01 上午
 */
class GlintTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var isGlint = false
        set(value) {
            field = value
            stopHandler()
            startHandler(value)
        }

    private val runnable by lazy {
        Runnable {
            if (!isGlint) {
                stopHandler()
                return@Runnable
            }
            visibility = View.INVISIBLE
            mHandler.sendEmptyMessageDelayed(CODE, 500)
        }
    }

    private val mHandler: Handler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg?.what == CODE && isGlint) {
                    visibility = View.VISIBLE
                    postDelayed(runnable, 500)
                }
            }
        }
    }

    init {
        init()
    }

    private fun init() {
        stopHandler()
        startHandler(isGlint)
    }

    /**
     * 开始闪烁
     */
    private fun startHandler(isGlint: Boolean){
        if (isGlint) {
            mHandler.sendEmptyMessage(CODE)
        }
    }

    /**
     * 停止闪烁
     */
    private fun stopHandler(){
        mHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 恢复（外部调用）
     */
    fun resume(){
        stopHandler()
        startHandler(isGlint)
    }

    /**
     * 暂停（外部调用）
     */
    fun pause() {
        stopHandler()
    }

    companion object {
        private const val TAG = "GlintTextView"
        private const val CODE = 0x11
    }

}
