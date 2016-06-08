package org.almiso.prefixnumberfix.util;

import android.util.Log;

/**
 * Created by Alexandr on 08.06.16.
 */
public class Logger {

    public static boolean ENABLED = true;
    public static boolean LOG_THREAD = true;
    
    private static final String LOG_PREFIX = "";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;


    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void d(String TAG, String message) {
        if (!ENABLED) {
            return;
        }
        if (LOG_THREAD) {
            Log.d(TAG, Thread.currentThread().getName() + "| "
                    + message);
        } else {
            Log.d(TAG, message);
        }
    }

    public static void e(String TAG, Exception e) {
        if (!ENABLED) {
            return;
        }
        Log.e(TAG, "Exception in class " + TAG, e);
    }

    public static void e(String TAG, String message, Exception e) {
        if (!ENABLED) {
            return;
        }
        Log.e(TAG, message, e);
    }

    public static void e(String TAG, String message, Throwable e) {
        if (!ENABLED) {
            return;
        }
        Log.e(TAG, message, e);
    }

    public static void w(String TAG, String message) {
        if (LOG_THREAD) {
            Log.w(TAG, Thread.currentThread().getName() + "| "
                    + message);
        } else {
            Log.w(TAG, message);
        }
    }
}
