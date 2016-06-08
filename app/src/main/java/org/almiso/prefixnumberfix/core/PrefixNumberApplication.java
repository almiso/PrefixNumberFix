package org.almiso.prefixnumberfix.core;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Alexandr on 07.06.16.
 */
public class PrefixNumberApplication extends Application {

    private static final String TAG = "PrefixNumberApplication";

    public static volatile PrefixNumberApplication application = null;
    public static volatile Context applicationContext = null;
    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();
        application = (PrefixNumberApplication) getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());


        ContactsController.getInstance();
    }
}
