package com.app.golfapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class NormalAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // data
    ArrayList<T> mDataList;

    public NormalAdapter(ArrayList<T> data) {
        mDataList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mAdapterListener != null)
            return mAdapterListener.onCreateViewHolder(parent, viewType);

        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (mAdapterListener != null)
            mAdapterListener.onBindViewHolder(viewHolder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : getBasicItemCount();
    }


    public int getBasicItemCount() {
        return mDataList.size();
    }

    AdapterListener mAdapterListener;

    public void setAdapterListener(AdapterListener listener) {
        mAdapterListener = listener;
    }

    public interface AdapterListener {
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
        public void onLoadMore();
    }
}
