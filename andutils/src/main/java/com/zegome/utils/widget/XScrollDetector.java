package com.zegome.utils.widget;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by QuanLT on 12/12/16.
 */
public class XScrollDetector extends GestureDetector.SimpleOnGestureListener {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private float mFactor = 1;

    // ===========================================================
    // Constructors
    // ===========================================================
    public XScrollDetector() {

    }

    public XScrollDetector(final float factor) {
        setFactor(factor);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public float getFactor() {
        return mFactor;
    }

    public void setFactor(float factor) {
        mFactor = factor < 1 ? 1 : factor;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    // Return false if we're scrolling in the y direction
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return Math.abs(distanceY) * mFactor < Math.abs(distanceX);
    }


    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
