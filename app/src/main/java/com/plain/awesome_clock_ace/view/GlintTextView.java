package com.plain.awesome_clock_ace.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 闪烁的文本
 *
 * @author Plain
 * @date 2019/12/2 11:01 上午
 */
class GlintTextView extends AppCompatTextView {

    private final String TAG = "GlintTextView";
    private final int CODE = 0x11;

    boolean isGlint = false;

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (!isGlint) {
                stopHandler();
                return;
            }
            setVisibility(View.INVISIBLE);
            mHandler.sendEmptyMessageDelayed(CODE, 500);
        }
    };

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CODE && isGlint) {
                setVisibility(View.VISIBLE);
                postDelayed(runnable, 500);
            }
        }
    };

    public GlintTextView(Context context) {
        super(context);
        init();
    }

    public GlintTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GlintTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        stopHandler();
        startHandler(isGlint);
    }

    public void setGlint(boolean isGlint) {
        this.isGlint = isGlint;
        stopHandler();
        startHandler(isGlint);
    }

    /**
     * 开始闪烁
     */
    private void startHandler(boolean isGlint){
        if (isGlint) {
            mHandler.sendEmptyMessage(CODE);
        }
    }

    /**
     * 停止闪烁
     */
    private void stopHandler(){
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 恢复（外部调用）
     */
    public void resume(){
        stopHandler();
        startHandler(isGlint);
    }

    /**
     * 暂停（外部调用）
     */
    public void pause() {
        stopHandler();
    }

}
