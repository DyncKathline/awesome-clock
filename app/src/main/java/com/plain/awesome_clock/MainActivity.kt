package com.plain.awesome_clock

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.plain.awesome_clock.fragment.BilibiliInfoFragment
import com.plain.awesome_clock.fragment.FlipClockFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var fragmentList: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSystemUIVisible(false)
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

    private fun initViewPager() {
        val pagerAdapter = MyPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.currentItem = 0
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {

            }
        })
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
