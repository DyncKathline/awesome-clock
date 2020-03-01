package com.plain.awesome_clock_ace.setting

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.plain.awesome_clock_ace.base.BaseActivity
import com.plain.awesome_clock_ace.constant.Constant
import com.plain.awesome_clock_ace.utils.SettingCacheHelper
import com.plain.awesome_clock_ace.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_setting.*
import kotlin.properties.Delegates
import java.util.*
import android.text.TextUtils
import android.widget.Spinner
import com.plain.awesome_clock_ace.R
import com.plain.awesome_clock_ace.R.*
import com.plain.awesome_clock_ace.about.AboutActivity

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
        setContentView(layout.activity_setting)
        setInit()
    }

    override fun initView() {
        super.initView()
        //Spinner相对垂直方向的偏移量（pixel)
        spClockHour.dropDownVerticalOffset = resources.getDimensionPixelSize(R.dimen.size50)
        initSetting()
    }

    override fun setListener() {
        super.setListener()

        back.setOnClickListener {
            finish()
        }

        about.setOnClickListener {
            startActivity(Intent(this@SettingActivity, AboutActivity::class.java))
        }

        swShowSecond.setOnCheckedChangeListener { _, isChecked ->
            SettingCacheHelper.setClockIsShowSecond(
                isChecked
            )
        }

        swGlint.setOnCheckedChangeListener { _, isChecked ->
            SettingCacheHelper.setClockIsGlint(
                isChecked
            )
        }

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

        spClockHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        SettingCacheHelper.setClockHourType(Calendar.HOUR)
                    }
                    1 -> {
                        SettingCacheHelper.setClockHourType(Calendar.HOUR_OF_DAY)
                    }
                }

            }

        }

    }

    private fun initSetting() {
        //初始化时钟文字颜色
        val clockTextColor = SettingCacheHelper.getClockTextColor();
        if (clockTextColor != Constant.SETTING_EMPTY) {
            this.clockTextColor = clockTextColor
            ivClockTextColor.setBackgroundColor(this.clockTextColor)
        } else {
            this.clockTextColor =
                ContextCompat.getColor(this, color.clock_bg)
            ivClockTextColor.setBackgroundColor(this.clockTextColor)
        }
        //初始化文字背景颜色
        val clockBgColor = SettingCacheHelper.getClockBgColor();
        if (clockBgColor != Constant.SETTING_EMPTY) {
            this.clockBgColor = clockBgColor
            ivClockBgColor.setBackgroundColor(this.clockBgColor)
        } else {
            this.clockBgColor =
                ContextCompat.getColor(this, color.clock_bg)
            ivClockBgColor.setBackgroundColor(this.clockBgColor)
        }
        val clockHourType = SettingCacheHelper.getClockHourType()
        val languages = resources.getStringArray(array.hour_type)
        setSpinnerDefaultValue(
            spClockHour, when (clockHourType) {
                Calendar.HOUR -> {
                    spClockHour.prompt = languages[0]
                    languages[0]
                }
                Calendar.HOUR_OF_DAY -> {
                    spClockHour.prompt = languages[1]
                    languages[1]
                }
                else -> {
                    spClockHour.prompt = languages[1]
                    languages[1]
                }
            }
        )
        //初始化是否显示秒
        swShowSecond.isChecked = SettingCacheHelper.getClockIsShowSecond()
        //初始化指针是否闪烁
        swGlint.isChecked = SettingCacheHelper.getClockIsGlint()
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
                val default = ContextCompat.getColor(
                    this@SettingActivity,
                    color.clock_text
                )
                view.setBackgroundColor(default)
                SettingCacheHelper.setClockTextColor(default)
            }
            Constant.CLOCK_BG_COLOR -> {
                val default = ContextCompat.getColor(
                    this@SettingActivity,
                    color.clock_bg
                )
                view.setBackgroundColor(default)
                SettingCacheHelper.setClockBgColor(default)
            }
        }
        ToastUtils.showSuccessToast(this, "保存成功")
    }

    /**
     * 设置Spinner默认值
     */
    private fun setSpinnerDefaultValue(spinner: Spinner, value: String) {
        val apsAdapter = spinner.adapter
        val size = apsAdapter.count
        for (i in 0 until size) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true)
                break
            }
        }
    }

}