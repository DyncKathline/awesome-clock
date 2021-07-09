package com.plain.awesome_clock_ace.utils;

import android.os.SystemClock;

/**
 *
 //第一步：初始化
 private ClickUtil clickUtil = new ClickUtil().setClickCount(2).setClickIntervalTime(2000).setCallBack(new ClickUtil.CallBack() {
@Override
public void onTick(int count) {
//过程中第几次点击
}

@Override
public void onFinish() {
//点击达到设置的目标数在有效时间内
}
});
 //第二步：在点击事件回调中调用
 clickUtil.nClick();
 */
public class ClickUtil {

    //点击2次
    private int CLICK_NUM = 2;
    //点击时间间隔5秒
    private int CLICK_INTERVAL_TIME = 5000;
    //上一次的点击时间
    private long lastClickTime = 0;
    //记录点击次数
    private int clickNum = 0;

    public ClickUtil setClickCount(int count) {
        CLICK_NUM = count;
        return this;
    }

    public ClickUtil setClickIntervalTime(int millisTime) {
        CLICK_INTERVAL_TIME = millisTime;
        return this;
    }

    public interface CallBack {
        void onTick(int count);
        void onFinish();
    }

    private CallBack mCallBack;

    public ClickUtil setCallBack(CallBack callBack) {
        mCallBack = callBack;
        return this;
    }

    /**
     * 点击n次
     */
    public void nClick() {
        //点击的间隔时间不能超过5秒
        long currentClickTime = SystemClock.uptimeMillis();
        if (currentClickTime - lastClickTime <= CLICK_INTERVAL_TIME || lastClickTime == 0) {
            lastClickTime = currentClickTime;
            clickNum = clickNum + 1;
            if(mCallBack != null) {
                mCallBack.onTick(clickNum);
            }
        } else {
            //超过5秒的间隔
            //重新计数 从1开始
            clickNum = 1;
            lastClickTime = 0;
            return;
        }
        if (clickNum == CLICK_NUM) {
            if(mCallBack != null) {
                mCallBack.onFinish();
            }
            //重新计数
            clickNum = 0;
            lastClickTime = 0;
            /*实现点击多次后的事件*/
        }
    }

}