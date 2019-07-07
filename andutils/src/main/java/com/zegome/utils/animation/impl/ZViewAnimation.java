package com.zegome.utils.animation.impl;

import androidx.annotation.NonNull;
import android.view.View;

import com.zegome.utils.animation.IAnimation;
import com.zegome.utils.animation.IUpdate;
import com.zegome.utils.animation.IViewAnimation;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by QuanLT on 2/13/17.
 */
public class ZViewAnimation implements IViewAnimation {

    // ===========================================================
    // Constants
    // ===========================================================
    private static final String TRANS_X = "trans_x";
    private static final String TRANS_Y = "trans_y";
    private static final String SCALE_X = "scale_x";
    private static final String SCALE_Y = "scale_y";
    private static final String ROTATION = "rotation";
    private static final String ALPHA = "alpha";

    // ===========================================================
    // Fields
    // ===========================================================
    private View mTarget = null;
    private ZUpdateController mParent = null;

    private boolean mIsRunning = false;
    private boolean mIsAutoRemove = false;

    private HashMap<String, IAnimation> mAnimations;
    private IAnimationListener mListener;

    // ===========================================================
    // Constructors
    // ===========================================================
    private ZViewAnimation() {
        mAnimations = new HashMap<>();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    @Override
    public IViewAnimation setTarget(View view) {
        mTarget = view;
        return this;
    }

    @Override
    public View getTarget() {
        return mTarget;
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
    public IViewAnimation setListener(IAnimationListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public ZUpdateController getParent() {
        return mParent;
    }

    @Override
    public void setParent(IUpdate.IUpdatable updatable) {
        if (updatable == null || !(updatable instanceof ZUpdateController)) {
            throw new IllegalArgumentException("parent must be ZUpdateController and not null");
        }
        mParent = (ZUpdateController)updatable;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public IViewAnimation translationX(float from, float to, long duration) {
        final IAnimation animation = ZAnimation.create()
                .from(from)
                .to(to)
                .duration(duration);
        removeIfContain(TRANS_X);
        mAnimations.put(TRANS_X, animation);
        return this;
    }

    @Override
    public IViewAnimation translationY(float from, float to, long duration) {
        final IAnimation animation = ZAnimation.create()
                .from(from)
                .to(to)
                .duration(duration);
        removeIfContain(TRANS_Y);
        mAnimations.put(TRANS_Y, animation);
        return this;
    }

    @Override
    public IViewAnimation alpha(float from, float to, long duration) {
        final IAnimation animation = ZAnimation.create()
                .from(from)
                .to(to)
                .duration(duration);
        removeIfContain(ALPHA);
        mAnimations.put(ALPHA, animation);
        return this;
    }

    @Override
    public IViewAnimation rotation(float from, float to, long duration) {
        final IAnimation animation = ZAnimation.create()
                .from(from)
                .to(to)
                .duration(duration);
        removeIfContain(ROTATION);
        mAnimations.put(ROTATION, animation);
        return this;
    }

    @Override
    public IViewAnimation scale(float from, float to, long duration) {
        scaleX(from, to, duration);
        return this;
    }

    @Override
    public IViewAnimation scaleX(float from, float to, long duration) {
        scaleY(from, to, duration);
        return this;
    }

    @Override
    public IViewAnimation scaleY(float from, float to, long duration) {
        final IAnimation animation = ZAnimation.create()
                .from(from)
                .to(to)
                .duration(duration);
        removeIfContain(SCALE_Y);
        mAnimations.put(SCALE_Y, animation);
        return this;
    }

    @Override
    public void removeSelf() {
        mIsRunning = false;
    }

    @Override
    public void onUpdate(float delta) {
        final Collection<IAnimation> animations = mAnimations.values();
        for (IAnimation ani : animations) {
            ani.onUpdate(delta);
        }
    }

    @Override
    public void onPost() {
        if (mAnimations.containsKey(TRANS_X)) {
            final IAnimation animation = mAnimations.get(TRANS_X);
            mTarget.setTranslationX(animation.getValue());
        }

        if (mAnimations.containsKey(TRANS_Y)) {
            final IAnimation animation = mAnimations.get(TRANS_Y);
            mTarget.setTranslationY(animation.getValue());
        }

        if (mAnimations.containsKey(SCALE_X)) {
            final IAnimation animation = mAnimations.get(SCALE_X);
            mTarget.setScaleX(animation.getValue());
        }

        if (mAnimations.containsKey(SCALE_Y)) {
            final IAnimation animation = mAnimations.get(SCALE_Y);
            mTarget.setScaleY(animation.getValue());
        }

        if (mAnimations.containsKey(ROTATION)) {
            final IAnimation animation = mAnimations.get(ROTATION);
            mTarget.setRotation(animation.getValue());
        }

        if (mAnimations.containsKey(ALPHA)) {
            final IAnimation animation = mAnimations.get(ALPHA);
            mTarget.setAlpha(animation.getValue());
        }

    }

    @Override
    public void start() {
        if (mTarget == null) {
            throw new IllegalArgumentException("target must be difference null");
        }

        mIsRunning = true;

        final Collection<IAnimation> animations = mAnimations.values();
        for (IAnimation ani : animations) {
            ani.start();
        }
        mParent.addChild(this);
        if (mListener != null) {
            mListener.onStart(this);
        }
    }

    @Override
    public void stop() {
        mIsAutoRemove = true;
        mIsRunning = false;
        if (mListener != null) {
            mListener.onCompleted(this);
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================
    public static ZViewAnimation with(ZUpdateController controller) {
        final ZViewAnimation animation = new ZViewAnimation();
        animation.setParent(controller);

        return animation;
    }

    private void removeIfContain(@NonNull final String key) {
        if (mAnimations.containsKey(key)) {
            mAnimations.remove(key);
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
