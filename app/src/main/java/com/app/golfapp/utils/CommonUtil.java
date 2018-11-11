package com.app.golfapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.app.golfapp.GolfApp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtil {
	/*
	 * Hide keyboard
	 */
	public static void hideKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	// Hashkey of keystore
	public static String printKeyHash(Activity context) {
		PackageInfo packageInfo;
		String key = null;
		try {
			//getting application package name, as defined in manifest
			String packageName = context.getApplicationContext().getPackageName();

			//Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

			Log.e("Package Name=", context.getApplicationContext().getPackageName());

			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));

				// String key = new String(Base64.encodeBytes(md.digest()));
				Log.e("Key Hash=", key);
			}
		} catch (PackageManager.NameNotFoundException e1) {
			Log.e("Name not found", e1.toString());
		}
		catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}

	/*
	 * Launch market
	 */
	public static void openUrl(String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		GolfApp.getContext().startActivity(browserIntent);
	}

	public static void launchMarket(String packageName) {
		try {
			openUrl("market://details?id=" + packageName);
		} catch (Exception e) {
			// open with browser
			openUrl("https://play.google.com/store/apps/details?id=" + packageName);
		}
	}

	// toggle UI
	public static void toggleSystemUI(AppCompatActivity activity, boolean isChecked) {
		if (isChecked) {
			// show status bar
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN){
				View decorView = activity.getWindow().getDecorView();
				// Hide the status bar and navigation bar.
				int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
				decorView.setSystemUiVisibility(uiOptions);
			}

			// show actionbar
//			ActionBar actionBar = activity.getSupportActionBar();
//			if (actionBar != null)
//				actionBar.show();

		} else {
			// hide status bar
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN){
				View decorView = activity.getWindow().getDecorView();
				// Hide the status bar.
				int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
				decorView.setSystemUiVisibility(uiOptions);
			}

			// hide actionbar
//			ActionBar actionBar = activity.getSupportActionBar();
//			if (actionBar != null)
//				actionBar.hide();
		}
	}
}
