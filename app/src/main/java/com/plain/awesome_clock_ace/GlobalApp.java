package com.plain.awesome_clock_ace;

import android.app.Application;
import com.plain.awesome_clock_ace.utils.ACache;

/**
 * 全局 Application
 *
 * @author Plain
 * @date 2019-11-28 15:50
 */
public class GlobalApp extends Application {

    public static ACache aCache;

    @Override
    public void onCreate() {
        super.onCreate();
        initACache();
    }

    private void initACache() {
        aCache = ACache.get(this);
    }

}