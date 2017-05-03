package com.zegome.utils.animation;

import android.view.View;

/**
 * Created by QuanLT on 2/13/17.
 */
public interface IViewAnimation extends IUpdate.IViewUpdatable {
    IViewAnimation setTarget(final View view);
    View getTarget();

    IViewAnimation setListener(IAnimationListener listener);

    IViewAnimation translationX(float from, float to, long duration);
    IViewAnimation translationY(float from, float to, long duration);
    IViewAnimation alpha(float from, float to, long duration);
    IViewAnimation rotation(float from, float to, long duration);
    IViewAnimation scale(float from, float to, long duration);
    IViewAnimation scaleX(float from, float to, long duration);
    IViewAnimation scaleY(float from, float to, long duration);

    interface IAnimationListener {
        void onStart(IViewAnimation animation);
        void onCompleted(IViewAnimation animation);
    }
}
