package com.plain.awesome_clock_ace.about

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.drakeet.about.AbsAboutActivity
import com.drakeet.about.Category
import com.drakeet.about.Contributor
import com.drakeet.about.License
import com.plain.awesome_clock_ace.BuildConfig
import com.plain.awesome_clock_ace.R

/**
 * 关于界面
 *
 * @author Plain
 * @date 2019-11-30 11:46
 */
class AboutActivity : AbsAboutActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground))
        slogan.setText(R.string.about_slogan)
        version.text = BuildConfig.VERSION_NAME
    }

    override fun onItemsCreated(items: MutableList<Any>) {
        items.add(Category("Developers"))
        items.add(
            Contributor(
                R.mipmap.avantor,
                "Plain",
                "Developer",
                "https://github.com/plain-dev"
            )
        )

        items.add(Category("Open Source Licenses"))
        items.add(
            License(
                "tab-digit",
                "xenione",
                License.APACHE_2,
                "https://github.com/xenione/tab-digit"
            )
        )
        items.add(
            License(
                "colorpicker",
                "QuadFlask",
                License.APACHE_2,
                "https://github.com/QuadFlask/colorpicker"
            )
        )
        items.add(
            License(
                "ImmersionBar",
                "gyf-dev",
                License.APACHE_2,
                "https://github.com/gyf-dev/ImmersionBar"
            )
        )
        items.add(
            License(
                "Toasty",
                "GrenderG",
                License.GPL_V3,
                "https://github.com/GrenderG/Toasty"
            )
        )
        items.add(
            License(
                "EventBus",
                "greenrobot",
                License.APACHE_2,
                "https://github.com/greenrobot/EventBus"
            )
        )
        items.add(
            License(
                "about-page",
                "drakeet",
                License.APACHE_2,
                "https://github.com/PureWriter/about-page"
            )
        )
    }

}