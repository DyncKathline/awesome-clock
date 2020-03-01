package com.plain.awesome_clock_ace.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Base Fragment
 *
 * @author Plain
 * @date 2019-11-28 14:34
 */
open class BaseFragment : androidx.fragment.app.Fragment() {

    protected lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setInit()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setInit() {
        initData()
        initView()
        setListener()
    }

    protected open fun initData() {

    }

    protected open fun initView() {

    }

    protected open fun setListener() {

    }


}