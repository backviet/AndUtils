package com.zegome.utils.animation;

/**
 * Created by QuanLT on 8/17/16.
 */
public interface ILifeCycle {
    void onResume();
    void onPause();
    boolean onBackSelected();
    void onCreate();
    void onDestroy();
}
