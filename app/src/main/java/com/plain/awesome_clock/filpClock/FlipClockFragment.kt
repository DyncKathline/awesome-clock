package com.plain.awesome_clock.filpClock

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plain.awesome_clock.R
import com.plain.awesome_clock.base.BaseFragment
import com.plain.awesome_clock.constant.Constant
import com.plain.awesome_clock.utils.SettingCacheHelper
import kotlinx.android.synthetic.main.fragment_flip_clock.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView =
            inflater.inflate(R.layout.fragment_flip_clock, container, false)
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
        updateSetting()
    }

    /**
     * 更新设置
     */
    private fun updateSetting() {
        var clockTextColor = SettingCacheHelper.getClockTextColor()
        if (clockTextColor == Constant.SETTING_EMPTY) {
            clockTextColor =
                ContextCompat.getColor(context!!, R.color.clock_text)
        }
        var clockBgColor = SettingCacheHelper.getClockBgColor()
        if (clockBgColor == Constant.SETTING_EMPTY) {
            clockBgColor =
                ContextCompat.getColor(context!!, R.color.clock_bg)
        }
        flipClockView.updateColor(clockTextColor, clockBgColor)
        flipClockView.setFlipClockIsShowSecond(SettingCacheHelper.getClockIsShowSecond())
    }

    override fun onPause() {
        super.onPause()
        flipClockView.pause()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        when (event) {
            Constant.CLOCK_RESUME -> {
                flipClockView.resume()
            }
            Constant.CLOCK_PAUSE -> {
                flipClockView.pause()
            }
        }
    };

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}