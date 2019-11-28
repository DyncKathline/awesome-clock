package com.plain.awesome_clock.view

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

import com.plain.awesome_clock.R
import com.xenione.digit.TabDigit

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
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes), Runnable {

    private lateinit var mCharHighSecond: TabDigit
    private lateinit var mCharLowSecond: TabDigit
    private lateinit var mCharHighMinute: TabDigit
    private lateinit var mCharLowMinute: TabDigit
    private lateinit var mCharHighHour: TabDigit
    private lateinit var mCharLowHour: TabDigit

    private val mClock = this
    private var mPause = true
    private var elapsedTime: Long = 0

    init {
        init()
    }

    private fun init() {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.layout_flip_clock, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mCharHighSecond = findViewById(R.id.charHighSecond)
        mCharLowSecond = findViewById(R.id.charLowSecond)
        mCharHighMinute = findViewById(R.id.charHighMinute)
        mCharLowMinute = findViewById(R.id.charLowMinute)
        mCharHighHour = findViewById(R.id.charHighHour)
        mCharLowHour = findViewById(R.id.charLowHour)

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
        view.backgroundColor = resources.getColor(R.color.clock_bg)
        view.textColor = resources.getColor(R.color.clock_text)
        view.setDividerColor(resources.getColor(R.color.clock_divider))
        view.cornerSize = resources.getDimensionPixelSize(R.dimen.clock_corner_size)
        if (!isLow) {
            view.chars = chars
        }
    }

    fun pause() {
        mPause = true
        mCharHighSecond.sync()
        mCharLowSecond.sync()
        mCharHighMinute.sync()
        mCharLowMinute.sync()
        mCharHighHour.sync()
        mCharLowHour.sync()
    }

    fun resume() {
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

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    override fun run() {
        if (mPause) {
            return
        }
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
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000)
    }

    companion object {
        private val HOURS = charArrayOf('0', '1', '2')

        private val SEXAGISIMAL = charArrayOf('0', '1', '2', '3', '4', '5')
    }
}
