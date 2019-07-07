package com.zegome.utils.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.zegome.utils.R;
import com.zegome.utils.font.FontHelper;

/**
 * Created by QuanLT on 2016/12/25.
 */
public class ZCheckBox extends AppCompatCheckBox {
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
    public ZCheckBox(Context context) {
        this(context, null);
    }

    public ZCheckBox(Context context, AttributeSet attrs) {
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
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZCheckBox);
        mIsStroke = a.getBoolean(R.styleable.ZCheckBox_useStroke, false);
        mStrokeWidth = a.getDimensionPixelSize(R.styleable.ZCheckBox_strokeWidth, 0);
        mStrokeColor = a.getColor(R.styleable.ZCheckBox_strokeColor, 0xffffffff);

        mIsCustomFont = a.getBoolean(R.styleable.ZCheckBox_useFont, false);
        mFontName = a.getString(R.styleable.ZCheckBox_fontName);

        if (mIsCustomFont && (mFontName != null && !"".equals(mFontName))) {
            FontHelper.setCustomFont(this, mFontName, context);
        }
        a.recycle();
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
