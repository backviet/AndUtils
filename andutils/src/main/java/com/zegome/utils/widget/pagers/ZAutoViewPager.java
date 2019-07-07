package com.zegome.utils.widget.pagers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by QuanLT on 10/20/16.
 */
public class ZAutoViewPager extends ZViewPager {
    // ===========================================================
    // Constants
    // ===========================================================
    public static final int SCROLL_WHAT = 0;
    public static final int DEFAULT_INTERVAL = 3000;

    // ===========================================================
    // Fields
    // ===========================================================
    private long mInterval = DEFAULT_INTERVAL;

    private boolean mIsStopWhenTouch = true;

    private Handler mHandler;
    private boolean mIsAutoScroll = false;
    private boolean mIsStopByTouch = false;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZAutoViewPager(Context paramContext) {
        super(paramContext);
        init();
    }

    public ZAutoViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }


    // ===========================================================
    // Getter & Setter
    // ===========================================================
    public long getInterval() {
        return mInterval;
    }

    public void setInterval(long interval) {
        this.mInterval = interval;
    }

    public boolean isStopWhenTouch() {
        return mIsStopWhenTouch;
    }

    public void setStopWhenTouch(boolean stopScrollWhenTouch) {
        this.mIsStopWhenTouch = stopScrollWhenTouch;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (mIsStopWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && mIsAutoScroll) {
                mIsStopByTouch = true;
                mScroller.useDefault = true;
                stopAutoScroll();
            } else if (((ev.getAction() == MotionEvent.ACTION_UP) || (ev.getAction() == MotionEvent.ACTION_CANCEL))
                    && mIsStopByTouch) {
                mIsStopByTouch = false;
                mScroller.useDefault = false;
                startAutoScroll();
            }
        }

        getParent().requestDisallowInterceptTouchEvent(false);

        return super.dispatchTouchEvent(ev);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void init() {
        mHandler = new MyHandler(this);
    }

    public void startAutoScroll() {
        mIsAutoScroll = true;
        sendScrollMessage((long)(mInterval));
    }

    public void startAutoScroll(int delayTimeInMills) {
        mIsAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    public void stopAutoScroll() {
        mIsAutoScroll = false;
        mHandler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        mHandler.removeMessages(SCROLL_WHAT);
        mHandler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private static class MyHandler extends Handler {

        private final WeakReference<ZAutoViewPager> autoScrollViewPager;

        public MyHandler(ZAutoViewPager zViewPager) {
            this.autoScrollViewPager = new WeakReference<>(zViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    ZAutoViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        final int curr = pager.getCurrentItem();
                        final int count = pager.getAdapter().getCount();
                        int next = curr >= count - 1 ? 0 : curr + 1;
                        if (next == 0) {
                            pager.setCurrentItem(next, false);
                        } else {
                            pager.setCurrentItem(next);
                        }
                        pager.sendScrollMessage(pager.mInterval + pager.getScrollDuration());
                    }
                default:
                    break;
            }
        }
    }
}
