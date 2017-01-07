package com.zegome.utils.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zegome.utils.R;
import com.zegome.utils.font.FontHelper;

/**
 * Created by QuanLT on 8/5/16.
 */
public class ZTextView extends TextView {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private boolean mIsStroke = false;
    private int mStrokeWidth = 0;
    private int mStrokeColor;

    private boolean mIsCustomFont = false;
    private String mFontName = null;

    // ===========================================================
    // Constructors
    // ===========================================================
    public ZTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public ZTextView(Context context) {
        this(context, null);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsStroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(mStrokeWidth);
            setTextColor(mStrokeColor);
            super.onDraw(canvas);

            getPaint().setStyle(Paint.Style.FILL);
            setTextColor(states);
        }

        super.onDraw(canvas);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    private void initView(@NonNull final Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZTextView);
        mIsStroke = a.getBoolean(R.styleable.ZTextView_useStroke, false);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.ZTextView_strokeWidth, 0);
        mStrokeColor = a.getColor(R.styleable.ZTextView_strokeColor, 0xffffffff);

        mIsCustomFont = a.getBoolean(R.styleable.ZTextView_useFont, false);
        mFontName = a.getString(R.styleable.ZTextView_fontName);

        if (mIsCustomFont && (mFontName != null && !"".equals(mFontName))) {
            FontHelper.setCustomFont(this, mFontName, context);
        }
        a.recycle();
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
