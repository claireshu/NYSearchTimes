package com.claireshu.nysearchtimes_;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by claireshu on 6/23/16.
 */
public class StaticUtils {
    public static Typeface sTypeFace(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesanspro.otf");

        return font;
    }
}
