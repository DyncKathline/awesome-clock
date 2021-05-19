package com.plain.awesome_clock_ace.filpClock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.plain.awesome_clock_ace.R;
import com.plain.awesome_clock_ace.constant.Constant;
import com.plain.awesome_clock_ace.setting.SettingActivity;
import com.plain.awesome_clock_ace.utils.MultiClickHelper;
import com.plain.awesome_clock_ace.utils.SettingCacheHelper;
import com.plain.awesome_clock_ace.utils.ToastUtils;
import com.plain.awesome_clock_ace.view.FlipClockView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/**
 * 翻页时钟 ⏰
 *
 * @author Plain
 * @date 2019-11-28 14:34
 */
public class FlipClockFragment extends Fragment implements FlipClockView.IElapsedTimeListener {

    private final String TAG = "FlipClock";
    private View rootView;
    private FrameLayout mainView;
    private FlipClockView flipClockView;
    private CardView customSizeToolbar;
    private AppCompatSeekBar sizeBar;
    private AppCompatSeekBar paddingSizeBar;
    private Button btnSave;

    public static FlipClockFragment newInstance() {
        return new FlipClockFragment();
    }

    private SeekBar.OnSeekBarChangeListener textSizeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                flipClockView.customTextSize(progress);
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
                flipClockView.customPadding(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @org.jetbrains.annotations.Nullable ViewGroup container, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_flip_clock, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        mainView = (FrameLayout) rootView.findViewById(R.id.mainView);
        flipClockView = (FlipClockView) rootView.findViewById(R.id.flipClockView);
        customSizeToolbar = (CardView) rootView.findViewById(R.id.customSizeToolbar);
        sizeBar = (AppCompatSeekBar) rootView.findViewById(R.id.size_bar);
        paddingSizeBar = (AppCompatSeekBar) rootView.findViewById(R.id.padding_size_bar);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
    }

    private void goSettingPage() {
        startActivity(new Intent(getContext(), SettingActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        flipClockView.setListener(this);
        sizeBar.setOnSeekBarChangeListener(textSizeListener);
        paddingSizeBar.setOnSeekBarChangeListener(paddingSizeListener);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomSizeSetting();
                hideCustomToolbar();
            }
        });
        mainView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showCustomToolbar();
                return true;
            }
        });
        mainView.setOnTouchListener(new MultiClickHelper(new MultiClickHelper.MultiClickListener() {
            @Override
            public void onClickProcess(int index) {

            }

            @Override
            public void onDoSomething() {
                goSettingPage();
            }
        }, 2));
        flipClockView.resume();
        updateSetting();
    }

    private void saveCustomSizeSetting() {
        int size = sizeBar.getProgress();
        int padding = paddingSizeBar.getProgress();
        SettingCacheHelper.setClockViewSize(size);
        SettingCacheHelper.setClockViewPadding(padding);
        ToastUtils.showSuccessToast(getContext(), "保存成功");
    }

    private void showCustomToolbar() {
        customSizeToolbar.setVisibility(View.VISIBLE);
    }

    private void hideCustomToolbar() {
        customSizeToolbar.setVisibility(View.GONE);
    }

    /**
     * 更新设置
     */
    private void updateSetting() {
        int clockTextColor = SettingCacheHelper.getClockTextColor();
        if (clockTextColor == Constant.SETTING_EMPTY) {
            clockTextColor = ContextCompat.getColor(getContext(), R.color.clock_text);
        }
        int clockBgColor = SettingCacheHelper.getClockBgColor();
        if (clockBgColor == Constant.SETTING_EMPTY) {
            clockBgColor = ContextCompat.getColor(getContext(), R.color.clock_bg);
        }
        flipClockView.updateColor(clockTextColor, clockBgColor);
        flipClockView.setFlipClockIsShowSecond(SettingCacheHelper.getClockIsShowSecond());
        flipClockView.setFlipClockIsGlint(SettingCacheHelper.getClockIsGlint());
        flipClockView.setClockHourType(SettingCacheHelper.getClockHourType());
        flipClockView.setFlipClockIsShowSecond(SettingCacheHelper.getClockIsShowSecond());
    }

    @Override
    public void onPause() {
        super.onPause();
        flipClockView.pause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        switch (event) {
            case Constant.CLOCK_RESUME: {
                flipClockView.resume();
            }
            case Constant.CLOCK_PAUSE: {
                flipClockView.pause();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onChange(String time) {
        checkSpecialTime(time);
    }

    // 43199 00:00(12) 46799 13:00 86399 00:00(24)
    private void checkSpecialTime(String time) {
        if (time == "43199" || time == "46799" || time == "86399") {
            Log.d(TAG, "Refresh Page");
            EventBus.getDefault().post(Constant.REBUILDING);
        }
    }
}