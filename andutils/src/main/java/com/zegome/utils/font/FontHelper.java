package com.zegome.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.HashMap;

public class FontHelper {

    private static HashMap<String, Typeface> sFontCache = null;

    private FontHelper() {

    }

    public static void setCustomFont(@NonNull final TextView textview, @NonNull final String font, @NonNull final Context context) {
        if (TextUtils.isEmpty(font)) {
            return;
        }

        final Typeface tf = get(font, context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }

    private static HashMap<String, Typeface> getFontCache() {
        if (sFontCache == null) {
            synchronized (FontHelper.class) {
                if (sFontCache == null) {
                    sFontCache = new HashMap<>();
                }
            }
        }
        return sFontCache;
    }

    public static synchronized Typeface get(@NonNull final String fontname, @NonNull final Context context) {
        Typeface typeface = getFontCache().get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            getFontCache().put(fontname, typeface);
        }

        return typeface;
    }
}