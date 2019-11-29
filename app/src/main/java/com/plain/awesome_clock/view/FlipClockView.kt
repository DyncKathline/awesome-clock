package com.plain.awesome_clock.view

import android.content.Context
import android.os.Looper
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.plain.awesome_clock.MainActivity

import com.plain.awesome_clock.R
import com.xenione.digit.TabDigit
import kotlinx.android.synthetic.main.layout_flip_clock.view.*

import java.util.Calendar
import java.util.logging.Handler

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
    private lateinit var mTvPoint01: TextView
    private lateinit var mTvPoint02: TextView

    private var mPause = true
    private var elapsedTime: Long = 0

    private lateinit var runnable: Runnable

    private val mHandler: android.os.Handler by lazy {
        object : android.os.Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg?.what == MSG_TASK || mPause) {
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
        view.padding = resources.getDimensionPixelSize(R.dimen.clock_padding)
        view.textSize = resources.getDimensionPixelSize(R.dimen.clock_text_size)
        view.backgroundColor = ContextCompat.getColor(context, R.color.clock_bg)
        view.textColor = ContextCompat.getColor(context, R.color.clock_text)
        view.setDividerColor(ContextCompat.getColor(context, R.color.clock_divider))
        view.cornerSize = resources.getDimensionPixelSize(R.dimen.clock_corner_size)
        if (!isLow) {
            view.chars = chars
        }
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
        mHandler.removeCallbacksAndMessages(null)
        mPause = false
        val time = Calendar.getInstance()
        /* hours*/
        val hour = time.get(Calendar.HOUR_OF_DAY)
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
