package com.zegome.utils.animation.impl;

import com.zegome.utils.animation.IAnimation;
import com.zegome.utils.animation.IUpdate;

/**
 * Created by QuanLT on 2/13/17.
 */
public class ZAnimation implements IAnimation {

    // ===========================================================
    // Constants
    // ===========================================================
    private static final float ONE_SECOND = 1000.f;

    // ===========================================================
    // Fields
    // ===========================================================
    protected float mFromValue = 0;
    protected float mToValue = 0;
    protected float mCurrentValue = 0;
    protected float mProgress = 0;

    protected IUpdate.IUpdatable mParent = null;

    protected float mDuration = 0;
    protected float mUpdatedTime = 0;

    private float mDiffValue = 0;

    protected boolean mIsRunning = false;
    protected boolean mIsAutoRemove = false;

    // ===========================================================
    // Constructors
    // ===========================================================
    private ZAnimation() {

    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    @Override
    public float getValue() {
        return mCurrentValue;
    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    @Override
    public boolean isAutoRemove() {
        return mIsAutoRemove;
    }

    @Override
    public void setAutoRemove(boolean autoRemove) {
        mIsAutoRemove = autoRemove;
    }

    @Override
    public IUpdate.IUpdatable getParent() {
        return mParent;
    }

    @Override
    public void setParent(IUpdate.IUpdatable updatable) {
        mParent = updatable;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public IAnimation from(float fromValue) {
        mFromValue = fromValue;
        return this;
    }

    @Override
    public IAnimation to(float toValue) {
        mToValue = toValue;
        return this;
    }

    @Override
    public IAnimation duration(long timeMiliseconds) {
        mDuration = timeMiliseconds / ONE_SECOND;
        return this;
    }

    @Override
    public void onUpdate(float delta) {
        mUpdatedTime += delta;
        mProgress = mUpdatedTime / mDuration;
        if (mUpdatedTime >= mDuration) {
            mProgress = 1;

        }
        mCurrentValue = mProgress * mDiffValue + mFromValue;
    }

    @Override
    public void onPost() {

    }

    @Override
    public void removeSelf() {
        mIsRunning = false;
    }

    @Override
    public void start() {
        if (mDuration <= 0 || (mFromValue == mToValue)) {
            throw new IllegalArgumentException("duration must be difference 0, fromValue and toValue must be difference");
        }
        mIsRunning = true;
        mUpdatedTime = 0;
        calculate();
    }

    @Override
    public void stop() {
        mIsRunning = false;
        removeSelf();
    }

    // ===========================================================
    // Methods
    // ===========================================================
    public static ZAnimation create() {
        return new ZAnimation();
    }

    private void calculate() {
        mDiffValue = mFromValue - mToValue;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
