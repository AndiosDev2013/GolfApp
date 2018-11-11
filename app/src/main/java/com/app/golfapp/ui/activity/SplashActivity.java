package com.app.golfapp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.app.golfapp.AppConstant;
import com.app.golfapp.AppPreferences;
import com.app.golfapp.GolfApp;
import com.app.golfapp.R;
import com.app.golfapp.service.GPSTracker;

public class SplashActivity extends BaseActivity {
    //
    private SplashActivity instance;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_splash);

        // get key hash
        // CommonUtil.printKeyHash(instance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Splash", "############ Main onresume #########");

        if (isErrorOccured)
            return;

        verifyPermissions(this);
    }

    private static final int REQUEST_PERMISSION = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
    };

    @TargetApi(19)
    public void verifyPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST_PERMISSION
            );

        } else {
            // we'll remove all old database content when user install again
            boolean firstLaunch = AppPreferences.getBool(AppPreferences.KEY.FIRST_LAUNCH, true);
            if (firstLaunch) {
                GolfApp.clearApplicationData();
                AppPreferences.setBool(AppPreferences.KEY.FIRST_LAUNCH, false);
            }

            // database
            //new DBManager(instance);

            if (!GPSTracker.isRunning) {
                Intent service = new Intent(instance, GPSTracker.class);
                startService(service);
            }

            GPSTracker tracker = new GPSTracker(instance);
            if (tracker.canGetLocation()) {
                new Handler().postDelayed(() -> {
                    finish();

                    Intent intent = new Intent(instance, MainActivity.class);
                    startActivity(intent);
                }, AppConstant.DELAY_SPLASH);

            } else {
                openGPSsetting();
            }
        }
    }

    private void openGPSsetting() {
        new AlertDialog.Builder(instance)
                .setMessage(R.string.dlg_gps_setting)
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(R.string.Cancel, null)
                .show();
    }
}