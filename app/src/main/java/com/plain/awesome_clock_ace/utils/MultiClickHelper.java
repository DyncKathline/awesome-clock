package com.plain.awesome_clock_ace.utils;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;


/**
 *  name: MultiClickHelper
 *  desc: 多次点击监听
 *  date: 2020/2/29 6:01 PM
 *  version: v1.0
 *  author: Plain
 *  blog: https://plain-dev.com
 *  email: support@plain-dev.com
 */
public class MultiClickHelper implements View.OnTouchListener {

    private final int toolTime = 1500;
    private MultiClickListener listener;

    private int count = 2;
    private long[] hints;
    private int index = 0;

    public MultiClickHelper(MultiClickListener listener) {
        this.listener = listener;
        hints = new long[count];
    }

    public MultiClickHelper(MultiClickListener listener, int count) {
        this(listener);
        this.count = count;
    }

    public interface MultiClickListener {

        /**
         * 监听过程中当前点击时第几次
         */
        void onClickProcess(int index);

        /**
         * 点击完成，做些什么
         */
        void onDoSomething();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            multiClickTask();
        }
        return false;
    }

    private void multiClickTask() {
        index++;
        System.arraycopy(hints, 1, hints, 0, hints.length - 1);
        hints[hints.length - 1] = SystemClock.uptimeMillis();
        Log.e("MultiClick", "cur arr: " + hints.toString() + " , " + index);
        listener.onClickProcess(index);
        if (hints[hints.length - 1] - hints[0] <= toolTime) {
            Log.e("MultiClick", "doSomething");
            listener.onDoSomething();
            restore();
        } else if (index == count) {
            restore();
        }
    }

    private void restore() {
        index = 0;
        hints = new long[count];
    }

}