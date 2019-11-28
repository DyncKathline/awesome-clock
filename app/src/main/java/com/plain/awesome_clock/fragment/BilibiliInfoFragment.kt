package com.plain.awesome_clock.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plain.awesome_clock.R
import com.plain.awesome_clock.base.BaseFragment

/**
 *
 *
 *
 * @author Plain
 * @date 2019-11-28 15:08
 */
class BilibiliInfoFragment : BaseFragment() {

    companion object {
        fun newInstance(): BilibiliInfoFragment {
            return BilibiliInfoFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_bilibili_info, container, false)
        return rootView
    }

    override fun initData() {
        super.initData()

    }

    override fun initView() {
        super.initView()

    }

}