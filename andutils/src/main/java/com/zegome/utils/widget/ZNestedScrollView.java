package com.zegome.utils.widget;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
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
    private GestureDetector mGestureDetector;

    private boolean mIsCheckGesture = true;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGestureDetector = new GestureDetector(context, new YScrollDetector(5));
        setFadingEdgeLength(0);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setCheckGesture(boolean checkGesture) {
        mIsCheckGesture = checkGesture;
    }

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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && (mIsCheckGesture ? mGestureDetector.onTouchEvent(ev) : true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        super.onTouchEvent(event);
        boolean result = mGestureDetector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                result = true;
            }
        }
        return result;
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
