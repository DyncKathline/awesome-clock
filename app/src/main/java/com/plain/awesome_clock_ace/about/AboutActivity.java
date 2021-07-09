package com.plain.awesome_clock_ace.about;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.widget.ImageView;
import android.widget.TextView;

import com.drakeet.about.AbsAboutActivity;
import com.drakeet.about.Category;
import com.drakeet.about.Contributor;
import com.drakeet.about.License;
import com.plain.awesome_clock_ace.BuildConfig;
import com.plain.awesome_clock_ace.R;

import java.util.List;

/**
 * 关于界面
 *
 * @author Plain
 * @date 2019-11-30 11:46
 */
public class AboutActivity extends AbsAboutActivity {

    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground));
        slogan.setText(R.string.about_slogan);
        version.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull List<Object> items) {
        items.add(new Category("Developers"));
        items.add(
                new Contributor(
                        R.mipmap.avantor,
                        "Plain",
                        "Developer",
                        "https://github.com/plain-dev"
                )
        );

        items.add(new Category("Open Source Licenses"));
        items.add(
                new License(
                        "tab-digit",
                        "xenione",
                        License.APACHE_2,
                        "https://github.com/xenione/tab-digit"
                )
        );
        items.add(
                new License(
                        "colorpicker",
                        "QuadFlask",
                        License.APACHE_2,
                        "https://github.com/QuadFlask/colorpicker"
                )
        );
        items.add(
                new License(
                        "ImmersionBar",
                        "gyf-dev",
                        License.APACHE_2,
                        "https://github.com/gyf-dev/ImmersionBar"
                )
        );
        items.add(
                new License(
                        "Toasty",
                        "GrenderG",
                        License.GPL_V3,
                        "https://github.com/GrenderG/Toasty"
                )
        );
        items.add(
                new License(
                        "EventBus",
                        "greenrobot",
                        License.APACHE_2,
                        "https://github.com/greenrobot/EventBus"
                )
        );
        items.add(
                new License(
                        "about-page",
                        "drakeet",
                        License.APACHE_2,
                        "https://github.com/PureWriter/about-page"
                )
        );
    }

}