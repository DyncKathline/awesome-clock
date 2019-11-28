package com.plain.awesome_clock

import android.app.Application
import android.content.Context
import com.plain.awesome_clock.utils.ACache

/**
 * 全局 Application
 *
 * @author Plain
 * @date 2019-11-28 15:50
 */
class GlobalApp : Application() {

    companion object {
        lateinit var aCache: ACache
    }

    override fun onCreate() {
        super.onCreate()
        initACache()
    }

    private fun initACache() {
        aCache = ACache.get(this)
    }

}