package com.plain.awesome_clock.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

import com.plain.awesome_clock.R
import com.plain.awesome_clock.utils.SettingCacheHelper
import com.xenione.digit.TabDigit
import kotlinx.android.synthetic.main.layout_flip_clock.view.*

import java.util.Calendar

/**
 * FlipClockView
 *
 * @author Plain
 * @date 2019-11-28 12:14
 */
class FlipClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var mCharHighSecond: TabDigit
    private lateinit var mCharLowSecond: TabDigit
    private lateinit var mCharHighMinute: TabDigit
    private lateinit var mCharLowMinute: TabDigit
    private lateinit var mCharHighHour: TabDigit
    private lateinit var mCharLowHour: TabDigit
    private lateinit var mFlPoint01: FrameLayout
    private lateinit var mFlPoint02: FrameLayout
    private lateinit var mTvPoint01: GlintTextView
    private lateinit var mTvPoint02: GlintTextView

    private var mPause = true
    private var elapsedTime: Long = 0
    private var isShowSecond = true

    private lateinit var runnable: Runnable

    private val mHandler: Handler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg?.what == MSG_TASK && !mPause) {
                    post(runnable)
                }
            }
        }
    }

    init {
        init()
    }

    private fun init() {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.layout_flip_clock, this)
        initRunnable()
    }

    private fun initRunnable() {
        runnable = Runnable {
            Log.d(TAG, "run")
            elapsedTime += 1
            mCharLowSecond.start()
            if (elapsedTime % 10 == 0L) {
                mCharHighSecond.start()
            }
            if (elapsedTime % 60 == 0L) {
                mCharLowMinute.start()
            }
            if (elapsedTime % 600 == 0L) {
                mCharHighMinute.start()
            }
            if (elapsedTime % 3600 == 0L) {
                mCharLowHour.start()
            }
            if (elapsedTime % 36000 == 0L) {
                mCharHighHour.start()
            }
            mHandler.sendEmptyMessageDelayed(MSG_TASK, 1000)
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mCharHighSecond = findViewById(R.id.charHighSecond)
        mCharLowSecond = findViewById(R.id.charLowSecond)
        mCharHighMinute = findViewById(R.id.charHighMinute)
        mCharLowMinute = findViewById(R.id.charLowMinute)
        mCharHighHour = findViewById(R.id.charHighHour)
        mCharLowHour = findViewById(R.id.charLowHour)
        mFlPoint01 = findViewById(R.id.flPoint01)
        mFlPoint02 = findViewById(R.id.flPoint02)
        mTvPoint01 = findViewById(R.id.tvPoint01)
        mTvPoint02 = findViewById(R.id.tvPoint02)

        initFlipView()
    }

    /**
     * 初始化翻页时钟样式
     */
    private fun initFlipView() {
        setFlipView(mCharHighSecond, SEXAGISIMAL)
        setFlipView(mCharLowSecond)
        setFlipView(mCharHighMinute, SEXAGISIMAL)
        setFlipView(mCharLowMinute)
        setFlipView(mCharHighHour, HOURS)
        setFlipView(mCharLowHour)
    }

    private fun setFlipView(view: TabDigit?) {
        setFlipView(view!!, null, true)
    }

    private fun setFlipView(view: TabDigit, chars: CharArray?, isLow: Boolean = false) {
        setFlipViewSize(view)
        setFlipViewColor(view)
        if (!isLow) {
            view.chars = chars
        }
    }

    /**
     * 设置分割线颜色
     */
    private fun setFlipViewColor(view: TabDigit) {
        view.setDividerColor(ContextCompat.getColor(context, R.color.clock_divider))
    }

    /**
     * 设置翻页时钟的样式
     */
    private fun setFlipViewSize(view: TabDigit) {
        view.padding = if (isShowSecond) {
            resources.getDimensionPixelSize(R.dimen.clock_padding)
        } else {
            resources.getDimensionPixelSize(R.dimen.clock_padding_big)
        }
        view.textSize = if (isShowSecond) {
            resources.getDimensionPixelSize(R.dimen.clock_text_size)
        } else {
            resources.getDimensionPixelSize(R.dimen.clock_text_size_big)
        }
        view.cornerSize = if (isShowSecond) {
            resources.getDimensionPixelSize(R.dimen.clock_corner_size)
        } else {
            resources.getDimensionPixelSize(R.dimen.clock_corner_size_big)
        }
    }

    /**
     * 设置指针是否闪烁
     */
    fun setFlipClockIsGlint(isGlint:Boolean){
        mTvPoint01.isGlint = isGlint
        mTvPoint02.isGlint = isGlint
    }

    /**
     * 设置时钟是否显示秒
     */
    fun setFlipClockIsShowSecond(isShowSecond: Boolean) {
        this.isShowSecond = isShowSecond
        if (isShowSecond) {
            mCharHighSecond.visibility = View.VISIBLE
            mCharLowSecond.visibility = View.VISIBLE
            mFlPoint02.visibility = View.VISIBLE
        } else {
            mCharHighSecond.visibility = View.GONE
            mCharLowSecond.visibility = View.GONE
            mFlPoint02.visibility = View.GONE
        }
        setFlipViewSize(mCharHighSecond)
        setFlipViewSize(mCharLowSecond)
        setFlipViewSize(mCharHighMinute)
        setFlipViewSize(mCharLowMinute)
        setFlipViewSize(mCharHighHour)
        setFlipViewSize(mCharLowHour)
    }

    /**
     * 更新颜色值
     */
    fun updateColor(textColor: Int, bgColor: Int) {
        setTextAndBgColor(textColor, bgColor, mCharHighSecond)
        setTextAndBgColor(textColor, bgColor, mCharLowSecond)
        setTextAndBgColor(textColor, bgColor, mCharHighMinute)
        setTextAndBgColor(textColor, bgColor, mCharLowMinute)
        setTextAndBgColor(textColor, bgColor, mCharHighHour)
        setTextAndBgColor(textColor, bgColor, mCharLowHour)
        flPoint01.setBackgroundColor(bgColor)
        flPoint02.setBackgroundColor(bgColor)
        tvPoint01.setTextColor(textColor)
        tvPoint02.setTextColor(textColor)
    }

    private fun setTextColor(textColor: Int, view: TabDigit) {
        view.textColor = textColor
    }

    private fun setBgColo(bgColor: Int, view: TabDigit) {
        view.backgroundColor = bgColor
    }

    private fun setTextAndBgColor(textColor: Int, bgColor: Int, view: TabDigit) {
        setTextColor(textColor, view)
        setBgColo(bgColor, view)
    }

    fun pause() {
        Log.d(TAG, "pause")
        tvPoint01.pause()
        tvPoint02.pause()
        mHandler.removeCallbacksAndMessages(null)
        mPause = true
        mCharHighSecond.sync()
        mCharLowSecond.sync()
        mCharHighMinute.sync()
        mCharLowMinute.sync()
        mCharHighHour.sync()
        mCharLowHour.sync()
    }

    fun resume() {
        Log.d(TAG, "resume")
        tvPoint01.resume()
        tvPoint02.resume()
        mHandler.removeCallbacksAndMessages(null)
        mPause = false
        val time = Calendar.getInstance()
        /* hours*/
        val hour = time.get(SettingCacheHelper.getClockHourType())
        val highHour = hour / 10
        mCharHighHour.setChar(highHour)

        val lowHour = hour - highHour * 10
        mCharLowHour.setChar(lowHour)

        /* minutes*/
        val minutes = time.get(Calendar.MINUTE)
        val highMinute = minutes / 10
        mCharHighMinute.setChar(highMinute)

        val lowMinute = minutes - highMinute * 10
        mCharLowMinute.setChar(lowMinute)

        /* seconds*/
        val seconds = time.get(Calendar.SECOND)
        val highSecond = seconds / 10
        mCharHighSecond.setChar(highSecond)

        val lowSecond = seconds - highSecond * 10
        mCharLowSecond.setChar(lowSecond)

        elapsedTime = (lowSecond + highSecond * 10
                + lowMinute * 60 + highMinute * 600
                + lowHour * 3600 + highHour * 36000).toLong()

        mHandler.sendEmptyMessageDelayed(MSG_TASK, 1000)
    }

    companion object {

        private const val TAG = "FlipClock"

        private const val MSG_TASK = 10

        private val HOURS = charArrayOf('0', '1', '2')
        private val SEXAGISIMAL = charArrayOf('0', '1', '2', '3', '4', '5')
    }
}
