package com.app.golfapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Starter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!GPSTracker.isRunning) {
            Intent service = new Intent(context, GPSTracker.class);
            context.startService(service);
        }
    }
}