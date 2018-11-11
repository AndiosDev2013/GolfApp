package com.app.golfapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.golfapp.AppPreferences;
import com.app.golfapp.R;
import com.app.golfapp.ui.dialog.MyProgressDialog;
import com.app.golfapp.utils.CommonUtil;
import com.app.golfapp.utils.DeviceUtil;

public abstract class BaseActivity extends AppCompatActivity {

	// UI
	public MyProgressDialog dlg_progress;
	// Data
	public boolean isErrorOccured = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dlg_progress = new MyProgressDialog(this);

		boolean toggleUI = AppPreferences.getBool(AppPreferences.KEY.TOGGLE_SYTEM_UI, true);
		CommonUtil.toggleSystemUI(this, toggleUI);
	}

	public void initActionBar() {
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		setTitle("");

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.in_left, R.anim.out_left);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.in_left, R.anim.out_left);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!DeviceUtil.isNetworkAvailable(this)) {
			isErrorOccured = true;
			ErrorNetworkActivity.OpenMe();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void myBack() {
		finish();
		overridePendingTransition(R.anim.in_right, R.anim.out_right);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dlg_progress != null)
			dlg_progress.dismiss();
	}

	@Override
	public void onBackPressed() {
		myBack();
	}
}

