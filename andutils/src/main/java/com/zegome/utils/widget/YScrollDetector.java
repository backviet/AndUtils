package com.zegome.utils.widget;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by QuanLT on 12/12/16.
 */
public class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // Return false if we're scrolling in the x direction
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return (Math.abs(distanceY) > Math.abs(distanceX));
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
