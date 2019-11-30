package com.plain.awesome_clock.about

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.plain.awesome_clock.BuildConfig
import com.plain.awesome_clock.R
import me.drakeet.multitype.Items
import me.drakeet.support.about.AbsAboutActivity
import me.drakeet.support.about.Category
import me.drakeet.support.about.Contributor
import me.drakeet.support.about.License

/**
 * 关于界面
 *
 * @author Plain
 * @date 2019-11-30 11:46
 */
class AboutActivity : AbsAboutActivity() {

    override fun onItemsCreated(items: Items) {
        items.add(Category("Developers"))
        items.add(
            Contributor(
                R.drawable.avantor,
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

    @SuppressLint("SetTextI18n")
    override fun onCreateHeader(icon: ImageView, slogan: TextView, version: TextView) {
        icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground))
        slogan.setText(R.string.about_slogan)
        version.text = "v${BuildConfig.VERSION_NAME}"
    }

}