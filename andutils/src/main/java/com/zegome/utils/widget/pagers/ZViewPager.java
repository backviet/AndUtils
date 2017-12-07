package com.zegome.utils.widget.pagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.zegome.utils.widget.XScrollDetector;
import com.zegome.utils.widget.YScrollDetector;

import java.lang.reflect.Field;

public class ZViewPager extends ViewPager {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private boolean isSwipe = false;
	private boolean isAnimation = true;

	protected ZDurationScroller mScroller = null;

	private GestureDetector mGestureDetector;

	private boolean mIsCheckGesture = true;


	// ===========================================================
	// Constructor
	// ===========================================================
	public ZViewPager(Context context) {
		this(context, null);
	}

	public ZViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

		mGestureDetector = new GestureDetector(context, new XScrollDetector(2));

		postInitViewPager();
	}


	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void setSwipe(final boolean swipe) {
		isSwipe = swipe;
	}

	public void setAni(final boolean animation) {
		isAnimation = animation;
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item, isAnimation);
	}

	/**
	 * Set the factor by which the duration will change
	 */
	public void setScrollDurationFactor(double scrollFactor) {
		if (null == mScroller) {
			return;
		}
		mScroller.setScrollDurationFactor(scrollFactor);
	}

	public long getScrollDuration() {
		return (null != mScroller ? mScroller.getDuration() : 0);
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
    	if (isSwipe) {
			return super.onInterceptTouchEvent(event)
					&& (mIsCheckGesture ? mGestureDetector.onTouchEvent(event) : true);
		}
        return false;
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
    	if (isSwipe) {
			super.onTouchEvent(event);
			boolean result = mGestureDetector.onTouchEvent(event);
			if (!result) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					result = true;
				}
			}
			return result;
    	}
        return false;
    }

	// ===========================================================
	// Methods
	// ===========================================================
	private void postInitViewPager() {
		isSwipe = false;
		isAnimation = false;

		try {
			Field scroller = ViewPager.class.getDeclaredField("mScroller");
			scroller.setAccessible(true);
			Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
			interpolator.setAccessible(true);

			mScroller = new ZDurationScroller(getContext(), (Interpolator) interpolator.get(null));
			scroller.set(this, mScroller);
		} catch (Exception e) {
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
