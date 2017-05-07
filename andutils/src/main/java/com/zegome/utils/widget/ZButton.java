package com.zegome.utils.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;

import com.zegome.utils.R;
import com.zegome.utils.font.FontHelper;

/**
 * Created by QuanLT on 8/5/16.
 */
public class ZButton extends AppCompatButton {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    private boolean mIsStroke = false;
    private int mStrokeWidth = 0;
    private int mStrokeColor;

    private boolean mCustomFont = false;
    private String mFontName = null;


    // ===========================================================
    // Constructors
    // ===========================================================
    public ZButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public ZButton(Context context) {
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
    private void initView(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZButton);
        mIsStroke = a.getBoolean(R.styleable.ZButton_useStroke, false);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.ZButton_strokeWidth, 0);
        mStrokeColor = a.getColor(R.styleable.ZButton_strokeColor, 0xffffffff);

        mCustomFont = a.getBoolean(R.styleable.ZButton_useFont, false);
        mFontName = a.getString(R.styleable.ZButton_fontName);

        if (mCustomFont && (mFontName != null && !"".equals(mFontName))) {
            FontHelper.setCustomFont(this, mFontName, context);
        }
        a.recycle();
    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
