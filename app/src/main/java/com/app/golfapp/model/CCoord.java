package com.app.golfapp.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class CCoord {
    @SerializedName("@coordinate")
    public String coordinate;

    public static LatLng parse(String coordinate) {
        if (TextUtils.isEmpty(coordinate))
            return null;

        String strings[] = coordinate.split(",");
        if (strings.length >= 2) {
            double longitude = Double.parseDouble(strings[0]);
            double latitude = Double.parseDouble(strings[1]);
            return new LatLng(latitude, longitude);
        }

        return null;
    }
}
