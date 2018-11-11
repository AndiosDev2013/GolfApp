package com.app.golfapp;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class AppGlobals {
	/*
	 * Screen size
	 */
	public static float SCREEN_WIDTH = 480;
	public static float SCREEN_HEIGHT = 800;

	/*
	 * Location:
	 * If user select the [Use my location], it will be user's current location
	 * If user select the [Set location by map], it will be the location which was set in Map.
	 */
	public static LatLng gStartLoc = null;

	public static void init(Context context) {
		// window size
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		AppGlobals.SCREEN_WIDTH = display.getWidth();
		AppGlobals.SCREEN_HEIGHT = display.getHeight();
	}
}
