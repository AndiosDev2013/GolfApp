package com.app.golfapp.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.app.golfapp.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View view) {
        super(view);
        progressBar = view.findViewById(R.id.progressBar1);
    }
}
