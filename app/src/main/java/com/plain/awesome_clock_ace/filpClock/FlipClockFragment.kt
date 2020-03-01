package com.plain.awesome_clock_ace.filpClock

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.plain.awesome_clock_ace.R
import com.plain.awesome_clock_ace.base.BaseFragment
import com.plain.awesome_clock_ace.constant.Constant
import com.plain.awesome_clock_ace.setting.SettingActivity
import com.plain.awesome_clock_ace.utils.MultiClickHelper
import com.plain.awesome_clock_ace.utils.SettingCacheHelper
import com.plain.awesome_clock_ace.utils.ToastUtils
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

    private val textSizeListener: SeekBar.OnSeekBarChangeListener by lazy {
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    flipClockView.customTextSize(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        }
    }

    private val paddingSizeListener: SeekBar.OnSeekBarChangeListener by lazy {
        object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    flipClockView.customPadding(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

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

    private fun goSettingPage() {
        startActivity(Intent(this@FlipClockFragment.context, SettingActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        size_bar.setOnSeekBarChangeListener(textSizeListener)
        padding_size_bar.setOnSeekBarChangeListener(paddingSizeListener)
        btnSave.setOnClickListener {
            saveCustomSizeSetting()
            hideCustomToolbar()
        }
        mainView.setOnLongClickListener {
            showCustomToolbar()
            return@setOnLongClickListener true
        }
        mainView.setOnTouchListener(MultiClickHelper(object : MultiClickHelper.MultiClickListener {
            override fun onClickProcess(index: Int) {
                //ToastUtils.showSuccessToast(this@FlipClockFragment.context!!, "点击了${index}次")
            }

            override fun onDoSomething() {
                goSettingPage()
            }
        }, 2))
        flipClockView.resume()
        updateSetting()
    }

    private fun saveCustomSizeSetting() {
        val size = size_bar.progress
        val padding = padding_size_bar.progress
        SettingCacheHelper.setClockViewSize(size)
        SettingCacheHelper.setClockViewPadding(padding)
        ToastUtils.showSuccessToast(this.context!!, "保存成功");
    }

    private fun showCustomToolbar() {
        customSizeToolbar.visibility = View.VISIBLE
    }

    private fun hideCustomToolbar() {
        customSizeToolbar.visibility = View.GONE
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
        flipClockView.setFlipClockIsGlint(SettingCacheHelper.getClockIsGlint())
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