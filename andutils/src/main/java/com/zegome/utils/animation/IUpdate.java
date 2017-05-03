package com.zegome.utils.animation;

/**
 * Created by QuanLT on 2/13/17.
 */

public interface IUpdate {
    interface IUpdatable {
        void onUpdate(float delta);
        void onPost();

        void start();
        void stop();

        boolean isRunning();
        boolean isAutoRemove();
        void setAutoRemove(boolean autoRemove);
    }

    interface IViewUpdatable extends IUpdatable {
        IUpdatable getParent();
        void setParent(IUpdatable updatable);

        void removeSelf();
    }

}
