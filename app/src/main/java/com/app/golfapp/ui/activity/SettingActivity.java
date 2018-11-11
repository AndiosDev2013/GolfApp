package com.app.golfapp.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.widget.SeekBar;

import com.app.golfapp.AppConstant;
import com.app.golfapp.AppPreferences;
import com.app.golfapp.R;
import com.app.golfapp.service.GPSTracker;
import com.app.golfapp.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    //
    public static SettingActivity instance;
    //
    @BindView(R.id.swt_toggle)
    SwitchCompat swt_toggle;
    @BindView(R.id.txt_time_interval)
    AppCompatTextView txt_time_interval;
    @BindView(R.id.seekbar_transparent)
    SeekBar seekbar_transparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initActionBar();
        setTitle(R.string.Settings);

        swt_toggle.setChecked(AppPreferences.getBool(AppPreferences.KEY.TOGGLE_SYTEM_UI, true));
        swt_toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            CommonUtil.toggleSystemUI(this, isChecked);
            AppPreferences.setBool(AppPreferences.KEY.TOGGLE_SYTEM_UI, isChecked);
        });

        int currentIndex = AppPreferences.getInt(AppPreferences.KEY.TIME_INTERVAL_INDEX, AppConstant.DEFAULT_TIME_INTERVAL_INDEX);
        txt_time_interval.setText(AppConstant.TIME_INTERVAL_TITLES[currentIndex]);

        int trans = AppPreferences.getInt(AppPreferences.KEY.MAP_TRANSPARENT, 100);
        seekbar_transparent.setProgress(trans);
        seekbar_transparent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    AppPreferences.setInt(AppPreferences.KEY.MAP_TRANSPARENT, progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @OnClick(R.id.txt_time_interval)
    public void onTimeInterval() {
        int currentIndex = AppPreferences.getInt(AppPreferences.KEY.TIME_INTERVAL_INDEX, AppConstant.DEFAULT_TIME_INTERVAL_INDEX); // 10 minutes as default
        new AlertDialog.Builder(instance)
                .setTitle(R.string.dlg_select_time_interval)
                .setSingleChoiceItems(AppConstant.TIME_INTERVAL_TITLES, currentIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        txt_time_interval.setText(AppConstant.TIME_INTERVAL_TITLES[which]);

                        AppPreferences.setInt(AppPreferences.KEY.TIME_INTERVAL_INDEX, which);

                        // change interval
                        GPSTracker tracker = new GPSTracker(instance);
                        tracker.canGetLocation();
                    }
                })
                .show();
    }

    @Override
    public void myBack() {
        if (MainActivity.instance != null) {
            MainActivity.instance.showHoleMap();

            boolean isToggleUI = AppPreferences.getBool(AppPreferences.KEY.TOGGLE_SYTEM_UI, true);
            CommonUtil.toggleSystemUI(MainActivity.instance, isToggleUI);
        }

        super.myBack();
    }
}