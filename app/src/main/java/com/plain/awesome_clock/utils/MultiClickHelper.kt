package com.plain.awesome_clock.utils

import android.annotation.SuppressLint
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*


/**
 *  name: MultiClickHelper
 *  desc: 多次点击监听
 *  date: 2020/2/29 6:01 PM
 *  version: v1.0
 *  author: Plain
 *  blog: https://plain-dev.com
 *  email: support@plain-dev.com
 */
class MultiClickHelper constructor(private val listener: MultiClickListener) :
    View.OnTouchListener {

    companion object {

        private const val toolTime = 1500

    }

    private var count = 2
    private var hints: LongArray;
    private var index = 0

    init {
        hints = LongArray(count)
    }

    constructor(listener: MultiClickListener, count: Int) : this(listener) {
        this.count = count
    }

    interface MultiClickListener {

        /**
         * 监听过程中当前点击时第几次
         */
        fun onClickProcess(index: Int)

        /**
         * 点击完成，做些什么
         */
        fun onDoSomething()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            multiClickTask()
        }
        return false
    }

    private fun multiClickTask() {
        index++
        System.arraycopy(hints, 1, hints, 0, hints.size - 1)
        hints[hints.size - 1] = SystemClock.uptimeMillis()
        Log.e("MultiClick", "cur arr: " + hints.contentToString() + " , " + index)
        listener.onClickProcess(index)
        if (hints[hints.size - 1] - hints[0] <= toolTime) {
            Log.e("MultiClick", "doSomething")
            listener.onDoSomething()
            restore()
        } else if (index == count) {
            restore()
        }
    }

    private fun restore() {
        index = 0
        hints = LongArray(count)
    }

}