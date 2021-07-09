package com.plain.awesome_clock_ace.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 闪烁的文本
 *
 * @author Plain
 * @date 2019/12/2 11:01 上午
 */
public class GlintTextView extends AppCompatTextView {

    private final String TAG = "GlintTextView";
    private final int CODE = 0x11;

    boolean isGlint = false;
    boolean isHidden = false;

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (!isGlint) {
                stopHandler();
                return;
            }
//            setVisibility(View.INVISIBLE);
            isHidden = true;
            invalidate();
            mHandler.sendEmptyMessageDelayed(CODE, 500);
        }
    };

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CODE && isGlint) {
//                setVisibility(View.VISIBLE);
                isHidden = false;
                invalidate();
                postDelayed(runnable, 500);
            }
        }
    };
    private Paint mPaint;

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

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setTextSize(getTextSize());
        mPaint.setColor(getCurrentTextColor());
        String text = getText().toString();
        int startX = (int) (getWidth() / 2 - mPaint.measureText(text) / 2);
        //解决高度绘制不居中
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
        int startY = getHeight() / 2 - fm.bottom + (fm.descent - fm.ascent - fm.leading) / 2;
//        Log.i("kath---", fm.toString() + ", height: " + getHeight() + ", x: " + startX + ", y: " + startY);

        if(isHidden) {
            canvas.drawText("", startX, startY, mPaint);
        }else {
            canvas.drawText(text, startX, startY, mPaint);
        }
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(getTextSize());
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
