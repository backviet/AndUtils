package com.zegome.utils;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by QuanLT on 8/1/16.
 */
public final class PrintLog {
    // ===========================================================
    // Constants
    // ===========================================================
    private static final String TAG = PrintLog.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    public static boolean sIsDebug = true;
    private static final String TAG_E = TAG + "E";
    private static final String TAG_D = TAG + "D";

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * Call this when log with not release mode
     * @param message
     */
    public static final void debug(@NonNull final String message) {
        if (sIsDebug) {
            Log.e(TAG, "debug: " + message);
        }
    }

    /**
     * Call this when log with not release mode
     * @param message
     */
    public static final void debug(@NonNull final String tag, @NonNull final String message) {
        if (sIsDebug) {
            Log.e(Utils.isStringEmptyorNull(tag) ? TAG : tag, "debug: " + message);
        }
    }

    /**
     * Call only log with release mode
     * @param message must be not null
     */
    public static final void e(@NonNull final String tag, @NonNull final String message) {
        Log.e(Utils.isStringEmptyorNull(tag) ? TAG_E : tag, TAG_E + ": " + message);
    }

    /**
     * Call only log with release mode
     * @param message must be not null
     */
    public static final void e(@NonNull final String message) {
        Log.e(TAG_E, TAG_E + ": " + message);
    }

    public static final void d(@NonNull final String tag, @NonNull final String message) {
        Log.d(Utils.isStringEmptyorNull(tag) ? TAG_D : tag, TAG_D + ": " + message);
    }

    public static final void d(@NonNull final String message) {
        Log.d(TAG_D, TAG_D + ": " + message);
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
