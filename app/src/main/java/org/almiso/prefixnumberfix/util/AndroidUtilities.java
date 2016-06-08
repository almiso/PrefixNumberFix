package org.almiso.prefixnumberfix.util;

import org.almiso.prefixnumberfix.core.PrefixNumberApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexandr on 07.06.16.
 */
public class AndroidUtilities {

    protected static final String TAG = Logger.makeLogTag(AndroidUtilities.class);
    public static Pattern pattern = Pattern.compile("[0-9]+");

    public static float density = 1;

    static {
        density = PrefixNumberApplication.applicationContext.getResources().getDisplayMetrics().density;
    }


    public static volatile DispatchQueue globalQueue = new DispatchQueue("globalQueue");
    public static volatile DispatchQueue stageQueue = new DispatchQueue("stageQueue");

    public static int dp(float value) {
        return (int) Math.ceil(density * value);
    }

    public static String parseIntToString(String value) {
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
}
