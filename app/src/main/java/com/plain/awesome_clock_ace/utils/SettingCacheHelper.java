package com.plain.awesome_clock_ace.utils;

import android.text.TextUtils;
import com.plain.awesome_clock_ace.GlobalApp;
import com.plain.awesome_clock_ace.constant.Constant;
import java.util.*;

/**
 * 设置项缓存帮助类
 *
 * @author Plain
 * @date 2019-11-28 16:05
 */
public class SettingCacheHelper {

    private static ACache aCache = GlobalApp.aCache;

    /**
     * 保存时钟文字颜色
     */
    public static void setClockTextColor(int color) {
        aCache.put(Constant.CLOCK_TEXT_COLOR, color + "");
    }

    /**
     * 获取时钟文字颜色
     */
    public static int getClockTextColor() {
        String textColor = aCache.getAsString(Constant.CLOCK_TEXT_COLOR);
       if (!TextUtils.isEmpty(textColor)) {
           return Integer.parseInt(textColor);
        } else {
            return Constant.SETTING_EMPTY;
        }
    }

    /**
     * 保存时钟背景颜色
     */
    public static void setClockBgColor(int color) {
        aCache.put(Constant.CLOCK_BG_COLOR, color + "");
    }

    /**
     * 获取时钟背景颜色
     */
    public static int getClockBgColor() {
        String bgColor = aCache.getAsString(Constant.CLOCK_BG_COLOR);
        if (!TextUtils.isEmpty(bgColor)) {
            return Integer.parseInt(bgColor);
        } else {
            return Constant.SETTING_EMPTY;
        }
    }

    /**
     * 保存时钟小时制，包括[Calendar.HOUR]12小时制和[Calendar.HOUR_OF_DAY]24小时制
     */
    public static void setClockHourType(int field) {
        aCache.put(Constant.CLOCK_HOUR_TYPE, field + "");
    }

    /**
     * 获取时钟的小时制，默认[Calendar.HOUR]12小时制
     */
    public static int getClockHourType() {
        String field = aCache.getAsString(Constant.CLOCK_HOUR_TYPE);
        if (!TextUtils.isEmpty(field)) {
            return Integer.parseInt(field);
        } else {
            return Calendar.HOUR;
        }
    }

    /**
     * 保存时钟是否显示秒
     */
    public static void setClockIsShowSecond(boolean isShowSecond) {
        aCache.put(
            Constant.CLOCK_IS_SHOW_SECOND, isShowSecond ? "0" : "1"
        );
    }

    /**
     * 获取时钟是否显示秒
     */
    public static boolean getClockIsShowSecond() {
        String isShowSecond = aCache.getAsString(Constant.CLOCK_IS_SHOW_SECOND);
        return TextUtils.isEmpty(isShowSecond) || isShowSecond.equals("0");
    }

    /**
     * 保存时钟指针是否闪烁
     */
    public static void setClockIsGlint(boolean isGlint) {
        aCache.put(
            Constant.CLOCK_IS_GLINT, isGlint ? "0" : "1"
        );
    }

    /**
     * 获取时钟指针是否闪烁
     */
    public static boolean getClockIsGlint() {
        String isGlint = aCache.getAsString(Constant.CLOCK_IS_GLINT);
        return TextUtils.isEmpty(isGlint) || isGlint.equals("0");
    }

    /**
     * 保存时钟大小
     */
    public static void setClockViewSize(int size) {
        boolean clockIsShowSecond = getClockIsShowSecond();
        if (clockIsShowSecond) {
            aCache.put(Constant.CLOCK_SIZE_TYPE_1, size + "");
        } else {
            aCache.put(Constant.CLOCK_SIZE_TYPE_2, size + "");
        }
    }

    /**
     * 获取时钟大小
     */
    public static int getClockViewSize() {
        boolean clockIsShowSecond = getClockIsShowSecond();
        if (clockIsShowSecond) {
            String padding = aCache.getAsString(Constant.CLOCK_SIZE_TYPE_1);
            if (TextUtils.isEmpty(padding)) {
                return 0;
            } else {
                return Integer.parseInt(padding);
            }
        } else {
            String padding = aCache.getAsString(Constant.CLOCK_SIZE_TYPE_2);
            if (TextUtils.isEmpty(padding)) {
                return 0;
            } else {
                return Integer.parseInt(padding);
            }
        }
    }

    /**
     * 保存时钟内间距
     */
    public static void setClockViewPadding(int size) {
        boolean clockIsShowSecond = getClockIsShowSecond();
        if (clockIsShowSecond) {
            aCache.put(Constant.CLOCK_PADDING_TYPE_1, size + "");
        } else {
            aCache.put(Constant.CLOCK_PADDING_TYPE_2, size + "");
        }
    }

    /**
     * 获取时钟内间距
     */
    public static int getClockViewPadding() {
        boolean clockIsShowSecond = getClockIsShowSecond();
        if (clockIsShowSecond) {
            String padding = aCache.getAsString(Constant.CLOCK_PADDING_TYPE_1);
            if (TextUtils.isEmpty(padding)) {
                return 0;
            } else {
                return Integer.parseInt(padding);
            }
        } else {
            String padding = aCache.getAsString(Constant.CLOCK_PADDING_TYPE_2);
            if (TextUtils.isEmpty(padding)) {
                return 0;
            } else {
                return Integer.parseInt(padding);
            }
        }
    }

}