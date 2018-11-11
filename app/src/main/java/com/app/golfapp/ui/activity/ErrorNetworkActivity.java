package com.app.golfapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.app.golfapp.R;
import com.app.golfapp.GolfApp;

public class ErrorNetworkActivity extends Activity {
	public static ErrorNetworkActivity instance = null;
	public static boolean isShown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		instance = this;
		setContentView(R.layout.activity_error_network);

		findViewById(R.id.btn_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
				
				isShown = true;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isShown = false;
					}
				}, 2000);
			}
		});
	}
	
	public static void OpenMe() {
		if (!isShown) {
			Intent i = new Intent(GolfApp.getContext(), ErrorNetworkActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			GolfApp.getContext().startActivity(i);
		}
	}
	
	public static void CloseMe() {
		if (ErrorNetworkActivity.instance != null)
			ErrorNetworkActivity.instance.finish();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}
}
