package com.zegome.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by QuanLT on 8/5/16.
 */
public class ZScrollView  extends ScrollView {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private GestureDetector mGestureDetector;

    private boolean mIsCheckGesture = true;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector(3));
        setFadingEdgeLength(0);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public boolean isCheckGesture() {
        return mIsCheckGesture;
    }

    public void setCheckGesture(boolean checkGesture) {
        mIsCheckGesture = checkGesture;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev)
                && (mIsCheckGesture ? mGestureDetector.onTouchEvent(ev) : true);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev){
//        mGestureDetector.onTouchEvent(ev);
//        super.dispatchTouchEvent(ev);
//        return true;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        boolean result = mGestureDetector.onTouchEvent(event);
        if (!result) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                stopScrolling();
                result = true;
            }
        }
        return result;
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void stopScrolling() {
//        mScroller.forceFinished(true);
//        fullScroll(SCROLL_AXIS_VERTICAL);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
