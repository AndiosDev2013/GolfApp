package com.app.golfapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import java.io.File;

import com.app.golfapp.database.DBManager;
import com.mapbox.mapboxsdk.Mapbox;

public class GolfApp extends MultiDexApplication {
    private static Context mContext = null;
    public static String mPackageName;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();

        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        // window size
        AppGlobals.init(mContext);

        // preference
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        AppPreferences.initialize(pref);

        // get package name
        mPackageName = mContext.getPackageName();
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getAppPackageName() {
        if (TextUtils.isEmpty(mPackageName))
            return "";

        return mPackageName;
    }

    public static void clearApplicationData() {
        File cacheDirectory = mContext.getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}