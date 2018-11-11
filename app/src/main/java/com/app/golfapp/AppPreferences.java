package com.app.golfapp;

import android.content.SharedPreferences;

public class AppPreferences {
	private static SharedPreferences instance = null;
	
	public static class KEY {
		public static final String FIRST_LAUNCH = "FIRST_LAUNCH";

		// rating
		public static final String RATE_ENABLE = "RATE_ENABLE";
		public static final String APP_LAUNCH_COUNT = "APP_LAUNCH_COUNT";

		// settings
		public static final String TIME_INTERVAL_INDEX = "TIME_INTERVAL_INDEX";
		public static final String MAP_TRANSPARENT = "MAP_TRANSPARENT";

		// toggle system UI
		public static final String TOGGLE_SYTEM_UI = "TOGGLE_SYTEM_UI";
	}
	
	public static void initialize(SharedPreferences pref) {
		instance = pref;
	}
	
	// check contain
	public static boolean contains(String key) {
		return instance.contains(key);
	}
	
	// boolean
	public static boolean getBool(String key, boolean def) {
		return instance.getBoolean(key, def);
	}
	public static void setBool(String key, boolean value) {
		SharedPreferences.Editor editor = instance.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}
	
	// int
	public static int getInt(String key, int def) {
		return instance.getInt(key, def);
	}
	public static void setInt(String key, int value) {
		SharedPreferences.Editor editor = instance.edit();
		editor.putInt(key, value);
		editor.apply();
	}
	
	// long
	public static long getLong(String key, long def) {
		return instance.getLong(key, def);
	}
	public static void setLong(String key, long value) {
		SharedPreferences.Editor editor = instance.edit();
		editor.putLong(key, value);
		editor.apply();
	}
	
	// string
	public static String getStr(String key, String def) {
		return instance.getString(key, def);
	}
	public static void setStr(String key, String value) {
		SharedPreferences.Editor editor = instance.edit();
		editor.putString(key, value);
		editor.apply();
	}

	// remove
	public static void removeKey(String key) {
		SharedPreferences.Editor editor = instance.edit();
		editor.remove(key);
		editor.apply();
	}
}
