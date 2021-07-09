package com.plain.awesome_clock_ace.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.plain.awesome_clock_ace.R;
import com.plain.awesome_clock_ace.view.digit.TabDigit;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

public class FlipClockView extends LinearLayout {

    private final String TAG = "FlipClock";

    private char[] HOURS = new char[]{'0', '1', '2'};
    private char[] SEXAGISIMAL = new char[]{'0', '1', '2', '3', '4', '5'};

    private TabDigit mCharHighSecond;
    private TabDigit mCharLowSecond;
    private TabDigit mCharHighMinute;
    private TabDigit mCharLowMinute;
    private TabDigit mCharHighHour;
    private TabDigit mCharLowHour;
    private GlintTextView mTvPoint01;
    private GlintTextView mTvPoint02;

    private boolean mPause = true;
    private long elapsedTime = 0;
    private boolean isShowSecond = true;

    private Runnable runnable;

    @IntDef({ClockHourType.hour12, ClockHourType.hour24})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ClockHourType {
        int hour12 = 12;
        int hour24 = 24;
    }

    private @ClockHourType int clockHourType;
    private int clockViewSize;
    private int clockViewPadding;

    private Handler mHandler = getHandler();

    @NotNull
    public Handler getHandler() {
        return new Handler(Looper.getMainLooper());
    }

    public FlipClockView(Context context) {
        super(context);
        init();
    }

    public FlipClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlipClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_flip_clock, this);
        initView();
        initRunnable();
    }

    private void initView() {
        mCharHighSecond = findViewById(R.id.charHighSecond);
        mCharLowSecond = findViewById(R.id.charLowSecond);
        mCharHighMinute = findViewById(R.id.charHighMinute);
        mCharLowMinute = findViewById(R.id.charLowMinute);
        mCharHighHour = findViewById(R.id.charHighHour);
        mCharLowHour = findViewById(R.id.charLowHour);
        mTvPoint01 = findViewById(R.id.tvPoint01);
        mTvPoint02 = findViewById(R.id.tvPoint02);
    }

    private void initRunnable() {
        runnable = new Runnable() {

            @Override
            public void run() {
                if(mPause) {
                   return;
                }
                Log.d(TAG, "Running -> " + elapsedTime);
                if (iElapsedTimeListener != null) {
                    iElapsedTimeListener.onChange(elapsedTime);
                }
                elapsedTime += 1;
                mCharLowSecond.start();
                if (elapsedTime % 10 == 0L) {
                    mCharHighSecond.start();
                }
                if (elapsedTime % 60 == 0L) {
                    mCharLowMinute.start();
                }
                if (elapsedTime % 600 == 0L) {
                    mCharHighMinute.start();
                }
                if (elapsedTime % 3600 == 0L) {
                    mCharLowHour.start();
                }
                if (elapsedTime % 36000 == 0L) {
                    mCharHighHour.start();
                }
                mHandler.postDelayed(this, 1000);
            }
        };

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initView();
        initFlipView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        if(runnable != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /**
     * 初始化翻页时钟样式
     */
    private void initFlipView() {
        setFlipView(mCharHighSecond, SEXAGISIMAL);
        setFlipView(mCharLowSecond);
        setFlipView(mCharHighMinute, SEXAGISIMAL);
        setFlipView(mCharLowMinute);
        setFlipView(mCharHighHour, HOURS);
        setFlipView(mCharLowHour);
    }

    private void setFlipView(TabDigit view) {
        setFlipView(view, null, true);
    }

    public void setFlipView(TabDigit view, char[] chars) {
        setFlipView(view, chars, false);
    }

    private void setFlipView(TabDigit view, char[] chars, boolean isLow) {
        setFlipViewSize(view);
        setFlipViewColor(view);
        if (!isLow) {
            view.setChars(chars);
        }
    }

    public int getClockHourType() {
        return clockHourType;
    }

    public void setClockHourType(@ClockHourType int clockHourType) {
        this.clockHourType = clockHourType;
    }

    /**
     * 设置分割线颜色
     */
    private void setFlipViewColor(TabDigit view) {
        view.setDividerColor(ContextCompat.getColor(getContext(), R.color.clock_divider));
    }

    /**
     * 设置翻页时钟的样式
     */
    private void setFlipViewSize(TabDigit view) {
        if (clockViewSize != 0) {
            view.setTextSize(clockViewSize);
        } else {
            if (isShowSecond) {
                view.setTextSize(dip2px(getContext(), 120));
            } else {
                view.setTextSize(dip2px(getContext(), 150));
            }
        }

        if (clockViewPadding != 0) {
            view.setPadding(clockViewPadding, clockViewPadding);
        } else {
            if (isShowSecond) {
                view.setPadding(dip2px(getContext(), 15), dip2px(getContext(), 15));
            } else {
                view.setPadding(dip2px(getContext(), 15), dip2px(getContext(), 15));
            }
        }

        if (isShowSecond) {
            view.setCornerSize(dip2px(getContext(), 4));
        } else {
            view.setCornerSize(dip2px(getContext(), 4));
        }
    }

    /**
     * 设置指针是否闪烁
     */
    public void setFlipClockIsGlint(boolean isGlint) {
        mTvPoint01.setGlint(isGlint);
        mTvPoint02.setGlint(isGlint);
    }

    /**
     * 设置时钟是否显示秒
     */
    public void setFlipClockIsShowSecond(boolean isShowSecond) {
        this.isShowSecond = isShowSecond;
        if (isShowSecond) {
            mCharHighSecond.setVisibility(View.VISIBLE);
            mCharLowSecond.setVisibility(View.VISIBLE);
            mTvPoint01.setVisibility(View.VISIBLE);
        } else {
            mCharHighSecond.setVisibility(View.GONE);
            mCharLowSecond.setVisibility(View.GONE);
            mTvPoint02.setVisibility(View.GONE);
        }
        setFlipViewSize(mCharHighSecond);
        setFlipViewSize(mCharLowSecond);
        setFlipViewSize(mCharHighMinute);
        setFlipViewSize(mCharLowMinute);
        setFlipViewSize(mCharHighHour);
        setFlipViewSize(mCharLowHour);
    }

    /**
     * 更新颜色值
     */
    public void updateColor(int textColor, int bgColor) {
        setTextAndBgColor(textColor, bgColor, mCharHighSecond);
        setTextAndBgColor(textColor, bgColor, mCharLowSecond);
        setTextAndBgColor(textColor, bgColor, mCharHighMinute);
        setTextAndBgColor(textColor, bgColor, mCharLowMinute);
        setTextAndBgColor(textColor, bgColor, mCharHighHour);
        setTextAndBgColor(textColor, bgColor, mCharLowHour);
        mTvPoint01.setBackgroundColor(bgColor);
        mTvPoint02.setBackgroundColor(bgColor);
        mTvPoint01.setTextColor(textColor);
        mTvPoint02.setTextColor(textColor);
    }

    private void setTextColor(int textColor, TabDigit view) {
        view.setTextColor(textColor);
    }

    private void setBgColo(int bgColor, TabDigit view) {
        view.setBackgroundColor(bgColor);
    }

    private void setTextAndBgColor(int textColor, int bgColor, TabDigit view) {
        setTextColor(textColor, view);
        setBgColo(bgColor, view);
    }

    public void customTextSize(int size) {
        clockViewSize = size;
        mCharHighSecond.setTextSize(size);
        mCharLowSecond.setTextSize(size);
        mCharHighMinute.setTextSize(size);
        mCharLowMinute.setTextSize(size);
        mCharHighHour.setTextSize(size);
        mCharLowHour.setTextSize(size);
        mTvPoint01.setTextSize(size / 2);
        mTvPoint02.setTextSize(size / 2);
    }

    public void customPadding(int padding) {
        mCharHighSecond.setPadding(padding, padding*2);
        mCharLowSecond.setPadding(padding, padding*2);
        mCharHighMinute.setPadding(padding, padding*2);
        mCharLowMinute.setPadding(padding, padding*2);
        mCharHighHour.setPadding(padding, padding*2);
        mCharLowHour.setPadding(padding, padding*2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public void pause() {
        Log.d(TAG, "pause");
        mTvPoint01.pause();
        mTvPoint02.pause();
        mHandler.removeCallbacksAndMessages(null);
        mPause = true;
        mCharHighSecond.reset();
        mCharLowSecond.reset();
        mCharHighMinute.reset();
        mCharLowMinute.reset();
        mCharHighHour.reset();
        mCharLowHour.reset();
    }

    public void resume() {
        Log.d(TAG, "resume");
        mTvPoint01.resume();
        mTvPoint02.resume();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        mHandler = getHandler();
        mPause = false;
        mCharHighSecond.reset();
        mCharLowSecond.reset();
        mCharHighMinute.reset();
        mCharLowMinute.reset();
        mCharHighHour.reset();
        mCharLowHour.reset();
        Calendar time = Calendar.getInstance();
        /* hours*/
        // 2020.02.29 fix 12小时制中午12点显示0的问题
        int hour = getHour(time);
        int highHour = hour / 10;
        mCharHighHour.setChar(highHour);

        int lowHour = hour - highHour * 10;
        mCharLowHour.setChar(lowHour);

        /* minutes*/
        int minutes = time.get(Calendar.MINUTE);
        int highMinute = minutes / 10;
        mCharHighMinute.setChar(highMinute);

        int lowMinute = minutes - highMinute * 10;
        mCharLowMinute.setChar(lowMinute);

        /* seconds*/
        int seconds = time.get(Calendar.SECOND);
        int highSecond = seconds / 10;
        mCharHighSecond.setChar(highSecond);

        int lowSecond = seconds - highSecond * 10;
        mCharLowSecond.setChar(lowSecond);

        elapsedTime = lowSecond + highSecond * 10
                + lowMinute * 60 + highMinute * 600
                + lowHour * 3600 + highHour * 36000;

        mHandler.postDelayed(runnable, 1000);
    }

    public void updateTime(int hour, int minutes, int seconds) {
        mTvPoint01.resume();
        mTvPoint02.resume();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        mHandler = getHandler();
        mPause = false;
        mCharHighSecond.reset();
        mCharLowSecond.reset();
        mCharHighMinute.reset();
        mCharLowMinute.reset();
        mCharHighHour.reset();
        mCharLowHour.reset();
        /* hours*/
        // 2020.02.29 fix 12小时制中午12点显示0的问题
        int highHour = hour / 10;
        mCharHighHour.setChar(highHour);

        int lowHour = hour - highHour * 10;
        mCharLowHour.setChar(lowHour);

        /* minutes*/
        int highMinute = minutes / 10;
        mCharHighMinute.setChar(highMinute);

        int lowMinute = minutes - highMinute * 10;
        mCharLowMinute.setChar(lowMinute);

        /* seconds*/
        int highSecond = seconds / 10;
        mCharHighSecond.setChar(highSecond);

        int lowSecond = seconds - highSecond * 10;
        mCharLowSecond.setChar(lowSecond);

        elapsedTime = lowSecond + highSecond * 10
                + lowMinute * 60 + highMinute * 600
                + lowHour * 3600 + highHour * 36000;

        mHandler.postDelayed(runnable, 1000);
    }

    /**
     * 获取时间（处理一下12小时制时，凌晨12点和中午12点都显示0的问题）
     * 详细见[Calendar.HOUR]
     */
    private int getHour(Calendar time) {
        int hour = time.get(clockHourType);
        if (clockHourType == Calendar.HOUR) {
            int amOrPm = time.get(Calendar.AM_PM);
            if (amOrPm == Calendar.PM && hour == 0) {
                hour = 12;
            }
        }
        return hour;
    }

    public interface IElapsedTimeListener {
        void onChange(long time);
    }

    IElapsedTimeListener iElapsedTimeListener = null;

    public void setListener(IElapsedTimeListener listener) {
        iElapsedTimeListener = listener;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
