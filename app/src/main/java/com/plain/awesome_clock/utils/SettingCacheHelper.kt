package com.plain.awesome_clock.utils

import android.text.TextUtils
import com.plain.awesome_clock.GlobalApp
import com.plain.awesome_clock.constant.Constant
import org.w3c.dom.Text
import java.util.*

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

    /**
     * 保存时钟小时制，包括[Calendar.HOUR]12小时制和[Calendar.HOUR_OF_DAY]24小时制
     */
    fun setClockHourType(field: Int) {
        aCache.put(Constant.CLOCK_HOUR_TYPE, field.toString())
    }

    /**
     * 获取时钟的小时制，默认[Calendar.HOUR]12小时制
     */
    fun getClockHourType(): Int {
        val field = aCache.getAsString(Constant.CLOCK_HOUR_TYPE)
        return if (!TextUtils.isEmpty(field)) {
            field.toInt()
        } else {
            Calendar.HOUR
        }
    }

    /**
     * 保存时钟是否显示秒
     */
    fun setClockIsShowSecond(isShowSecond: Boolean) {
        aCache.put(
            Constant.CLOCK_IS_SHOW_SECOND, if (isShowSecond) {
                "0"
            } else {
                "1"
            }
        )
    }

    /**
     * 获取时钟是否显示秒
     */
    fun getClockIsShowSecond(): Boolean {
        val isShowSecond = aCache.getAsString(Constant.CLOCK_IS_SHOW_SECOND)
        return TextUtils.isEmpty(isShowSecond) || isShowSecond == "0"
    }

    /**
     * 保存时钟指针是否闪烁
     */
    fun setClockIsGlint(isGlint: Boolean) {
        aCache.put(
            Constant.CLOCK_IS_GLINT, if (isGlint) {
                "0"
            } else {
                "1"
            }
        )
    }

    /**
     * 获取时钟指针是否闪烁
     */
    fun getClockIsGlint(): Boolean {
        val isGlint = aCache.getAsString(Constant.CLOCK_IS_GLINT)
        return TextUtils.isEmpty(isGlint) || isGlint == "0"
    }

}