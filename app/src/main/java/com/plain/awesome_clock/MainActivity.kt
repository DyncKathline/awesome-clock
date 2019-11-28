package com.plain.awesome_clock

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import com.plain.awesome_clock.base.BaseActivity
import com.plain.awesome_clock.biliibli.BilibiliInfoFragment
import com.plain.awesome_clock.filpClock.FlipClockFragment
import com.plain.awesome_clock.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setInit()
    }

    override fun initData() {
        super.initData()
        fragmentList = ArrayList()
        fragmentList.add(FlipClockFragment.newInstance())
        fragmentList.add(BilibiliInfoFragment.newInstance())
    }

    override fun initView() {
        super.initView()
        initViewPager()
    }

    override fun setListener() {
        super.setListener()

        ivSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    private fun initViewPager() {
        viewPager.removeAllViews()
        val pagerAdapter = MyPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.currentItem = 0
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        initViewPager()
    }

    private class MyPagerAdapter(
        fragmentManager: FragmentManager,
        var fragmentList: List<Fragment>
    ) :
        FragmentPagerAdapter(fragmentManager) {

        override fun getItem(p0: Int): Fragment {
            return fragmentList[p0]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

    }

}
