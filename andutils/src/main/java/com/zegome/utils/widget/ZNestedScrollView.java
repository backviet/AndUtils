package com.zegome.utils.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by QuanLT on 1/6/16.
 */

public class ZNestedScrollView extends NestedScrollView{
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private OnScrollListener mScrollListener;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFadingEdgeLength(0);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setScrollListener(OnScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean startNestedScroll(int axes) {
        if (mScrollListener != null) {
            mScrollListener.onStart();
        }
        return super.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        super.stopNestedScroll();
        if (mScrollListener != null) {
            mScrollListener.onStop();
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
    public interface OnScrollListener {
        void onStop();
        void onStart();
        boolean isScrolling();
    }
}
