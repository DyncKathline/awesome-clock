package com.plain.awesome_clock.filpClock

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plain.awesome_clock.R
import com.plain.awesome_clock.base.BaseFragment
import com.plain.awesome_clock.constant.Constant
import com.plain.awesome_clock.utils.SettingCacheHelper
import kotlinx.android.synthetic.main.fragment_flip_clock.*

/**
 * 翻页时钟 ⏰
 *
 * @author Plain
 * @date 2019-11-28 14:34
 */
class FlipClockFragment : BaseFragment() {

    companion object {

        private const val TAG: String = "FlipClock"

        fun newInstance(): FlipClockFragment {
            return FlipClockFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_flip_clock, container, false)
        return rootView
    }

    override fun initData() {
        super.initData()

    }

    override fun initView() {
        super.initView()

    }

    override fun onResume() {
        super.onResume()
        flipClockView.resume()
        updateColor()
    }

    private fun updateColor() {
        var clockTextColor = SettingCacheHelper.getClockTextColor()
        if (clockTextColor == Constant.SETTING_EMPTY) {
            clockTextColor = ContextCompat.getColor(context!!, R.color.clock_text)
        }
        var clockBgColor = SettingCacheHelper.getClockBgColor()
        if (clockBgColor == Constant.SETTING_EMPTY) {
            clockBgColor = ContextCompat.getColor(context!!, R.color.clock_bg)
        }
        flipClockView.updateColor(clockTextColor, clockBgColor)
    }

    override fun onPause() {
        super.onPause()
        flipClockView.pause()
    }

}