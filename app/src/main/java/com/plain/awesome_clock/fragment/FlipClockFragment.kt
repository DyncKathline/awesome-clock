package com.plain.awesome_clock.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plain.awesome_clock.R
import com.plain.awesome_clock.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_flip_clock.*

/**
 * 翻页时钟 ⏰
 *
 * @author Plain
 * @date 2019-11-28 14:34
 */
class FlipClockFragment : BaseFragment() {

    companion object {

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
    }

    override fun onPause() {
        super.onPause()
        flipClockView.pause()
    }

}