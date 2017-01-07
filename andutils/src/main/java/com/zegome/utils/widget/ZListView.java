package com.zegome.utils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by QuanLT on 8/5/16.
 */
public class ZListView extends ListView {
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
    public ZListView(Context context) {
        super(context);
    }

    public ZListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setExpanded(boolean expanded) {
        this.mIsExpanded = expanded;
    }

    public void setAutoComplete(final boolean autoComplete) {
        mIsAutoComplete = autoComplete;
    }

    public boolean isExpanded() {
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

            final ViewGroup.LayoutParams params = getLayoutParams();
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
