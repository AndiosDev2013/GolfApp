package com.app.golfapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.app.golfapp.R;
import com.app.golfapp.ui.view.LoadingViewHolder;

/*
 * RecyclerView Adapter that allows to add a header view.
 * */
public class SelectRestaurantAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    public static final int TYPE_ADD_RESTAURANT = 2;
    public static final int TYPE_ITEM = 3;
    private static final int TYPE_LOADING_MORE = 4;

    // data
    private Context mContext;

    // loading more
    boolean isLoading;
    private int mVisibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    ArrayList<T> mDataList;

    public SelectRestaurantAdapter(RecyclerView recycler_view, Context context, ArrayList<T> data, int visibleThreshold) {
        mContext = context;
        mDataList = data;

        final LinearLayoutManager layoutMgr = (LinearLayoutManager) recycler_view.getLayoutManager();
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutMgr.getItemCount();
                lastVisibleItem = layoutMgr.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + mVisibleThreshold)) {
                    isLoading = true;
                    if (mSelectRestaurantAdapterListener != null)
                        mSelectRestaurantAdapterListener.onLoadMore();
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);

        } else if (viewType == TYPE_ADD_RESTAURANT || viewType == TYPE_ITEM) {
            if (mSelectRestaurantAdapterListener != null)
                return mSelectRestaurantAdapterListener.onCreateViewHolder(parent, viewType);
            return null;

        } else if (viewType == TYPE_LOADING_MORE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_loading_more, parent, false);
            return new LoadingViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position) && !isPostionLoadingMore(position)) {
            if (mSelectRestaurantAdapterListener != null)
                mSelectRestaurantAdapterListener.onBindViewHolder(viewHolder, position - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionAddRestaurant(position)) {
            return TYPE_ADD_RESTAURANT;
        } else if (isPostionLoadingMore(position)) {
            return TYPE_LOADING_MORE;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : getBasicItemCount() + 1;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public int getBasicItemCount() {
        return mDataList.size();
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionAddRestaurant(int position) {
        return position == 1;
    }

    private boolean isPostionLoadingMore(int position) {
        return mDataList.get(position - 1) == null;
    }

    SelectRestaurantAdapterListener mSelectRestaurantAdapterListener;

    public void setSelectRestaurantAdapterListener(SelectRestaurantAdapterListener listener) {
        mSelectRestaurantAdapterListener = listener;
    }

    public interface SelectRestaurantAdapterListener {
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
        public void onLoadMore();
    }
}
