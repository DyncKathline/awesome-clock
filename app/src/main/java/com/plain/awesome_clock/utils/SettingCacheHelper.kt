package com.plain.awesome_clock.utils

import android.text.TextUtils
import com.plain.awesome_clock.GlobalApp
import com.plain.awesome_clock.constant.Constant

/**
 * 设置项缓存帮助类
 *
 * @author Plain
 * @date 2019-11-28 16:05
 */
object SettingCacheHelper {

    private val aCache = GlobalApp.aCache

    /**
     * 保存时钟文字颜色
     */
    fun setClockTextColor(color: Int) {
        aCache.put(Constant.CLOCK_TEXT_COLOR, color.toString())
    }

    /**
     * 获取时钟文字颜色
     */
    fun getClockTextColor(): Int {
        val textColor = aCache.getAsString(Constant.CLOCK_TEXT_COLOR)
        return if (!TextUtils.isEmpty(textColor)) {
            textColor.toInt()
        } else {
            Constant.SETTING_EMPTY
        }
    }

    /**
     * 保存时钟背景颜色
     */
    fun setClockBgColor(color: Int) {
        aCache.put(Constant.CLOCK_BG_COLOR, color.toString())
    }

    /**
     * 获取时钟背景颜色
     */
    fun getClockBgColor(): Int {
        val bgColor = aCache.getAsString(Constant.CLOCK_BG_COLOR)
        return if (!TextUtils.isEmpty(bgColor)) {
            bgColor.toInt()
        } else {
            Constant.SETTING_EMPTY
        }
    }

}