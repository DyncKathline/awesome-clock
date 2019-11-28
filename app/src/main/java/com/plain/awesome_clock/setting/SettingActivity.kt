package com.plain.awesome_clock.setting

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.plain.awesome_clock.R
import com.plain.awesome_clock.base.BaseActivity
import com.plain.awesome_clock.constant.Constant
import com.plain.awesome_clock.utils.SettingCacheHelper
import com.plain.awesome_clock.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_setting.*
import kotlin.properties.Delegates

/**
 * 设置页面
 *
 * @author Plain
 * @date 2019-11-28 15:49
 */
class SettingActivity : BaseActivity() {

    private var clockTextColor by Delegates.notNull<Int>()
    private var clockBgColor by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setInit()
    }

    override fun initData() {
        super.initData()

    }

    override fun initView() {
        super.initView()
        initSetting()
    }

    override fun setListener() {
        super.setListener()

        rlClockTextColorRely.setOnClickListener {
            buildColorPicker(
                "选择时钟文字颜色",
                Constant.CLOCK_TEXT_COLOR,
                clockTextColor,
                ivClockTextColor
            )
        }

        rlClockBgRely.setOnClickListener {
            buildColorPicker(
                "选择时钟背景颜色",
                Constant.CLOCK_BG_COLOR,
                clockBgColor,
                ivClockBgColor
            )
        }
    }

    private fun initSetting() {
        //初始化时钟文字颜色
        val clockTextColor = SettingCacheHelper.getClockTextColor();
        if (clockTextColor != Constant.SETTING_EMPTY) {
            this.clockTextColor = clockTextColor
            ivClockTextColor.setBackgroundColor(this.clockTextColor)
        } else {
            this.clockTextColor = ContextCompat.getColor(this, R.color.clock_bg)
            ivClockTextColor.setBackgroundColor(this.clockTextColor)
        }
        //初始化文字背景颜色
        val clockBgColor = SettingCacheHelper.getClockBgColor();
        if (clockBgColor != Constant.SETTING_EMPTY) {
            this.clockBgColor = clockBgColor
            ivClockBgColor.setBackgroundColor(this.clockBgColor)
        } else {
            this.clockBgColor = ContextCompat.getColor(this, R.color.clock_bg)
            ivClockBgColor.setBackgroundColor(this.clockBgColor)
        }
    }

    private fun buildColorPicker(title: String, type: String, color: Int, view: ImageView) {
        ColorPickerDialogBuilder.with(this)
            .setTitle(title)
            .initialColor(color)
            .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
            .density(12)
            .setPositiveButton(
                "确认"
            ) { _, lastSelectedColor, _ -> updateColor(view, type, lastSelectedColor) }
            .setNegativeButton("还原") { dialog, _ ->
                revertColor(view, type)
                dialog!!.cancel()
            }
            .build()
            .show()
    }

    /**
     * 更新并保存设置的颜色值
     */
    private fun updateColor(
        view: ImageView,
        type: String,
        lastSelectedColor: Int
    ) {
        view.setBackgroundColor(lastSelectedColor)
        when (type) {
            Constant.CLOCK_TEXT_COLOR -> {
                SettingCacheHelper.setClockTextColor(lastSelectedColor)
            }
            Constant.CLOCK_BG_COLOR -> {
                SettingCacheHelper.setClockBgColor(lastSelectedColor)
            }
        }
        ToastUtils.showSuccessToast(this, "保存成功")
    }

    private fun revertColor(
        view: ImageView,
        type: String
    ) {
        when (type) {
            Constant.CLOCK_TEXT_COLOR -> {
                val default = ContextCompat.getColor(this@SettingActivity, R.color.clock_text)
                view.setBackgroundColor(default)
                SettingCacheHelper.setClockTextColor(default)
            }
            Constant.CLOCK_BG_COLOR -> {
                val default = ContextCompat.getColor(this@SettingActivity, R.color.clock_bg)
                view.setBackgroundColor(default)
                SettingCacheHelper.setClockBgColor(default)
            }
        }
        ToastUtils.showSuccessToast(this, "保存成功")
    }

}