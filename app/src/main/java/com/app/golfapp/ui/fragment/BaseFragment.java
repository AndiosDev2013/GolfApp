package com.app.golfapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.app.golfapp.ui.activity.BaseActivity;

public abstract class BaseFragment extends Fragment {
	// UI
	protected View rootView;
	protected LayoutInflater mInflater;
	protected BaseActivity mActivity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}