package com.zegome.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by QuanLT on 8/5/16.
 */
public class ZGridView extends GridView {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private boolean mIsAutoComplete = true;
    private boolean mIsExpanded = false;

    // ===========================================================
    // Constructors
    // ===========================================================

    public ZGridView(Context context) {
        super(context);
    }

    public ZGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setmIsExpanded(boolean mIsExpanded) {
        this.mIsExpanded = mIsExpanded;
    }

    public void setmIsAutoComplete(final boolean mIsAutoComplete) {
        this.mIsAutoComplete = mIsAutoComplete;
    }

    public boolean ismIsExpanded() {
        return mIsExpanded;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsExpanded) {
            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();

            if (mIsAutoComplete) {
                mIsExpanded = false;
            }

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
