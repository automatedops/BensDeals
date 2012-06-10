package net.bensdeals.util;

import android.util.Log;

public class ALog {
    public static final String TAG = "DealApplication";

    public static void i(String print) {
        Log.i(TAG, ("  == > " + print));
    }

    public static void e(Exception e) {
        i(e.toString());
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            Log.i(TAG, ("  == > " + stackTraceElement.toString()));
        }
    }
}
