package com.plain.awesome_clock_ace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.plain.awesome_clock_ace.constant.Constant;
import com.plain.awesome_clock_ace.setting.SettingActivity;
import com.plain.awesome_clock_ace.utils.ClickUtil;
import com.plain.awesome_clock_ace.utils.SettingCacheHelper;
import com.plain.awesome_clock_ace.utils.ToastUtils;
import com.plain.awesome_clock_ace.view.FlipClockView;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private FlipClockView mFlipclockview;
    private AppCompatSeekBar mSizeBar;
    private AppCompatSeekBar mPaddingSizeBar;
    private Button mBtnsave;
    private Button mBtnCancel;
    private CardView mCustomsizetoolbar;
    private FrameLayout mMainview;

    private SeekBar.OnSeekBarChangeListener textSizeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mFlipclockview.customTextSize(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener paddingSizeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mFlipclockview.customPadding(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    private long elapsedTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        mFlipclockview = findViewById(R.id.flipClockView);
        mSizeBar = findViewById(R.id.size_bar);
        mPaddingSizeBar = findViewById(R.id.padding_size_bar);
        mBtnsave = findViewById(R.id.btnSave);
        mBtnCancel = findViewById(R.id.btnCancel);
        mCustomsizetoolbar = findViewById(R.id.customSizeToolbar);
        mMainview = findViewById(R.id.mainView);

        elapsedTime = 45;
        mFlipclockview.setListener(new FlipClockView.IElapsedTimeListener() {
            @Override
            public void onChange(long time) {
                elapsedTime = time;

            }
        });

        mSizeBar.setOnSeekBarChangeListener(textSizeListener);
        mPaddingSizeBar.setOnSeekBarChangeListener(paddingSizeListener);
        mBtnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomSizeSetting();
                hideCustomToolbar();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideCustomToolbar();
            }
        });
        mMainview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showCustomToolbar();
                return true;
            }
        });
        final ClickUtil clickUtil = new ClickUtil().setClickCount(2);
        clickUtil.setCallBack(new ClickUtil.CallBack() {
            @Override
            public void onTick(int count) {

            }

            @Override
            public void onFinish() {
                goSettingPage();
            }
        });
        mMainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickUtil.nClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSetting();
        mFlipclockview.resume();
//        int hour = (int) (elapsedTime / 3600);
//        int minute = (int) (elapsedTime / 60);
//        int second = (int) (elapsedTime - hour * 3600 - minute * 60);
//        mFlipclockview.updateTime(hour, minute, second);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mFlipclockview.pause();
    }

    /**
     * 更新设置
     */
    private void updateSetting() {
        int clockTextColor = SettingCacheHelper.getClockTextColor();
        if (clockTextColor == Constant.SETTING_EMPTY) {
            clockTextColor = ContextCompat.getColor(this, R.color.clock_text);
        }
        int clockBgColor = SettingCacheHelper.getClockBgColor();
        if (clockBgColor == Constant.SETTING_EMPTY) {
            clockBgColor = ContextCompat.getColor(this, R.color.clock_bg);
        }
//        mFlipclockview.updateColor(clockTextColor, clockBgColor);
        mFlipclockview.setFlipClockIsShowSecond(SettingCacheHelper.getClockIsShowSecond());
        mFlipclockview.setFlipClockIsGlint(SettingCacheHelper.getClockIsGlint());
        mFlipclockview.setClockHourType(SettingCacheHelper.getClockHourType());
        mFlipclockview.setFlipClockIsShowSecond(SettingCacheHelper.getClockIsShowSecond());
    }

    private void goSettingPage() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    private void showCustomToolbar() {
        mCustomsizetoolbar.setVisibility(View.VISIBLE);
    }

    private void hideCustomToolbar() {
        mCustomsizetoolbar.setVisibility(View.GONE);
    }

    private void saveCustomSizeSetting() {
        int size = mSizeBar.getProgress();
        int padding = mPaddingSizeBar.getProgress();
        SettingCacheHelper.setClockViewSize(size);
        SettingCacheHelper.setClockViewPadding(padding);
        ToastUtils.showSuccessToast(this, "保存成功");
    }

}
