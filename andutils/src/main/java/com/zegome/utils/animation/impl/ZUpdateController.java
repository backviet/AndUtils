package com.zegome.utils.animation.impl;

import android.app.Activity;

import com.zegome.utils.animation.ILifeCycle;
import com.zegome.utils.animation.IUpdate;

import java.util.ArrayList;


/**
 * Created by QuanLT on 2/13/17.
 */
public class ZUpdateController implements IUpdate.IUpdatable, ILifeCycle, Runnable {

    // ===========================================================
    // Constants
    // ===========================================================
    // desired fps
    private final static int 	MAX_FPS = 30;
    // maximum number of frames to be skipped
    private final static int	MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;

    private final static float NANO_TIME = 1000000000.0f;
    // ===========================================================
    // Fields
    // ===========================================================
    private Activity mActivity;

    Thread mUpdateThread = null;
    volatile boolean mIsRunning = false;

    private ArrayList<IUpdate.IUpdatable> mChildren;

    private boolean mIsStartable = false;

    // ===========================================================
    // Constructors
    // ===========================================================
    private ZUpdateController(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity must be not null");
        }
        mIsStartable = false;
        mActivity = activity;
        mChildren = new ArrayList<>();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    @Override
    public boolean isAutoRemove() {
        return true;
    }

    @Override
    public void setAutoRemove(boolean autoRemove) {

    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void onUpdate(float delta) {
        synchronized (mChildren) {
            for (int i = mChildren.size() - 1; i >= 0; i--) {
                final IUpdate.IUpdatable updatable = mChildren.get(i);
                if (!updatable.isRunning() && updatable.isAutoRemove()) {
                    mChildren.remove(updatable);
                }
            }

            for (IUpdate.IUpdatable updatable : mChildren) {
                updatable.onUpdate(delta);
            }
        }
    }

    @Override
    public void onPost() {
        synchronized (mChildren) {
            for (IUpdate.IUpdatable updatable : mChildren) {
                updatable.onPost();
            }
        }
    }

    @Override
    public void onResume() {
        if (mIsStartable) {
            onStart();
        }
    }

    @Override
    public void onPause() {
        destroy();
    }

    @Override
    public boolean onBackSelected() {
        return false;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        stop();
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime = 0;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped
        while(mIsRunning) {
            float deltaTime = (System.nanoTime()-startTime) / NANO_TIME;
            startTime = System.nanoTime();

            beginTime = System.currentTimeMillis();
            framesSkipped = 0;	// resetting the frames skipped

            if (mChildren != null) {
                onUpdate(deltaTime);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onPost();
                    }
                });
            }

            // calculate how long did the cycle take
            timeDiff = System.currentTimeMillis() - beginTime;
            // calculate sleep time
            sleepTime = (int)(FRAME_PERIOD - timeDiff);

            if (sleepTime > 0) {
                // if sleepTime > 0 we're OK
                try {
                    // send the thread to sleep for a short period
                    // very useful for battery saving
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {}
            }

            while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                // we need to catch up
                // update without rendering

                if (mChildren != null) {
                    onUpdate(deltaTime);
                }

                // add frame period to check if in next frame
                sleepTime += FRAME_PERIOD;
                framesSkipped++;
            }

        }
    }

    @Override
    public void stop() {
        mIsStartable = false;
        destroy();
    }

    @Override
    public void start() {
        mIsStartable = true;
        onStart();
    }

    // ===========================================================
    // Methods
    // ===========================================================
    public final static ZUpdateController with(final Activity activity) {
        return new ZUpdateController(activity);
    }

    private void destroy() {
        if (!mIsRunning) {
            return;
        }
        mIsRunning = false;
        while(true) {
            try {
                mUpdateThread.join();
                return;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }

    private void onStart() {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
        mUpdateThread = new Thread(this);
        mUpdateThread.start();
    }

    public void addChild(final IUpdate.IUpdatable updatable) {
        mChildren.add(updatable);
    }

    public boolean removeChild(final IUpdate.IUpdatable updatable) {
        if (mChildren.contains(updatable)) {
            return mChildren.remove(updatable);
        }
        return false;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
