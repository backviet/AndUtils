package com.zegome.utils.font;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontCache {

    private static HashMap<String, Typeface> sFontCache = new HashMap<>();

    public static Typeface get(String fontname, Context context) {
        Typeface typeface = sFontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            sFontCache.put(fontname, typeface);
        }

        return typeface;
    }
}