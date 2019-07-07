package com.zegome.utils.widget;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by QuanLT on 12/12/16.
 */
public class ZSwipeRefreshLayout extends SwipeRefreshLayout {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private GestureDetector mGestureDetector;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public ZSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGestureDetector = new GestureDetector(context, new YDeepScrollDetector());
        setFadingEdgeLength(0);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }


    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
    private class YDeepScrollDetector extends GestureDetector.SimpleOnGestureListener {
        // Return false if we're scrolling in the x direction
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return (Math.abs(distanceY) > Math.abs(distanceX) && (Math.abs(distanceY) >= Math.abs(distanceX) * 5 || distanceX == 0));
        }
    }
}
