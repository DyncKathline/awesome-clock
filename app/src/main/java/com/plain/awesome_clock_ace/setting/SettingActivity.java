package com.plain.awesome_clock_ace.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.plain.awesome_clock_ace.constant.Constant;
import com.plain.awesome_clock_ace.utils.SettingCacheHelper;
import com.plain.awesome_clock_ace.utils.ToastUtils;

import java.util.*;

import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

import com.plain.awesome_clock_ace.R;
import com.plain.awesome_clock_ace.R.*;
import com.plain.awesome_clock_ace.about.AboutActivity;

import org.jetbrains.annotations.Nullable;

/**
 * 设置页面
 *
 * @author Plain
 * @date 2019-11-28 15:49
 */
public class SettingActivity extends AppCompatActivity {

    private int clockTextColor;
    private int clockBgColor;
    private Spinner spClockHour;
    private ImageView back;
    private ImageView about;
    private Switch swShowSecond;
    private Switch swGlint;
    private RelativeLayout rlClockTextColorRely;
    private RelativeLayout rlClockBgRely;
    private ImageView ivClockTextColor;
    private ImageView ivClockBgColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_setting);
        initView();
        setListener();
    }

    protected void initView() {
        spClockHour = findViewById(id.spClockHour);
        back = findViewById(id.back);
        about = findViewById(id.about);
        swShowSecond = findViewById(id.swShowSecond);
        swGlint = findViewById(id.swGlint);
        rlClockTextColorRely = findViewById(id.rlClockTextColorRely);
        rlClockBgRely = findViewById(id.rlClockBgRely);
        ivClockTextColor = findViewById(id.ivClockTextColor);
        ivClockBgColor = findViewById(id.ivClockBgColor);
        //Spinner相对垂直方向的偏移量（pixel)
        spClockHour.setDropDownVerticalOffset(getResources().getDimensionPixelSize(R.dimen.size50));
        initSetting();
    }

    protected void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
            }
        });

        swShowSecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SettingCacheHelper.setClockIsShowSecond(isChecked);
            }
        });

        swGlint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SettingCacheHelper.setClockIsGlint(isChecked);
            }
        });

        rlClockTextColorRely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildColorPicker(
                        "选择时钟文字颜色",
                        Constant.CLOCK_TEXT_COLOR,
                        clockTextColor,
                        ivClockTextColor
                );
            }
        });

        rlClockBgRely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildColorPicker(
                        "选择时钟背景颜色",
                        Constant.CLOCK_BG_COLOR,
                        clockBgColor,
                        ivClockBgColor
                );
            }
        });

        spClockHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:{
                        SettingCacheHelper.setClockHourType(Calendar.HOUR);
                        break;
                    }
                    case 1:{
                        SettingCacheHelper.setClockHourType(Calendar.HOUR_OF_DAY);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSetting() {
        //初始化时钟文字颜色
        int clockTextColor = SettingCacheHelper.getClockTextColor();
        if (clockTextColor != Constant.SETTING_EMPTY) {
            this.clockTextColor = clockTextColor;
            ivClockTextColor.setBackgroundColor(this.clockTextColor);
        } else {
            this.clockTextColor =
                    ContextCompat.getColor(this, color.clock_bg);
            ivClockTextColor.setBackgroundColor(this.clockTextColor);
        }
        //初始化文字背景颜色
        int clockBgColor = SettingCacheHelper.getClockBgColor();
        if (clockBgColor != Constant.SETTING_EMPTY) {
            this.clockBgColor = clockBgColor;
            ivClockBgColor.setBackgroundColor(this.clockBgColor);
        } else {
            this.clockBgColor =
                    ContextCompat.getColor(this, color.clock_bg);
            ivClockBgColor.setBackgroundColor(this.clockBgColor);
        }
        int clockHourType = SettingCacheHelper.getClockHourType();
        String[] languages = getResources().getStringArray(array.hour_type);
        String language = languages[0];
        switch(clockHourType) {
            case Calendar.HOUR: {
                spClockHour.setPrompt(languages[0]);
                language = languages[0];
                break;
            }
            case Calendar.HOUR_OF_DAY:
            default:{
                spClockHour.setPrompt(languages[1]);
                language = languages[1];
                break;
            }
        }
        setSpinnerDefaultValue(
                spClockHour,
                language
        );
        //初始化是否显示秒
        swShowSecond.setChecked(SettingCacheHelper.getClockIsShowSecond());
        //初始化指针是否闪烁
        swGlint.setChecked(SettingCacheHelper.getClockIsGlint());
    }

    private void buildColorPicker(String title, final String type, int color, final ImageView view) {
        ColorPickerDialogBuilder.with(this)
                .setTitle(title)
                .initialColor(color)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton(
                        "确认"
                        , new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors) {
                                updateColor(view, type, lastSelectedColor);
                            }
                        }
                )
                .setNegativeButton("还原", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        revertColor(view, type);
                        dialogInterface.cancel();
                    }
                })
                .build()
                .show();
    }

    /**
     * 更新并保存设置的颜色值
     */
    private void updateColor(
            ImageView view,
            String type,
            int lastSelectedColor
    ) {
        view.setBackgroundColor(lastSelectedColor);
        switch (type) {
            case Constant.CLOCK_TEXT_COLOR: {
                SettingCacheHelper.setClockTextColor(lastSelectedColor);
                break;
            }
            case Constant.CLOCK_BG_COLOR: {
                SettingCacheHelper.setClockBgColor(lastSelectedColor);
                break;
            }
        }
        ToastUtils.showSuccessToast(this, "保存成功");
    }

    private void revertColor(
            ImageView view,
            String type
    ) {
        switch (type) {
            case Constant.CLOCK_TEXT_COLOR: {
                int color = ContextCompat.getColor(
                        SettingActivity.this,
                        R.color.clock_text
                );
                view.setBackgroundColor(color);
                SettingCacheHelper.setClockTextColor(color);
                break;
            }
            case Constant.CLOCK_BG_COLOR: {
                int color = ContextCompat.getColor(
                        SettingActivity.this,
                        R.color.clock_bg
                );
                view.setBackgroundColor(color);
                SettingCacheHelper.setClockBgColor(color);
            }
        }
        ToastUtils.showSuccessToast(this, "保存成功");
    }

    /**
     * 设置Spinner默认值
     */
    private void setSpinnerDefaultValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);
                break;
            }
        }
    }

}