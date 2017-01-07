package com.zegome.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by QuanLT on 1/4/17.
 */
public class ZListViewRefreshLoadMore extends ZSwipeRefreshLayout {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private final int mTouchSlop;
    private ListView mListView;
    private ILoadMoreListener mLoadMoreListener;

    private float mTouchDownY;
    private float mTouchUpY;

    private boolean mIsLoading = false;
    private boolean mIsLoadMore = true;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZListViewRefreshLoadMore(Context context) {
        this(context, null);
    }

    public ZListViewRefreshLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public void setLoadMore(final boolean isLoadMore) {
        mIsLoadMore = isLoadMore;
    }

    public void setListView(final ListView listView) {
        mListView = listView;
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        if (checkIfLoadMorable()) {
                            loadMore(true);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public void setLoadMoreListener(final ILoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    private boolean isBottom() {
        if (mListView.getChildCount() > 0) {
            try {
                final int con1 = mListView.getLastVisiblePosition();
                final int con2 = mListView.getAdapter().getCount() - 1;
                final int con3 = mListView.getChildCount() > 0 ? mListView.getChildAt(mListView.getChildCount() - 1).getTop() : 0;
                final int con4 = mListView.getHeight();
                if (con1 == con2 && con3 <= con4) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    private boolean isPullingUp() {
        return mTouchDownY - mTouchUpY > mTouchSlop;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mTouchDownY = ev.getRawY();
                break;
            }

            case MotionEvent.ACTION_UP: {
                mTouchUpY = ev.getRawY();
            }
        }

        return super.dispatchTouchEvent(ev);
    }


    // ===========================================================
    // Methods
    // ===========================================================
    private boolean checkIfLoadMorable() {
        return mIsLoadMore && isBottom() && !mIsLoading && isPullingUp();
    }

    private void checkLoadMoreListener() {
        if (mLoadMoreListener != null) {
            mLoadMoreListener.onLoadMore();
        }
    }

    public void loadMore(final boolean load) {
        if (mListView == null) {
            return;
        }
        mIsLoading = load;
        if (load) {
            if (isRefreshing()) {
                setRefreshing(false);
            }
            if ( mListView.getAdapter() == null && mListView.getAdapter().getCount() > 0) {
                mListView.setSelection(mListView.getAdapter().getCount() - 1);
            }

            checkLoadMoreListener();
        } else {
            resetTouch();
        }
    }

    private void resetTouch() {
        mTouchDownY = 0;
        mTouchUpY = 0;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
    public interface ILoadMoreListener {
        void onLoadMore();
    }
}
