package org.almiso.prefixnumberfix.util;

import org.almiso.prefixnumberfix.core.PrefixNumberApplication;

/**
 * Created by Alexandr on 07.06.16.
 */
public class AndroidUtilities {

    protected static final String TAG = "AndroidUtilities";
    public static float density = 1;

    static {
        density = PrefixNumberApplication.applicationContext.getResources().getDisplayMetrics().density;
    }

    public static int dp(float value) {
        return (int) Math.ceil(density * value);
    }
}
