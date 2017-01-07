package com.zegome.utils.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.zegome.utils.R;
import com.zegome.utils.font.FontHelper;

/**
 * Created by QuanLT on 2016/12/25.
 */
public class ZRadioButton extends RadioButton {
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
    // Constructor
    // ===========================================================
    public ZRadioButton(Context context) {
        this(context, null);
    }

    public ZRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
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
        mIsStroke = a.getBoolean(R.styleable.ZRadioButton_rbUseStroke, false);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.ZRadioButton_rbStrokeWidth, 0);
        mStrokeColor = a.getColor(R.styleable.ZRadioButton_rbStrokeColor, 0xffffffff);

        mIsCustomFont = a.getBoolean(R.styleable.ZRadioButton_rbUseFont, false);
        mFontName = a.getString(R.styleable.ZRadioButton_rbFontName);

        if (mIsCustomFont && (mFontName != null && !"".equals(mFontName))) {
            FontHelper.setCustomFont(this, mFontName, context);
        }
        a.recycle();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
