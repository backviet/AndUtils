package com.zegome.utils.animation;

/**
 * Created by QuanLT on 2/13/17.
 */
public interface IAnimation extends IUpdate.IViewUpdatable {
    IAnimation from(float fromValue);
    IAnimation to(float toValue);
    IAnimation duration(long timeMiliseconds);

    float getValue();
}
