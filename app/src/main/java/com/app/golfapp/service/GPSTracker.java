package com.app.golfapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.app.golfapp.AppConstant;
import com.app.golfapp.AppPreferences;
import com.app.golfapp.GolfApp;
import com.app.golfapp.utils.DateTimeUtils;
import com.app.golfapp.utils.LogUtil;

import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class GPSTracker extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    //
    public static boolean isRunning = false;

    private Context mContext = null;
    boolean canGetLocation = false;
    private static Location sLoc; // location

    public GPSTracker() {
        mContext = GolfApp.getContext();
        isRunning = true;
        getLocation();
    }

    public GPSTracker(Context context) {
        this.mContext = context;
        isRunning = true;
        getLocation();
    }

    public void getLocation() {
        try {
            LocationManager locMgr = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNetworkEnabled = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                canGetLocation = false;

            } else {
                if (ActivityCompat.checkSelfPermission(mContext, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(mContext, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED) {
                    this.canGetLocation = true;

                    int intervalIndex = AppPreferences.getInt(AppPreferences.KEY.TIME_INTERVAL_INDEX, AppConstant.DEFAULT_TIME_INTERVAL_INDEX);
                    int seconds = AppConstant.TIME_INTERVAL_VALUES[intervalIndex];

                    // First get location from Network Provider
                    if (isNetworkEnabled) {
                        locMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, seconds * 1000, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (sLoc == null)
                            sLoc = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Log.d("NETWORK_PROVIDER", "NETWORK_PROVIDER Enabled");
                    }

                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, seconds * 1000, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (sLoc == null)
                            sLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d("GPS_PROVIDER", "GPS_PROVIDER Enabled");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sLoc == null)
            canGetLocation = false;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public static double getLatitude() {
        if (sLoc != null)
            return sLoc.getLatitude();
        return 0;
    }

    public static double getLongitude() {
        if (sLoc != null)
            return sLoc.getLongitude();
        return 0;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (sLoc != null) {
            float[] distance = new float[1];
            Location.distanceBetween(sLoc.getLatitude(), sLoc.getLongitude(), location.getLatitude(), location.getLongitude(), distance);

            // if the distance between current location and new location is less than 1 meter
            // we will not update
            if (distance[0] < 1)
                return;
        }

        sLoc = location;

        // save log
        LogUtil.writeDebugLog("#######", String.format(Locale.getDefault(),
                "Location -- latitude:%f, longitude:%f%n" +
                        "Timestamp -- %s",
                getLatitude(), getLongitude(),
                DateTimeUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}